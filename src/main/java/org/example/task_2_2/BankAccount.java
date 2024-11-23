package org.example.task_2_2;

public class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Начальный баланс не может быть отрицательным.");
        }
        balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount < 0) {
            System.out.println("Ошибка: сумма депозита не может быть отрицательной.");
            return;
        }
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount < 0) {
            System.out.println("Ошибка: сумма снятия не может быть отрицательной.");
            return;
        }
        if (balance < amount) {
            System.out.println("Ошибка: недостаточно средств на счете.");
            return;
        }
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }
}