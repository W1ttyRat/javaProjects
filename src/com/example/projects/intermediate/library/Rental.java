package com.example.projects.intermediate.library;

import java.time.LocalDate;

public class Rental {

    private int id;
    private int bookId;
    private String memberName;
    private LocalDate rentDate;
    private LocalDate returnDate;

    public Rental(int id, int bookId, String memberName, LocalDate rentDate, LocalDate returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.memberName = memberName;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
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

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", memberName='" + memberName + '\'' +
                ", rentDate=" + rentDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
