package com.example.projects.intermediate.library;

import java.time.LocalDate;

public class Rental {

    private int id;
    private int bookId;
    private String memberName;
    private LocalDate rentDate;
    private LocalDate returnDate;
    private LocalDate dueDate;

    public Rental(LocalDate rentDate, String memberName, int bookId) {
        this.rentDate = rentDate;
        this.memberName = memberName;
        this.bookId = bookId;
    }

    public Rental(int id, int bookId, String memberName, LocalDate rentDate, LocalDate dueDate) {
        this.id = id;
        this.bookId = bookId;
        this.memberName = memberName;
        this.rentDate = rentDate;
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public LocalDate getRentDate() {
        return rentDate;
    }

    public void setRentDate(LocalDate rentDate) {
        this.rentDate = rentDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", memberName='" + memberName + '\'' +
                ", rentDate=" + rentDate +
                ", returnDate=" + returnDate +
                ", dueDate=" + dueDate +
                '}';
    }
}
