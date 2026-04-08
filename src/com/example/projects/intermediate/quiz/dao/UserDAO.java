package com.example.projects.intermediate.quiz.dao;

import com.example.projects.intermediate.quiz.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.example.projects.beginner.todo.databaseConnection.getConnection;

public class UserDAO {

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();

        String sql = "SELECT username, role FROM users";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                String username = rs.getString("username");
                String role = rs.getString("role");

                User user = new User(username, role);
                allUsers.add(user);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allUsers;
    }

    public User getUserById(int id) {
    }
}
