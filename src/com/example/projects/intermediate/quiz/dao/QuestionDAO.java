package com.example.projects.intermediate.quiz.dao;

import com.example.projects.intermediate.quiz.entity.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.projects.beginner.todo.databaseConnection.getConnection;

public class QuestionDAO {

    public boolean createQuestion(Question question) {

        String sqlQuestion = "INSERT INTO questions (quiz_id, title, correct_answer) VALUES (?, ?, ?)";
        String sqlOption = "INSERT INTO question_options (question_id, option_text) VALUES (?, ?)";

        try (Connection conn = getConnection()) {
            try {
                conn.setAutoCommit(false);
                try (PreparedStatement pstmt1 = conn.prepareStatement(sqlQuestion, PreparedStatement.RETURN_GENERATED_KEYS)) {

                    pstmt1.setInt(1, question.getQuizId());
                    pstmt1.setString(2, question.getTitle());
                    pstmt1.setString(3, question.getAnswer());

                    int affectedRows = pstmt1.executeUpdate();

                    if (affectedRows > 0) {
                        try (ResultSet generatedKeys = pstmt1.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                question.setId(generatedKeys.getInt(1));
                            }
                        }
                    }
                }
                try (PreparedStatement pstmt2 = conn.prepareStatement(sqlOption)) {

                    for (String optionText : question.getOptions()) {
                        pstmt2.setInt(1, question.getId());
                        pstmt2.setString(2, optionText);
                        pstmt2.executeUpdate();
                    }
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                conn.rollback();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
