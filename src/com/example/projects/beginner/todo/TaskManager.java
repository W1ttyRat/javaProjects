package com.example.projects.beginner.todo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
