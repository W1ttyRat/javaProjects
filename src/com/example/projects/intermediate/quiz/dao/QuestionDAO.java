package com.example.projects.intermediate.quiz.dao;

import com.example.projects.intermediate.quiz.entity.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Question> getQuestionsForQuiz(int quizId) {
        List<Question> questionList = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE quiz_id = ?";

        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, quizId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    int questionId = rs.getInt("id");
                    String title = rs.getString("title");
                    String correctAnswer = rs.getString("correct_answer");

                    List<String> options = getOptionsForQuestion(questionId);

                    Question question = new Question(title, options, correctAnswer);
                    question.setId(questionId);
                    question.setQuizId(quizId);

                    questionList.add(question);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return questionList;
    }

    private List<String> getOptionsForQuestion(int questionId) {
        List<String> options = new ArrayList<>();
        String sql = "SELECT option_text FROM question_options WHERE question_id = ?";

        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, questionId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    String optionText = rs.getString("option_text");

                    options.add(optionText);

                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return options;
    }

    public boolean updateQuestion(Question question) {
        String sqlUpdateQuest = "UPDATE questions SET title = ?, correct_answer = ? WHERE id = ?";
        String sqlDeleteOptions = "DELETE FROM question_options WHERE question_id = ?";
        String sqlInsertOption = "INSERT INTO question_options (question_id, option_text) VALUES (?, ?)";

        try (Connection conn = getConnection()) {
            try {
                conn.setAutoCommit(false);

                try (PreparedStatement pstmt1 = conn.prepareStatement(sqlUpdateQuest)) {
                    pstmt1.setString(1, question.getTitle());
                    pstmt1.setString(2, question.getAnswer());
                    pstmt1.setInt(3, question.getId());
                    pstmt1.executeUpdate();
                }

                try (PreparedStatement pstmt2 = conn.prepareStatement(sqlDeleteOptions)) {
                    pstmt2.setInt(1, question.getId());
                    pstmt2.executeUpdate();
                }

                try (PreparedStatement pstmt3 = conn.prepareStatement(sqlInsertOption)) {
                    for (String optionText : question.getOptions()) {
                        pstmt3.setInt(1, question.getId());
                        pstmt3.setString(2, optionText);
                        pstmt3.executeUpdate();
                    }
                }
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
