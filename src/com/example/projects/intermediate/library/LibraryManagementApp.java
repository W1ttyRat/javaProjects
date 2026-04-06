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
                return;
                //librarian
            } else if (choice == 2) {
                //user
                /*List<Book> allBooks = libraryManager.getAllBooks();

                for (Book book : allBooks) {
                    System.out.println(book);
                }*/
                boolean userView = true;
                while (userView) {
                    System.out.println("1. find by title");
                    System.out.println("2. find by author");
                    System.out.println("3. exit");
                    int userChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (userChoice == 1) {
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
