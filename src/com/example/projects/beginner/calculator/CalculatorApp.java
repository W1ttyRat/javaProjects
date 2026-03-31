package com.example.projects.beginner.calculator;

import java.util.Scanner;

public class CalculatorApp {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("--- Calculator Application ---");
        /*
         * TO-DO LIST:
         * [ ] Design the UI (Console-based: "Enter first number", "Select operator").
         * [ ] Implement basic arithmetic operations (Add, Subtract, Multiply, Divide).
         * [ ] Add error handling (e.g., division by zero).
         * [ ] Implement a loop to allow multiple calculations without restarting.
         * [ ] Optional: Add advanced functions like square root or power.
         *
         * HOW IT WORKS:
         * 1. The user is prompted to enter a number.
         * 2. The user selects an operator (+, -, *, /).
         * 3. The user enters a second number.
         * 4. The application performs the operation and displays the result.
         * 5. The user is asked if they want to perform another calculation.
         */
        while (true) {
            System.out.println("(1) addition \n(2) subtract \n(3) multiply \n(4) divide");
            int choice = sc.nextInt();
            if (choice > 0 && choice < 5) {
                System.out.println("Enter two numbers");
                double num1 = sc.nextDouble();
                double num2 = sc.nextDouble();
                switch (choice) {
                    case 1:
                        System.out.println(add(num1, num2));
                        break;
                    case 2:
                        System.out.println(subtract(num1, num2));
                        break;
                    case 3:
                        System.out.println(multiply(num1, num2));
                        break;
                    case 4:
                        try {
                            System.out.println(divide(num1, num2));
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    default:
                        return;
                }
            } else {
                return;
            }
        }
    }

    public static double add(double num1, double num2) {
        return num1 + num2;
    }
    public static double subtract(double num1, double num2) {
        return num1 - num2;
    }
    public static double multiply(double num1, double num2) {
        return num1 * num2;
    }
    public static double divide(double num1, double num2) {
        if (num2 == 0) {
            throw new IllegalArgumentException("error, cant divide with zero");
        }
        return num1 / num2;
    }
}
