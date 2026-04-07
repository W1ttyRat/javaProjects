package com.example.projects.intermediate.library;

import com.example.projects.intermediate.library.dto.ActiveRentalInfo;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class LibraryManagementApp {
    public static void main(String[] args) {
        System.out.println("--- Library Management System ---");
        /*
         * TO-DO LIST:
         * [ ] Define Book and Member classes.
         * [ ] Create a system to track "Checked Out" vs "Available" books.
         * [ ] Implement search functionality (by title or author).
         * [ ] Track due dates and calculate late fees.
         * [ ] Use a database (SQLite/H2) or JSON file for persistence.
         *
         * HOW IT WORKS:
         * 1. The librarian adds books and members to the system.
         * 2. Members search for books by title or author.
         * 3. Books are checked out and return dates are set.
         * 4. The system updates the availability status of books.
         * 5. The system calculates late fees for overdue books.
         */
        Scanner scanner = new Scanner(System.in);

        LibraryManager libraryManager = new LibraryManager();

        while (true) {
            System.out.println("1. Librarian");
            System.out.println("2. User");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                //librarian
                boolean librarianView = true;
                while (librarianView) {
                    System.out.println("1. View all books");
                    System.out.println("2. Enter a new book");
                    System.out.println("3. View all currently rented books");
                    System.out.println("4. Search currently rented books by title");
                    System.out.println("5. Return a book");
                    System.out.println("6. Rent a book");
                    System.out.println("7. Delete a book");
                    System.out.println("8. View late fees");
                    System.out.println("9. Exit");

                    int librarianChoice = scanner.nextInt();
                    scanner.nextLine();

                    switch (librarianChoice) {
                        case 1 -> {
                            List<Book> results = libraryManager.getAllBooks();

                            if (results.isEmpty()) {
                                System.out.println("No books in the library?");
                            } else {
                                for (Book books : results) {
                                    System.out.println(books);
                                }
                            }
                        }
                        case 2 -> {

                            System.out.println("title: ");
                            String newTitle = scanner.nextLine();
                            System.out.println("Author: ");
                            String newAuthor = scanner.nextLine();

                            Book newBook = new Book(newTitle, newAuthor);
                            libraryManager.addBook(newBook);

                            System.out.println("Added a new book to the library");
                        }
                        case 3 -> {
                            List<ActiveRentalInfo> results = libraryManager.getActiveRentals();

                            if (results.isEmpty()) {
                                System.out.println("No books are being rented");
                            } else {
                                for (ActiveRentalInfo activeRentalInfo : results) {
                                    System.out.println(activeRentalInfo);
                                }
                            }
                        }
                        case 4 -> {
                            System.out.println("Enter the title of the book");
                            String searchTitle = scanner.nextLine();

                            List<ActiveRentalInfo> results = libraryManager.searchActiveRentals(searchTitle);

                            if (results.isEmpty()) {
                                System.out.println("No books are rented with the given title");
                            } else {
                                for (ActiveRentalInfo activeRentalInfo : results) {
                                    System.out.println(activeRentalInfo);
                                }
                            }
                        }
                        case 5 -> {
                            System.out.println("Enter the id of the book");
                            int returnId = scanner.nextInt();
                            scanner.nextLine();

                            Book book = libraryManager.getBookById(returnId);

                            if (book == null) {
                                System.out.println("Error: book doesn't exist");
                            } else if (book.isAvailable()) {
                                System.out.println("Can't return a book that isn't rented");
                            } else {
                                if (libraryManager.returnBook(returnId)) {
                                    System.out.println("Book returned");
                                } else {
                                    System.out.println("Error: no book returned");
                                }
                            }
                        }
                        case 6 -> {
                            System.out.println("Enter Book ID to rent: ");
                            int rentId = scanner.nextInt();
                            scanner.nextLine();

                            Book targetBook = libraryManager.getBookById(rentId);

                            if (targetBook == null) {
                                System.out.println("Error: Book not found");
                            } else if (!targetBook.isAvailable()) {
                                System.out.println("Error: This book is already rented out");
                            } else {
                                System.out.println("Enter name: ");
                                String memberName = scanner.nextLine();

                                if (libraryManager.rentBook(rentId, memberName)) {
                                    System.out.println("Successfully rented");
                                }
                            }
                        }
                        case 7 -> {
                            System.out.println("Enter the Book ID: ");
                            int deleteId = scanner.nextInt();

                            if (libraryManager.removeBook(deleteId)) {
                                System.out.println("Successfully deleted Book ID #" + deleteId);
                            } else {
                                System.out.println("Error: No book was found with ID #" + deleteId);
                            }
                        }
                        case 8 -> {
                            System.out.println("function in progress");
                        }
                        case 9 -> librarianView = false;
                        default -> System.out.println("Enter a number");
                    }
                }

            } else if (choice == 2) {
                //user
                /*List<Book> allBooks = libraryManager.getAllBooks();

                for (Book book : allBooks) {
                    System.out.println(book);
                }*/
                boolean userView = true;
                while (userView) {
                    System.out.println("1. find all books");
                    System.out.println("2. find by title");
                    System.out.println("3. find by author");
                    System.out.println("4. rent a book");
                    System.out.println("5. exit");
                    int userChoice = scanner.nextInt();
                    scanner.nextLine();
                    switch (userChoice) {
                        case 1 -> {
                            List<Book> searchResults = libraryManager.getAllBooks();
                            if (searchResults.isEmpty()) {
                                System.out.println("No books in the library");
                            } else {
                                for (Book books : searchResults) {
                                    System.out.println(books);
                                }
                            }
                        }
                        case 2 -> {
                            System.out.println("Enter the title: ");
                            String searchTitle = scanner.nextLine();

                            List<Book> searchResults = libraryManager.getBookByTitle(searchTitle);

                            if (searchResults.isEmpty()) {
                                System.out.println("No books were found");
                            } else {
                                for (Book books : searchResults) {
                                    System.out.println(books);
                                }
                            }
                        }
                        case 3 -> {
                            System.out.println("Enter the author: ");
                            String searchAuthor = scanner.nextLine();

                            List<Book> searchResults = libraryManager.getBookByAuthor(searchAuthor);

                            if (searchResults.isEmpty()) {
                                System.out.println("No books were found");
                            } else {
                                for (Book books : searchResults) {
                                    System.out.println(books);
                                }
                            }
                        }
                        case 4 -> {
                            System.out.println("Enter Book ID to rent: ");
                            int rentId = scanner.nextInt();
                            scanner.nextLine();

                            Book targetBook = libraryManager.getBookById(rentId);

                            if (targetBook == null) {
                                System.out.println("Error: Book not found");
                            } else if (!targetBook.isAvailable()) {
                                System.out.println("Error: This book is already rented out");
                            } else {
                                System.out.println("Enter name: ");
                                String memberName = scanner.nextLine();

                                if (libraryManager.rentBook(rentId, memberName)) {
                                    System.out.println("Successfully rented");
                                }
                            }
                        }
                        case 5 -> userView = false;
                        default -> System.out.println("Enter a number");
                    }
                }

            } else if (choice == 3) {
                //exit
                System.exit(0);
            } else {
                System.out.println("Enter a number");
            }
        }
    }
}
