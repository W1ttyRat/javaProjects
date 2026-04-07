package com.example.projects.intermediate.library;

import com.example.projects.intermediate.library.dto.ActiveRentalInfo;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.example.projects.beginner.todo.databaseConnection.getConnection;

public class LibraryManager {
    // This is your "Clerk" role (DAO Layer).
    // Use PreparedStatement and Try-with-Resources here.

    public List<Book> getAllBooks() {
        List<Book> allBooks = new ArrayList<>();
        String sql = "SELECT id, title, author, is_available FROM books";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                boolean isAvailable = rs.getBoolean("is_available");

                Book book = new Book(id, title, author, isAvailable);
                allBooks.add(book);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allBooks;
    }

    public Book getBookById(int id) {

        String sql = "SELECT id, title, author, is_available FROM books WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getBoolean("is_available")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Book> getBookByTitle(String searchTitle) {

        List<Book> allBooks = new ArrayList<>();
        String sql = "SELECT id, title, author, is_available FROM books WHERE title ILIKE ?";

        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + searchTitle + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    boolean isAvailable = rs.getBoolean("is_available");

                    Book book = new Book(id, title, author, isAvailable);
                    allBooks.add(book);

                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allBooks;
    }

    public List<Book> getBookByAuthor(String searchAuthor) {

        List<Book> allBooks = new ArrayList<>();
        String sql = "SELECT id, title, author, is_available FROM books where author ILIKE ?";

        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + searchAuthor + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    boolean isAvailable = rs.getBoolean("is_available");

                    Book book = new Book(id, title, author, isAvailable);
                    allBooks.add(book);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allBooks;
    }

    public void addBook(Book book) {

        String sql = "INSERT INTO books (title, author) VALUES (?, ?)";

        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateAvailability(int id, boolean isAvailable) {

        String sql = "UPDATE books SET is_available = ? WHERE id = ?";

        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(2, id);
            pstmt.setBoolean(1, isAvailable);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean removeBook(int id) {

        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsDeleted = pstmt.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean rentBook(int bookId, String memberName) {

        String sqlInsert = "INSERT INTO rentals (book_id, member_name, rent_date, due_date) VALUES (?, ?, CURRENT_DATE, CURRENT_DATE + INTERVAL '14 days')";
        String sqlUpdate = "UPDATE books SET is_available = false WHERE id = ?";

        try (Connection conn = getConnection()) {

            try (PreparedStatement pstmt1 = conn.prepareStatement(sqlInsert)) {

                pstmt1.setInt(1, bookId);
                pstmt1.setString(2, memberName);
                pstmt1.executeUpdate();

            }

            try (PreparedStatement pstmt2 = conn.prepareStatement(sqlUpdate)) {

                pstmt2.setInt(1, bookId);
                pstmt2.executeUpdate();

            }

            System.out.println("Book successfully rented");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean returnBook(int bookId) {

        String sqlUpdateBook = "UPDATE books SET is_available = TRUE WHERE id = ?";
        String sqlUpdateRental = "UPDATE rentals SET return_date = CURRENT_DATE, fine_amount = ? WHERE book_id = ? AND return_date IS NULL";
        String sqlGetDueDate = "SELECT due_date FROM rentals WHERE book_id = ? AND return_date IS NULL";

        double fine = 0.0;

        try (Connection conn = getConnection()) {

            LocalDate dueDate = null;

            try (PreparedStatement pstmt3 = conn.prepareStatement(sqlGetDueDate)) {

                pstmt3.setInt(1, bookId);

                try (ResultSet rs = pstmt3.executeQuery()) {

                    if (rs.next()) {
                        dueDate = rs.getDate("due_date").toLocalDate();
                    }
                }
            }

            if (dueDate == null) return false;

            LocalDate today = LocalDate.now();

            long daysLate = ChronoUnit.DAYS.between(dueDate, today);

            if (daysLate > 0) {
                fine = daysLate * 0.50;
                System.out.println("This book is " + daysLate + " days late");
                System.out.println("The total fine is: " + fine + "€");

            } else {
                System.out.println("Rental was not late, no fees");
            }

            try (PreparedStatement pstmt1 = conn.prepareStatement(sqlUpdateBook)) {

                pstmt1.setInt(1, bookId);
                pstmt1.executeUpdate();
            }

            try (PreparedStatement pstmt2 = conn.prepareStatement(sqlUpdateRental)) {

                pstmt2.setDouble(1, fine);
                pstmt2.setInt(2, bookId);
                pstmt2.executeUpdate();
            }
            System.out.println("Book is successfully returned");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<ActiveRentalInfo> getActiveRentals() {
        List<ActiveRentalInfo> infoList = new ArrayList<>();

        String sql = "SELECT b.id, b.title, r.member_name, r.rent_date, r.due_date " +
                "FROM books b " +
                "JOIN rentals r ON b.id = r.book_id " +
                "WHERE r.return_date IS NULL";

        try (Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String title = rs.getString("title");
                String memberName = rs.getString("member_name");
                LocalDate rentDate = rs.getDate("rent_date").toLocalDate();
                LocalDate dueDate = rs.getDate("due_date").toLocalDate();


                ActiveRentalInfo activeRentalInfo = new ActiveRentalInfo(id, title, memberName, rentDate, dueDate);
                infoList.add(activeRentalInfo);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return infoList;
    }

    public List<ActiveRentalInfo> searchActiveRentals(String searchTitle) {
        List<ActiveRentalInfo> searchResult = new ArrayList<>();

        String sql = "SELECT b.id, b.title, r.member_name, r.rent_date, r.due_date " +
                "FROM books b " +
                "JOIN rentals r ON b.id = r.book_id " +
                "WHERE r.return_date IS NULL " +
                "AND b.title ILIKE ?";

        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + searchTitle + "%");

            try (ResultSet rs = pstmt.executeQuery()) {

                while(rs.next()) {

                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String memberName = rs.getString("member_name");
                    LocalDate returnDate = rs.getDate("rent_date").toLocalDate();
                    LocalDate dueDate = rs.getDate("due_date").toLocalDate();

                    ActiveRentalInfo activeRentalInfo = new ActiveRentalInfo(id, title, memberName, returnDate, dueDate);
                    searchResult.add(activeRentalInfo);
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return searchResult;
    }

    public List<ActiveRentalInfo> getLateFeeHistory() {
        List<ActiveRentalInfo> lateFeeList = new ArrayList<>();

        String sql = "SELECT b.id, b.title, r.member_name, r.rent_date, r.due_date, r.fine_amount " +
                "FROM books b " +
                "JOIN rentals r ON b.id = r.book_id " +
                "WHERE r.return_date IS NOT NULL " +
                "AND r.fine_amount > 0";

        try (Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String title = rs.getString("title");
                String memberName = rs.getString("member_name");
                LocalDate rentDate = rs.getDate("rent_date").toLocalDate();
                LocalDate dueDate = rs.getDate("due_date").toLocalDate();
                double fineAmount = rs.getDouble("fine_amount");

                ActiveRentalInfo activeRentalInfo = new ActiveRentalInfo(id, title, memberName, rentDate, dueDate, fineAmount);
                lateFeeList.add(activeRentalInfo);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lateFeeList;
    }
}
