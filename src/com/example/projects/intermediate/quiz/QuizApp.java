package com.example.projects.intermediate.quiz;

import com.example.projects.intermediate.quiz.dao.QuizDAO;
import com.example.projects.intermediate.quiz.dao.UserDAO;
import com.example.projects.intermediate.quiz.entity.Quiz;
import com.example.projects.intermediate.quiz.entity.User;

import java.util.List;
import java.util.Scanner;

public class QuizApp {
    public static void main(String[] args) {
        System.out.println("--- Online Quiz Application ---");
        /*
         * TO-DO LIST:
         * [ ] Create a Question class (text, options, correct answer).
         * [ ] Load questions from a file or internal list.
         * [ ] Implement a timer for each question.
         * [ ] Calculate the final score and display a summary.
         * [ ] Add different categories/difficulty levels.
         *
         * HOW IT WORKS:
         * 1. The user selects a quiz category or starts a random quiz.
         * 2. Questions are presented one by one with a time limit.
         * 3. The application checks the user's answers and keeps score.
         * 4. A summary report is displayed at the end.
         */
        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        QuizDAO quizDAO = new QuizDAO();
        boolean loggedIn = false;

        System.out.println("1. Log in");
        System.out.println("2. Sign up");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            while (true) {
                System.out.println("Username: ");
                String enterUsername = scanner.nextLine();

                User user = userDAO.findByUsername(enterUsername);

                System.out.println("Password: ");
                String enterPassword = scanner.nextLine();

                if (!enterPassword.equals(user.getPassword())) {
                    System.out.println("Wrong password");
                } else if (enterPassword.equals(user.getPassword())) {
                    System.out.println("Logged in");
                    loggedIn = true;
                    break;
                }
            }
        } else if (choice == 2) {
            System.out.println("Enter username:");
            String createUsername = scanner.nextLine();
            System.out.println("Enter password");
            String createPassword = scanner.nextLine();
            System.out.println("Are you a teacher/student");
            String createRole = scanner.nextLine().toLowerCase().strip();

            User newUser = new User(createUsername, createPassword, createRole);
        }


        //List<User> results = userDAO.getAllUsers();
        //System.out.println(results);

        //UserDAO check user and stuff
        // create new user, enter username, password, choose (student/teacher)


        while (loggedIn) {

            System.out.println("1. Start quiz");
            System.out.println("2. Create quiz");
            System.out.println("3. Exit/logout");
            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("");
            } else if (choice == 2) {
                System.out.println("Title: ");
                String quizTitle = scanner.nextLine();
                System.out.println("Category: ");
                String quizCategory = scanner.nextLine();

                //quizDAO.createQuiz(quizTitle, quizCategory, ());

                // create questions

                // while loop
                // enter question
                // enter min 3 answers
                // choose the correct answer
                // save questions, link to quiz_id


            }
        }
    }
}
