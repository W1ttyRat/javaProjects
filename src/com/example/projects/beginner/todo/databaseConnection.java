package com.example.projects.beginner.todo;

import java.sql.*;

public class databaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/javaTodo";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
