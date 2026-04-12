package com.example.projects.intermediate.quiz.dao;

import com.example.projects.intermediate.quiz.entity.User;

import javax.xml.transform.Result;
import java.sql.*;
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

    public boolean createUser(User user) {

        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRole());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setId(rs.getInt(1));
                    }
                }
            }

            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public User findByUsername(String username) {

        String sql = "SELECT id, username, password, role FROM users WHERE username = ?";

        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
