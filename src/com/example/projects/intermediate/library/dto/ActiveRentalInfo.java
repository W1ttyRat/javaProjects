package com.example.projects.intermediate.library.dto;

import java.time.LocalDate;

public class ActiveRentalInfo {

    private int id;
    private String title;
    private String memberName;
    private LocalDate rentDate;
    private LocalDate dueDate;
    private double fineAmount;

    public ActiveRentalInfo(int id, String title, String memberName, LocalDate rentDate, LocalDate dueDate) {
        this.id = id;
        this.title = title;
        this.memberName = memberName;
        this.rentDate = rentDate;
        this.dueDate = dueDate;
    }

    public ActiveRentalInfo(int id, String title, String memberName, LocalDate rentDate, LocalDate dueDate, double fineAmount) {
        this.id = id;
        this.title = title;
        this.memberName = memberName;
        this.rentDate = rentDate;
        this.dueDate = dueDate;
        this.fineAmount = fineAmount;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMemberName() {
        return memberName;
    }

    public LocalDate getRentDate() {
        return rentDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    @Override
    public String toString() {
        return "ActiveRentalInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", memberName='" + memberName + '\'' +
                ", rentDate=" + rentDate +
                ", dueDate=" + dueDate +
                ", fineAmount=" + fineAmount +
                '}';
    }
}