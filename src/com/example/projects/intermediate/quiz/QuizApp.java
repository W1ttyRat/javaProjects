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


        //List<User> results = userDAO.getAllUsers();
        //System.out.println(results);

        System.out.println("Username: ");
        String enterUsername = scanner.nextLine();
        System.out.println("Password: ");
        String enterPassword = scanner.nextLine();

        //UserDAO check user and stuff

        System.out.println("1. Start quiz");
        System.out.println("2. Create quiz");
        System.out.println("3. Exit/logout");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.println("");
        } else if (choice == 2) {
            System.out.println("Title: ");
            String quizTitle = scanner.nextLine();
            System.out.println("Category: ");
            String quizCategory = scanner.nextLine();

            Quiz newQuiz = new Quiz(quizTitle, null, quizCategory);
            newQuiz.setCreatorId(userDAO.getUserById());

            if (quizDAO.createQuiz(newQuiz)) {
                System.out.println("Quiz saved! new id is: " + newQuiz.getId());
            }

            // create Quiz, save to DB and then get ID

            // create questions

            // while loop
            // enter question
            // enter min 3 answers
            // choose the correct answer
            // save questions, link to quiz_id


        }
    }
}
