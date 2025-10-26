package com.exclusaodigital.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/exclusaodigital";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    // Método que retorna uma conexão pronta para uso
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
