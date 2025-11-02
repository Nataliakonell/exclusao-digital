package com.exclusaodigital.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.exclusaodigital.Model.Categoria;
import com.exclusaodigital.Model.Subcategoria;
import com.exclusaodigital.Util.DatabaseConnection;

public class SubcategoriaRepository {

    public Subcategoria buscarPorId(int id, Categoria categoria) {
        String sql = "SELECT * FROM subcategorias WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs, categoria);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar subcategoria por ID: " + e.getMessage());
        }
        return null;
    }

    public List<Subcategoria> buscarPorNome(String nome, Categoria categoria) {
        List<Subcategoria> subcategorias = new ArrayList<>();
        String sql = "SELECT * FROM subcategorias WHERE nome LIKE ? AND id_categoria = ? ORDER BY nome";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, "%" + nome + "%");
            ps.setInt(2, categoria.getId());
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    subcategorias.add(mapResultSet(rs, categoria));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar subcategorias por nome: " + e.getMessage());
        }
        return subcategorias;
    }

    public List<Subcategoria> buscarTodasSubcategorias() {
        List<Subcategoria> subcategorias = new ArrayList<>();
        String sql = "SELECT s.*, c.id as categoria_id, c.nome as categoria_nome " +
                    "FROM subcategorias s " +
                    "JOIN categorias c ON s.id_categoria = c.id " +
                    "ORDER BY c.nome, s.nome";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Categoria categoria = new Categoria(rs.getInt("categoria_id"), rs.getString("categoria_nome"));
                    subcategorias.add(mapResultSet(rs, categoria));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar todas as subcategorias: " + e.getMessage());
        }
        return subcategorias;
    }

    public List<Subcategoria> buscarPorCategoria(Categoria categoria) {
        List<Subcategoria> subcategorias = new ArrayList<>();
        String sql = "SELECT * FROM subcategorias WHERE id_categoria = ? ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, categoria.getId());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    subcategorias.add(mapResultSet(rs, categoria));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar subcategorias: " + e.getMessage());
        }

        return subcategorias;
    }

    // Método auxiliar para mapear ResultSet para Subcategoria
    private Subcategoria mapResultSet(ResultSet rs, Categoria categoria) throws SQLException {
        return new Subcategoria(
                rs.getInt("id"),
                categoria,
                rs.getString("nome")
        );
    }
}
