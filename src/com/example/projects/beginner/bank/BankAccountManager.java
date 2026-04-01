package com.example.projects.beginner.bank;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class BankAccountManager {
    private Map<String, Account> accounts = new HashMap<>();

    public void loadFromFile(String fileName) {
        java.io.File file = new java.io.File(fileName);

        if (!file.exists()) {
            System.out.println("No existing account data found. Starting fresh!");
            return;
        }
        try {

            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {

                if (line.trim().isEmpty()) {
                    line = reader.readLine();
                    continue;
                }

                String[] parts = line.split(",");
                String name = parts[0];
                String pin = parts[1];
                double balance = Double.parseDouble(parts[2]);

                Account acc = new Account(name, pin, balance);
                accounts.put(name, acc);

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public boolean addAccount(Account newAccount) {

        if (accounts.containsKey(newAccount.getName())) {
            return false;
        }

        accounts.put(newAccount.getName(), newAccount);
        return true;
    }

    public Account getAccount(String name) {
        return accounts.get(name);
    }

    public void saveToFile(String fileName) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName));

            for (Account acc : accounts.values()) {
                String line = acc.getName() + "," + acc.getPin() + "," + acc.getBalance();

                writer.println(line);
            }

            writer.close();
            System.out.println("Data saved successfully");
        } catch (IOException e) {
            System.out.println("Error saving to file");
        }
    }
    /*
     * TO-DO LIST:
     * [ ] Add a Map<String, Account> to store all accounts by name or ID.
     * [ ] Implement a constructor to initialize the Map.
     * [ ] Create addAccount(Account account) to register a new user.
     * [ ] Create getAccount(String name) to find an account for login.
     * [ ] Implement saveToFile(String filename) to export all account data.
     * [ ] Implement loadFromFile(String filename) to import account data on startup.
     *
     * HOW IT WORKS:
     * 1. This class is the "Manager" or "Bank Clerk" for your system.
     * 2. It holds the "Filing Cabinet" (the Map) where all accounts live.
     * 3. The BankSystemApp talks to the Manager to find or create users.
     * 4. The Manager is responsible for reading/writing to the database file.
     */
}
