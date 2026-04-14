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

                System.out.println("Search a quiz by title");
                String searchQuizTitle = scanner.nextLine();

                List<Quiz> results = quizDAO.searchQuizByTitle(searchQuizTitle);

                if (results.isEmpty()) {
                    System.out.println("No quizzes found");
                } else {
                    for (int i = 0; i < results.size(); i++) {
                        System.out.println((i + 1) + ". [ID: " + results.get(i).getId() + "] " + results.get(i).getTitle() + " (" + results.get(i).getCategory() + ")");
                    }
                    System.out.println();
                }




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
                while (true) {
                    int creatorId = currentUser.getId();
                    List<Quiz> results = quizDAO.getUserQuizzes(creatorId);

                    if (results.isEmpty()) {
                        System.out.println("No quizzes have been created");
                    } else {
                        /* for (Quiz quiz : results) {
                            System.out.println(quiz.getId() + ". " + quiz.getTitle());
                        } */

                        for (int i = 0; i < results.size(); i++) {
                            System.out.println((i+1) + ". " + "[ID: " + results.get(i).getId() + "] " + results.get(i).getTitle());
                        }
                    }

                    System.out.println("\n1. Delete Quiz");
                    System.out.println("2. Edit quiz");
                    System.out.println("3. Exit view");
                    int choiceQuiz = scanner.nextInt();
                    scanner.nextLine();

                    if (choiceQuiz == 1) {
                        System.out.println("Enter the id of the quiz you wish to delete: ");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine();
                        if (quizDAO.deleteQuiz(deleteId)) {
                            System.out.println("Deleted!");
                        } else {
                            System.out.println("Error!");
                        }
                    } else if (choiceQuiz == 2) {
                        System.out.println("Enter the id of the quiz you wish to edit: ");
                        int editId = scanner.nextInt();
                        scanner.nextLine();

                        Quiz myQuiz = quizDAO.getQuizById(editId);

                        if (myQuiz == null) {
                            System.out.println("Quiz not found!");
                        } else {

                            List<Question> questions = questionDAO.getQuestionsForQuiz(editId);
                            myQuiz.setQuestions(questions);

                            System.out.println("Loaded: " + myQuiz.getTitle());
                            //System.out.println("true");
                            System.out.println("1. Edit quiz title");
                            System.out.println("2. Edit quiz category");
                            System.out.println("3. Edit quiz questions");
                            System.out.println("4. Exit");
                            int editChoice = scanner.nextInt();
                            scanner.nextLine();

                            switch (editChoice) {
                                case 1 -> {
                                    System.out.println("New title: ");
                                    String newTitle = scanner.nextLine();

                                    myQuiz.setTitle(newTitle);
                                    if (quizDAO.updateQuiz(myQuiz)) {
                                        System.out.println("updated title");
                                    } else {
                                        System.out.println("failed to update title");
                                    }
                                }
                                case 2 -> {
                                    System.out.println("New category: ");
                                    String newCategory = scanner.nextLine();

                                    myQuiz.setCategory(newCategory);
                                    if (quizDAO.updateQuiz(myQuiz)) {
                                        System.out.println("updated category");
                                    } else {
                                        System.out.println("failed to update category");
                                    }
                                }
                                case 3 -> {
                                    System.out.println("Questions in this quiz:");
                                    for (int i = 0; i < questions.size(); i++) {
                                        System.out.println((i+1) + ". " + questions.get(i).getTitle());
                                    }

                                    System.out.println("Select the question number to edit: ");
                                    int qIndex = scanner.nextInt() - 1;
                                    scanner.nextLine();

                                    if (qIndex >= 0 && qIndex < questions.size()) {
                                        Question selectedQ = questions.get(qIndex);

                                        System.out.println("New question title: ");
                                        selectedQ.setTitle(scanner.nextLine());

                                        System.out.println("How many options? ");
                                        int optionAmount = scanner.nextInt();
                                        scanner.nextLine();

                                        List<String> newOptions = new ArrayList<>();
                                        for (int i = 1; i <= optionAmount; i++) {
                                            System.out.println("New option " + i + ": ");
                                            newOptions.add(scanner.nextLine());
                                        }
                                        selectedQ.setOptions(newOptions);

                                        System.out.println("New correct answer: ");
                                        selectedQ.setAnswer(scanner.nextLine());

                                        if (questionDAO.updateQuestion(selectedQ)) {
                                            System.out.println("Question updated successfully!");
                                        } else {
                                            System.out.println("Failed to update question.");
                                        }
                                    } else {
                                        System.out.println("invalid selection.");
                                    }
                                }
                                case 4 -> {}
                                default -> {}

                            }
                        }
                    } else if (choiceQuiz == 3) {
                        break;
                    }
                }
            } else if (choice == 4) {
                loggedIn = false;
            }
        }
    }
}
