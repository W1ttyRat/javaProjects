package com.example.projects.intermediate.quiz.entity;

import java.util.List;

public class Quiz {

    private int id;
    private int creatorId;
    private String title;
    private List<Question> questions;
    private String category;

    public Quiz(String title, List<Question> questions, String category, int creatorId) {
        this.title = title;
        this.questions = questions;
        this.category = category;
        this.creatorId = creatorId;
    }

    public Quiz(int id, String title, String category) {
        this.id = id;
        this.title = title;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", creatorId=" + creatorId +
                ", title='" + title + '\'' +
                ", questions=" + questions +
                ", category='" + category + '\'' +
                '}';
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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }
}
