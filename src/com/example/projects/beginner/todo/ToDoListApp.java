package com.example.projects.beginner.todo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ToDoListApp {
    public static void main(String[] args) {
        System.out.println("--- To-Do List Application ---");
        /*
         * TO-DO LIST:
         * [ ] Create a Task class (title, description, status).
         * [ ] Use a List or Array to store tasks.
         * [ ] Implement "Add Task" and "View Tasks" functionality.
         * [ ] Implement "Mark as Complete" and "Delete Task".
         * [ ] Save the list to a text file/database and load it on startup.
         *
         * HOW IT WORKS:
         * 1. The application loads your tasks from a file or database on startup.
         * 2. The application displays a menu to the user.
         * 3. The user can add a task, view the list, mark a task as done, or delete a task.
         * 4. Any changes are automatically saved to the file or database.
         * 5. The application continues to run until the user chooses to exit.
         */

        Scanner scanner = new Scanner(System.in);

        try (Connection conn = databaseConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Successful connection to db");
            }
        } catch (SQLException e) {
            System.out.println("Couldn't connect to db: " + e.getMessage());
        }
        TaskManager manager = new TaskManager();
        while (true) {
            System.out.println("Enter 1 to view all tasks");
            System.out.println("Enter 2 to add a task");
            System.out.println("Enter 3 to edit a task");
            System.out.println("Enter 4 to remove a task");
            System.out.println("Enter 5 to exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    try {
                        List<Task> myTasks = manager.getAllTasks();

                        for (Task t : myTasks) {
                            System.out.println(t.getId() + " " + t.getTitle() + " \t--" + t.getStatus());
                            //System.out.println(t.getTitle());
                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("\n");
                    break;
                case 2:
                    System.out.println("Enter the title of the task");
                    String title = scanner.nextLine();
                    System.out.println("Enter the description of the task");
                    String desc = scanner.nextLine();
                    System.out.println("Enter the status of the task");
                    String status = scanner.nextLine();

                    Task newTask = new Task(title, desc, status);
                    manager.addTask(newTask);

                    break;
                case 3:
                    try {
                        List<Task> myTasks = manager.getAllTasks();

                        for (Task t : myTasks) {
                            System.out.println(t.getId() + " " + t.getTitle());
                            //System.out.println(t.getTitle());
                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }

                    System.out.println("\n");
                    System.out.println("Enter the id of the task which you want to edit");
                    int editId = scanner.nextInt();
                    scanner.nextLine();
                    Task task = manager.getTaskById(editId);
                    if (task == null) {
                        System.out.println("No task was found with the id: " + editId);
                        break;
                    } else {
                        boolean editing = true;
                        while (editing) {
                            System.out.println("1. Edit title");
                            System.out.println("2. Edit description");
                            System.out.println("3. Edit status");
                            System.out.println("4. Done");
                            choice = scanner.nextInt();
                            scanner.nextLine();

                            switch (choice) {
                                case 1:
                                    System.out.println("Enter a new title: ");
                                    String newTitle = scanner.nextLine();

                                    task.setTitle(newTitle);
                                    manager.editTask(task);
                                    break;
                                case 2:
                                    System.out.println("Enter a new description: ");
                                    String newDesc = scanner.nextLine();

                                    task.setDescription(newDesc);
                                    manager.editTask(task);
                                    break;
                                case 3:
                                    System.out.println("Enter a new status");
                                    String newStatus = scanner.nextLine();

                                    task.setStatus(newStatus);
                                    manager.editTask(task);
                                    break;
                                case 4:
                                    editing = false;
                                    System.out.println("Changes finalized");
                                    break;
                                default:
                                    editing = false;
                                    System.out.println("Leaving editing");
                                    break;
                            }

                        }

                    }
                    break;
                case 4:
                    try {
                        List<Task> myTasks = manager.getAllTasks();

                        for (Task t : myTasks) {
                            System.out.println(t.getId() + " " + t.getTitle());
                            //System.out.println(t.getTitle());
                        }

                        System.out.println("Enter the id of a task to remove");
                        int removeId = scanner.nextInt();
                        scanner.nextLine();

                        task = manager.getTaskById(removeId);
                        if (task == null) {
                            System.out.println("Invalid id");
                        } else {
                            manager.removeTask(removeId);
                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }


    }
}
