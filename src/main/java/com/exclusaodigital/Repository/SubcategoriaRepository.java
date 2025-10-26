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
