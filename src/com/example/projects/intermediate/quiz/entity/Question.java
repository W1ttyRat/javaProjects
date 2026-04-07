package com.example.projects.intermediate.quiz.entity;

import java.util.List;

public class Question {

    private int id;
    private int quizId;
    private String title;
    private List<String> options;
    private String answer;

    public Question(String title, List<String> options, String answer) {
        this.title = title;
        this.options = options;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", quizId=" + quizId +
                ", title='" + title + '\'' +
                ", options=" + options +
                ", answer='" + answer + '\'' +
                '}';
    }
}
