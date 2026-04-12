package com.example.projects.intermediate.quiz;

import com.example.projects.intermediate.quiz.dao.QuestionDAO;
import com.example.projects.intermediate.quiz.dao.QuizDAO;
import com.example.projects.intermediate.quiz.dao.UserDAO;
import com.example.projects.intermediate.quiz.entity.Question;
import com.example.projects.intermediate.quiz.entity.Quiz;
import com.example.projects.intermediate.quiz.entity.User;

import java.util.ArrayList;
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
        QuestionDAO questionDAO = new QuestionDAO();
        boolean loggedIn = false;
        User currentUser = null;

        while (!loggedIn) {
            System.out.println("1. Log in");
            System.out.println("2. Sign up");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                while (true) {
                    System.out.println("Username: ");
                    String enterUsername = scanner.nextLine();

                    boolean checkUsername = userDAO.checkUsername(enterUsername);
                    if (!checkUsername) {
                        System.out.println("User doesn't exist!");
                        break;
                    }

                    System.out.println("Password: ");
                    String enterPassword = scanner.nextLine();

                    User user = userDAO.userLogIn(enterUsername);

                    if (user == null || !user.getPassword().equals(enterPassword)) {
                        System.out.println("Invalid password!");
                        break;
                    } else {
                        currentUser = user;
                        loggedIn = true;
                        System.out.println("Welcome, " + currentUser.getUsername() + "!");
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
                userDAO.createUser(newUser);

                System.out.println("User created!");

            } else {
                System.exit(0);
            }
        }

        //List<User> results = userDAO.getAllUsers();
        //System.out.println(results);

        //UserDAO check user and stuff
        // create new user, enter username, password, choose (student/teacher)


        while (loggedIn) {

            System.out.println("1. Start quiz");
            System.out.println("2. Create quiz");
            System.out.println("3. View quizzes");
            System.out.println("4. Exit/logout");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("");
            } else if (choice == 2) {
                System.out.println("Quiz Title: ");
                String quizTitle = scanner.nextLine();
                System.out.println("Quiz Category: ");
                String quizCategory = scanner.nextLine();

                Quiz newQuiz = new Quiz(quizTitle, null, quizCategory, currentUser.getId());

                boolean createQuestions = false;
                if (quizDAO.createQuiz(newQuiz)) {
                    System.out.println("Quiz created!");
                    createQuestions = true;
                }

                while (createQuestions) {
                    System.out.println("Enter a question: ");
                    String questionTitle = scanner.nextLine();

                    System.out.println("How many options should the question have: ");
                    int optionAmount = scanner.nextInt();
                    scanner.nextLine();

                    List<String> options = new ArrayList<>();
                    for (int i = 1; i <= optionAmount; i++) {
                        System.out.println("Option " + i + ": ");
                        String optionText = scanner.nextLine();

                        options.add(optionText);
                    }

                    System.out.println("Which of these is the correct answer?");
                    String correctAnswer = scanner.nextLine();

                    Question newQuestion = new Question(questionTitle, options, correctAnswer);
                    newQuestion.setQuizId(newQuiz.getId());
                    questionDAO.createQuestion(newQuestion);

                    System.out.println("Would you like to make an another question? (y/n)");
                    String moreQuestion = scanner.nextLine();

                    if (moreQuestion.equals("n") || moreQuestion.equals("N")) {
                        createQuestions = false;
                    } else {
                        System.out.println();
                    }
                }
            } else if (choice == 3) {
                int creatorId = currentUser.getId();
                List<Quiz> results = quizDAO.getUserQuizzes(creatorId);

                if (results.isEmpty()) {
                    System.out.println("No quizzes have been created");
                } else {
                    for (Quiz quiz : results) {
                        System.out.println(quiz);
                    }
                }
            } else if (choice == 4) {
                loggedIn = false;
            }
        }
    }
}
