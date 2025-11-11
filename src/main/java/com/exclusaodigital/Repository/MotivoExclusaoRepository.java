package com.exclusaodigital.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.exclusaodigital.Model.Categoria;
import com.exclusaodigital.Model.MotivoExclusao;
import com.exclusaodigital.Model.Subcategoria;
import com.exclusaodigital.Util.DatabaseConnection;

public class MotivoExclusaoRepository {
    
    public MotivoExclusao buscarPorId(int id, Subcategoria subcategoria) {
        String sql = "SELECT * FROM motivos_exclusao WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs, subcategoria);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar motivo por ID: " + e.getMessage());
        }
        return null;
    }

    public List<MotivoExclusao> buscarPorMotivoPrevalente(String motivo, int valorMinimo) {
        List<MotivoExclusao> motivos = new ArrayList<>();
        String sql = "SELECT m.*, s.id as subcategoria_id, s.nome as subcategoria_nome, " +
                    "c.id as categoria_id, c.nome as categoria_nome " +
                    "FROM motivos_exclusao m " +
                    "JOIN subcategorias s ON m.id_subcategoria = s.id " +
                    "JOIN categorias c ON s.id_categoria = c.id " +
                    "WHERE m." + motivo + " >= ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, valorMinimo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Categoria categoria = new Categoria(rs.getInt("categoria_id"), rs.getString("categoria_nome"));
                    Subcategoria subcategoria = new Subcategoria(rs.getInt("subcategoria_id"), categoria, rs.getString("subcategoria_nome"));
                    motivos.add(mapResultSet(rs, subcategoria));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar motivos: " + e.getMessage());
        }
        return motivos;
    }

    public List<MotivoExclusao> buscarPorMultiplosFiltros(Map<String, Integer> filtros) {
        List<MotivoExclusao> motivos = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT m.*, s.id as subcategoria_id, s.nome as subcategoria_nome, " +
            "c.id as categoria_id, c.nome as categoria_nome " +
            "FROM motivos_exclusao m " +
            "JOIN subcategorias s ON m.id_subcategoria = s.id " +
            "JOIN categorias c ON s.id_categoria = c.id WHERE 1=1");
        
        List<Object> params = new ArrayList<>();
        
        for (Map.Entry<String, Integer> entry : filtros.entrySet()) {
            sql.append(" AND m.").append(entry.getKey()).append(" >= ?");
            params.add(entry.getValue());
        }
        
        sql.append(" ORDER BY c.nome, s.nome");
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Categoria categoria = new Categoria(rs.getInt("categoria_id"), rs.getString("categoria_nome"));
                    Subcategoria subcategoria = new Subcategoria(rs.getInt("subcategoria_id"), categoria, rs.getString("subcategoria_nome"));
                    motivos.add(mapResultSet(rs, subcategoria));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar motivos com múltiplos filtros: " + e.getMessage());
        }
        return motivos;
    }

    public Map<String, Map<String, Integer>> buscarTotaisAcumulados() {
        Map<String, Map<String, Integer>> totais = new LinkedHashMap<>();
        String sql = "SELECT " +
                    "SUM(falta_interesse_necessidade) as total_falta_interesse, " +
                    "SUM(nao_saber_usar) as total_nao_saber_usar, " +
                    "SUM(nao_ter_onde_acessar) as total_nao_ter_acessar, " +
                    "SUM(ser_muito_caro) as total_muito_caro, " +
                    "SUM(preocupacoes_seguranca_privacidade) as total_seguranca, " +
                    "SUM(evitar_contato_conteudo_perigoso) as total_conteudo_perigoso, " +
                    "SUM(outro_motivo) as total_outro_motivo, " +
                    "c.nome as categoria_nome, " +
                    "s.nome as subcategoria_nome " +
                    "FROM motivos_exclusao m " +
                    "JOIN subcategorias s ON m.id_subcategoria = s.id " +
                    "JOIN categorias c ON s.id_categoria = c.id " +
                    "GROUP BY c.nome, s.nome " +
                    "ORDER BY c.nome, s.nome";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String categoria = rs.getString("categoria_nome");
                String subcategoria = rs.getString("subcategoria_nome");
                String chave = categoria + " - " + subcategoria;

                Map<String, Integer> motivos = new LinkedHashMap<>();
                motivos.put("Falta de Interesse/Necessidade", rs.getInt("total_falta_interesse"));
                motivos.put("Não Saber Usar", rs.getInt("total_nao_saber_usar"));
                motivos.put("Não Ter Onde Acessar", rs.getInt("total_nao_ter_acessar"));
                motivos.put("Ser Muito Caro", rs.getInt("total_muito_caro"));
                motivos.put("Segurança/Privacidade", rs.getInt("total_seguranca"));
                motivos.put("Evitar Conteúdo Perigoso", rs.getInt("total_conteudo_perigoso"));
                motivos.put("Outro Motivo", rs.getInt("total_outro_motivo"));

                totais.put(chave, motivos);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar totais acumulados: " + e.getMessage());
        }
        return totais;
    }

    public List<MotivoExclusao> buscarTodosMotivos() {
        List<MotivoExclusao> motivos = new ArrayList<>();
        String sql = "SELECT m.*, s.id as subcategoria_id, s.nome as subcategoria_nome, " +
                    "c.id as categoria_id, c.nome as categoria_nome " +
                    "FROM motivos_exclusao m " +
                    "JOIN subcategorias s ON m.id_subcategoria = s.id " +
                    "JOIN categorias c ON s.id_categoria = c.id " +
                    "ORDER BY c.nome, s.nome";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Categoria categoria = new Categoria(rs.getInt("categoria_id"), rs.getString("categoria_nome"));
                    Subcategoria subcategoria = new Subcategoria(rs.getInt("subcategoria_id"), categoria, rs.getString("subcategoria_nome"));
                    motivos.add(mapResultSet(rs, subcategoria));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar todos os motivos: " + e.getMessage());
        }
        return motivos;
    }

    public List<MotivoExclusao> buscarPorSubcategoria(Subcategoria subcategoria) {
        List<MotivoExclusao> motivos = new ArrayList<>();
        String sql = "SELECT * FROM motivos_exclusao WHERE id_subcategoria = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, subcategoria.getId());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    motivos.add(mapResultSet(rs, subcategoria));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar motivos: " + e.getMessage());
        }
        return motivos;
    }

    private MotivoExclusao mapResultSet(ResultSet rs, Subcategoria subcategoria) throws SQLException {
        return new MotivoExclusao(
                rs.getInt("id"),
                subcategoria,
                rs.getInt("falta_interesse_necessidade"),
                rs.getInt("nao_saber_usar"),
                rs.getInt("nao_ter_onde_acessar"),
                rs.getInt("ser_muito_caro"),
                rs.getInt("preocupacoes_seguranca_privacidade"),
                rs.getInt("evitar_contato_conteudo_perigoso"),
                rs.getInt("outro_motivo")
        );
    }

    /**
     * Busca motivos de exclusão digital filtrando por IDs de categoria e subcategoria, e tipo de motivo.
     * 
     * @param categoriaId ID da categoria (0 para todas)
     * @param subcategoriaId ID da subcategoria (0 para todas)
     * @param tipoMotivo Coluna do motivo específico a ser filtrado
     * @return Lista de motivos de exclusão que atendem aos critérios
     */
    public List<MotivoExclusao> buscarPorIds(int categoriaId, int subcategoriaId, String tipoMotivo) {
        List<MotivoExclusao> motivos = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT m.*, s.id as subcategoria_id, s.nome as subcategoria_nome, ")
           .append("c.id as categoria_id, c.nome as categoria_nome, ")
           .append("m.").append(tipoMotivo).append(" as valor_filtrado ")
           .append("FROM motivos_exclusao m ")
           .append("JOIN subcategorias s ON m.id_subcategoria = s.id ")
           .append("JOIN categorias c ON s.id_categoria = c.id ")
           .append("WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (categoriaId > 0) {
            sql.append("AND c.id = ? ");
            params.add(categoriaId);
        }

        if (subcategoriaId > 0) {
            sql.append("AND s.id = ? ");
            params.add(subcategoriaId);
        }

        sql.append("AND m.").append(tipoMotivo).append(" > 0 ");

        sql.append("ORDER BY c.nome, s.nome, m.").append(tipoMotivo).append(" DESC");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setInt(i + 1, (Integer) params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Categoria categoria = new Categoria(rs.getInt("categoria_id"), rs.getString("categoria_nome"));
                    Subcategoria subcategoria = new Subcategoria(rs.getInt("subcategoria_id"), categoria, rs.getString("subcategoria_nome"));
                    motivos.add(mapResultSet(rs, subcategoria));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar motivos por IDs: " + e.getMessage());
        }
        return motivos;
    }
}
