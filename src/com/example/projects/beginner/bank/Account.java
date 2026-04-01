package com.example.projects.beginner.bank;

public class Account {
    /*
     * TO-DO LIST:
     * [ ] Add fields: name (String), pin (String), balance (double).
     * [ ] Create a constructor to initialize these fields.
     * [ ] Implement getBalance(), getName(), and getPin() methods.
     * [ ] Implement deposit(double amount) with a positive value check.
     * [ ] Implement withdraw(double amount) with an overdraft check.
     * [ ] Optional: Add a transaction log (List<String>) to track changes.
     *
     * HOW IT WORKS:
     * 1. The object stores private data that can only be changed via methods.
     * 2. deposit() adds money if the amount is valid (> 0).
     * 3. withdraw() removes money only if the balance is sufficient.
     * 4. The main application uses these methods to handle financial logic safely.
     */
    private String name;
    private String pin;
    private double balance;

    public Account(String name, String pin) {

        this.name = name;
        this.pin = pin;
        this.balance = 1000;
    }

    public Account(String name, String pin, double balance) {
        this.name = name;
        this.pin = pin;
        this.balance = balance;
    }

    public double getBalance() {
        return this.balance;
    }

    public String getName() {
        return this.name;
    }

    public String getPin() {
        return this.pin;
    }

    public boolean checkPin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public double deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
        return this.balance;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Account: " + name + " | Balance: €" + balance;
    }


}
