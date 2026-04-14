package com.example.projects.intermediate.quiz.dao;

import com.example.projects.intermediate.quiz.entity.Quiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Quiz> getUserQuizzes(int creatorId) {
        List<Quiz> quizzes = new ArrayList<>();

        String sql = "SELECT id, title, category FROM quizzes WHERE creator_id = ?";

        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, creatorId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String category = rs.getString("category");

                    Quiz quiz = new Quiz(id, title, category);
                    quizzes.add(quiz);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return quizzes;
    }

    public boolean deleteQuiz(int id) {

        String sql = "DELETE FROM quizzes WHERE id = ?";

        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsDeleted = pstmt.executeUpdate();

            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteQuiz(Quiz quiz) {
        return deleteQuiz(quiz.getId());
    }

    public Quiz getQuizById(int id) {

        String sql = "SELECT id, title, category, creator_id FROM quizzes WHERE id = ?";

        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Quiz quiz = new Quiz(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("category")
                    );
                    quiz.setCreatorId(rs.getInt("creator_id"));
                    return quiz;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean updateQuiz(Quiz quiz) {
        String sql = "UPDATE quizzes SET title = ?, category = ? WHERE id = ?";

        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, quiz.getTitle());
            pstmt.setString(2, quiz.getCategory());
            pstmt.setInt(3, quiz.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Quiz> searchQuizByTitle(String searchTitle) {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT id, title, category FROM quizzes WHERE title ILIKE ? LIMIT 20";

        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + searchTitle + "%");


            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    int id = rs.getInt("id");
                    String title  = rs.getString("title");
                    String category = rs.getString("category");

                    Quiz quiz = new Quiz(id, title, category);
                    quizzes.add(quiz);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return quizzes;
    }
}
