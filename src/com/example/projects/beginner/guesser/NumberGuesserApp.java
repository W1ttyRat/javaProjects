package com.example.projects.beginner.guesser;

import java.util.Random;
import java.util.Scanner;

public class NumberGuesserApp {
    public static void main(String[] args) {
        System.out.println("--- Number Guessing Game ---");
        /*
         * TO-DO LIST:
         * [ ] Generate a random number within a specific range (e.g., 1-100).
         * [ ] Create a loop to accept user guesses.
         * [ ] Implement "Higher" or "Lower" feedback logic.
         * [ ] Track the number of attempts taken.
         * [ ] Add a "Play Again" feature.
         *
         * HOW IT WORKS:
         * 1. The application picks a random number.
         * 2. The user enters a guess.
         * 3. The application tells the user if the guess is too high, too low, or correct.
         * 4. The user continues to guess until the correct number is found.
         * 5. The application displays the total number of attempts.
         */

        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        // nextInt(1000) > 0 to 999;
        int randomNum = rand.nextInt(1000) + 1;
        int attempts = 0;

        System.out.println("Guess a random number from 1 to 1000");


        while (true) {
            System.out.print("Number: ");
            int guess = sc.nextInt();

            attempts++;
            if (guess == randomNum) {
                System.out.println("You guessed correctly");
                System.out.println("It took you " + attempts + " attempts to guess it!");
                System.out.println("Do you wanna play again? (Y/N)");
                String action = sc.next();
                if (action.equals("Y") || action.equals("y")) {
                    attempts = 0;
                    randomNum = rand.nextInt(1000) + 1;
                    System.out.println("Guess a random number from 1 to 1000");
                } else {
                    System.out.println("Game Over!");
                    break;
                }
            } else if (guess < randomNum) {
                System.out.println("Your guess is lower");
            } else {
                System.out.println("Your guess is higher");
            }
        }
    }
}
