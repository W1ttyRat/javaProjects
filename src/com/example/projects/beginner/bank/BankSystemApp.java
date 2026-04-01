package com.example.projects.beginner.bank;

import java.util.Scanner;


public class BankSystemApp {
    public static void main(String[] args) {
        System.out.println("--- Simple Bank Account System ---");
        /*
         * TO-DO LIST:
         * [ ] Create an Account class with balance and accountNumber.
         * [ ] Implement deposit(amount) and withdraw(amount) methods.
         * [ ] Add logic to prevent overdrafts (withdrawing more than available).
         * [ ] Create a menu for checking balance, depositing, and withdrawing.
         * [ ] Support multiple accounts using a Map.
         * [ ] Save/Load account data to a file (txt/csv) or a database.
         *
         * HOW IT WORKS:
         * 1. The application loads existing accounts from a file or database.
         * 2. The user can create an account or select one.
         * 3. The user can deposit funds, withdraw funds, or check their balance.
         * 4. The application updates the file/database after each transaction.
         * 5. Errors like insufficient funds are handled correctly.
         */

        Scanner scanner = new Scanner(System.in);

        BankAccountManager bank = new BankAccountManager();
        bank.loadFromFile("accounts.txt");

        while (true) {
            System.out.println("Enter 1 to login");
            System.out.println("Enter 2 to sign up");
            System.out.println("Enter 3 to exit");
            int input = scanner.nextInt();

            if (input == 1) {
                System.out.println("Enter your account's name");
                String accountName = scanner.next();

                Account userAcc = bank.getAccount(accountName);

                if (userAcc != null) {
                    System.out.println("Enter your pin:");
                    String enteredPin = scanner.next();

                    if (userAcc.getPin().equals(enteredPin)) {
                        System.out.println("Login successful! Welcome, " + userAcc.getName());

                        boolean loggedIn = true;

                        //performActions(userAcc, bank);...
                        while (loggedIn) {
                            System.out.println("(1) Get account balance \n(2) Deposit an amount \n(3) Withdraw an amount \n(4) To exit");
                            int choice = scanner.nextInt();

                            switch (choice) {
                                case 1:
                                    System.out.println(userAcc.getBalance() + "€");
                                    break;
                                case 2:
                                    System.out.println("How much do you want to deposit: ");
                                    double userDeposit = scanner.nextDouble();
                                    userAcc.deposit(userDeposit);
                                    bank.saveToFile("accounts.txt");
                                    System.out.println("Your balance is now: " + userAcc.getBalance() + "€");
                                    break;
                                case 3:
                                    System.out.println("How much do you want to withdraw: ");
                                    double userWithdraw = scanner.nextDouble();

                                    if (userWithdraw <= 0 || userWithdraw > userAcc.getBalance()) {
                                        System.out.println("The entered amount is too low or you don't have enough money to withdraw");
                                    } else {
                                        bank.saveToFile("accounts.txt");
                                        userAcc.withdraw(userWithdraw);
                                    }
                                    break;
                                case 4:
                                    System.out.println("Logging out...");
                                    bank.saveToFile("accounts.txt");
                                    loggedIn = false;
                                    break;
                                default:
                                    System.out.println("Enter a correct choice");
                                    break;
                            }
                        }
                    } else {
                        System.out.println("Error: Incorrect PIN!");
                    }
                } else {
                    System.out.println("Error: Account not found!");
                }
            } else if (input == 2) {
                System.out.println("Enter your account's NAME: ");
                String newAccountName = scanner.next();

                if (bank.getAccount(newAccountName) != null) {
                    System.out.println("Error: The name '" + newAccountName + "' is already taken! Please choose another");

                } else {
                    System.out.println("Enter a PIN for your account: ");
                    String newAccountPin = scanner.next();

                    Account newUser = new Account(newAccountName, newAccountPin);

                    bank.addAccount(newUser);
                    bank.saveToFile("accounts.txt");

                    System.out.println("Welcome! Account created successfully with a 1000€ bonus!");
                }
            } else {
                System.exit(0);
            }
        }
    }
}
