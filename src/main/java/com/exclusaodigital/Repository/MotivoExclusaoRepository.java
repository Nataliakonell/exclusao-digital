package com.exclusaodigital.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.exclusaodigital.Model.MotivoExclusao;
import com.exclusaodigital.Model.Subcategoria;
import com.exclusaodigital.Util.DatabaseConnection;

public class MotivoExclusaoRepository {

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

    // Método auxiliar para mapear ResultSet para MotivoExclusao
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
}
