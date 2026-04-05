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
        try {
            List<Task> myTasks = manager.getAllTasks();

            for (Task t : myTasks) {
                System.out.println(t);
                //System.out.println(t.getTitle());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }







    }
}
