package com.example.projects.intermediate.quiz.entity;

public class QuestionOptions {

    private int id;
    private int questionId;
    private String optionText;

    public QuestionOptions(String optionText) {
        this.optionText = optionText;
    }

    @Override
    public String toString() {
        return "QuestionOptions{" +
                "id=" + id +
                ", questionId=" + questionId +
                ", optionText='" + optionText + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }
}
