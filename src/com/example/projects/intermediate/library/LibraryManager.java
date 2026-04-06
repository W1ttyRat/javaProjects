package com.example.projects.intermediate.library;

import java.sql.*;
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
}
