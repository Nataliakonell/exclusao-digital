package com.exclusaodigital.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.exclusaodigital.Model.Categoria;
import com.exclusaodigital.Util.DatabaseConnection;

public class CategoriaRepository {

    public Categoria buscarPorNome(String nome) {
        String sql = "SELECT * FROM categorias WHERE nome LIKE ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, "%" + nome + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar categoria por nome: " + e.getMessage());
        }
        return null;
    }

    public List<Categoria> buscarPorNomeParcial(String nomeParcial) {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categorias WHERE nome LIKE ? ORDER BY nome";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, "%" + nomeParcial + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    categorias.add(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar categorias por nome parcial: " + e.getMessage());
        }
        return categorias;
    }


    public List<Categoria> buscarTodos() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categorias ORDER BY id";

        try (
                Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                categorias.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar categorias: " + e.getMessage());
        }

        return categorias;
    }

// Método auxiliar para mapear ResultSet para Categoria
    private Categoria mapResultSet(ResultSet rs) throws SQLException {
        return new Categoria(rs.getInt("id"), rs.getString("nome"));
    }

    public Categoria buscarPorId(int id) {
        String sql = "SELECT * FROM categorias WHERE id = ?";
       
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return mapResultSet(rs);
            }
        }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar categoria: " + e.getMessage());
        }

        return null;
    }
}
