package com.example.projects.intermediate.quiz.dao;

import com.example.projects.intermediate.quiz.entity.Quiz;

import java.sql.*;

import static com.example.projects.beginner.todo.databaseConnection.getConnection;

public class QuizDAO {

    public boolean createQuiz(Quiz quiz) {

        String sql = "INSERT INTO quizzes (title, category, creator_id) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, quiz.getTitle());
            pstmt.setString(2, quiz.getCategory());
            pstmt.setInt(3, quiz.getCreatorId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        quiz.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
