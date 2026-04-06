package com.example.projects.intermediate.library;

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
                    System.out.println("3. Exit");

                    int librarianChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (librarianChoice == 1) {
                        List<Book> results = libraryManager.getAllBooks();

                        if (results.isEmpty()) {
                            System.out.println("No books in the library?");
                        } else {
                            for (Book books : results) {
                                System.out.println(books);
                            }
                        }

                    } else if (librarianChoice == 2) {

                        System.out.println("title: ");
                        String newTitle = scanner.nextLine();
                        System.out.println("Author: ");
                        String newAuthor = scanner.nextLine();

                        Book newBook = new Book(newTitle, newAuthor);
                        libraryManager.addBook(newBook);

                        System.out.println("Added a new book to the library");

                    } else if (librarianChoice == 3) {
                        librarianView = false;
                    } else {
                        System.out.println("Enter a number");
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
                    System.out.println("4. exit");
                    int userChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (userChoice == 1) {
                        List<Book> searchResults = libraryManager.getAllBooks();
                        if (searchResults.isEmpty()) {
                            System.out.println("No books in the library");
                        } else {
                            for (Book books : searchResults) {
                                System.out.println(books);
                            }
                        }
                    }
                    if (userChoice == 2) {
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

                    } else if (userChoice == 3) {
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
                    } else if (userChoice == 4) {
                        userView = false;
                    } else {
                        System.out.println("Enter a number");
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
