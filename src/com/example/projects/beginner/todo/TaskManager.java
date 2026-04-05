package com.example.projects.beginner.todo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.projects.beginner.todo.databaseConnection.getConnection;

public class TaskManager {

    public List<Task> getAllTasks() throws SQLException {
        List<Task> allTasks = new ArrayList<>();
        String sql = "SELECT id, title, description, status FROM tasks";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String status = rs.getString("status");

                Task task = new Task(id, title, description, status);
                allTasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allTasks;
    }

    public void addTask(Task task) {

        String sql = "INSERT INTO tasks (title, description, status) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getDescription());
            pstmt.setString(3, task.getStatus());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void editTask(Task task) {

        String sql = "UPDATE tasks SET title = ?, description = ?, status = ? WHERE id = ?";

        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getDescription());
            pstmt.setString(3, task.getStatus());
            pstmt.setInt(4, task.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Task getTaskById(int id) {

        String sql = "SELECT id, title, description, status FROM tasks WHERE id = ?";

        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    return new Task(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
