package com.example.projects.intermediate.library.dto;

public class ActiveRentalInfo {

    private int id;
    private String title;
    private String memberName;
    private String rentDate;

    public ActiveRentalInfo(int id, String title, String memberName, String rentDate) {
        this.id = id;
        this.title = title;
        this.memberName = memberName;
        this.rentDate = rentDate;
    }

    public ActiveRentalInfo(String title, String memberName, String rentDate) {
        this.title = title;
        this.memberName = memberName;
        this.rentDate = rentDate;
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

    public String getRentDate() {
        return rentDate;
    }

    @Override
    public String toString() {
        return "ActiveRentalInfo{" +
                "title='" + title + '\'' +
                ", memberName='" + memberName + '\'' +
                ", rentDate='" + rentDate + '\'' +
                '}';
    }
}