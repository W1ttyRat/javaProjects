package com.example.projects.beginner.converter;

import java.util.Scanner;

public class TemperatureConverterApp {
    public static void main(String[] args) {
        System.out.println("--- Temperature Converter ---");
        Scanner sc = new Scanner(System.in);
        /*
         * TO-DO LIST:
         * [ ] Create a menu to select conversion type (C to F, F to C).
         * [ ] Accept numeric input for the temperature.
         * [ ] Apply conversion formulas: (C * 9/5) + 32 and (F - 32) * 5/9.
         * [ ] Format output to 2 decimal places.
         * [ ] Validate that the input is a valid number.
         *
         * HOW IT WORKS:
         * 1. The user selects a conversion direction (e.g., Celsius to Fahrenheit).
         * 2. The user enters a temperature value.
         * 3. The application calculates the converted temperature.
         * 4. The application displays the result formatted to 2 decimal places.
         * 5. The user is asked if they want to perform another conversion.
         */
        while (true) {
            System.out.println("Type the number to choose: \n(1)C to F \n(2)F to C \n(3)To exit");

            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.println("Enter temperature(C): ");
                double C = sc.nextDouble();
                double result = cToF(C);
                System.out.printf("%.1f degrees Celsius is %.2f degrees Fahrenheit", C, result);
            } else if (choice == 2) {
                System.out.println("Enter temperature(F): ");
                double F = sc.nextDouble();
                double result = fToC(F);
                System.out.printf("%.1f degrees Fahrenheit is %.2f degrees Celsius\n", F, result);
            } else {
                break;
            }
        }

    }

    public static double cToF(double C) {
        return (C * 9.0/5) + 32;
    }

    public static double fToC(double F) {
        return (F - 32) * 5.0/9;
    }
}
