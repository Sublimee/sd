package org.example.task_2;

public class TestBankAccount {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000.0);

        System.out.println("Начальный баланс: " + account.getBalance());

        account.deposit(500.0);
        System.out.println("После депозита 500.0: " + account.getBalance());

        account.withdraw(200.0);
        System.out.println("После снятия 200.0: " + account.getBalance());

        // Баланс может стать отрицательным
        account.withdraw(1500.0);
        System.out.println("После снятия 1500.0: " + account.getBalance());

        // Депозит отрицательной суммы
        account.deposit(-100.0);
        System.out.println("После депозита -100.0: " + account.getBalance());

        // Снятие отрицательной суммы
        account.withdraw(-50.0);
        System.out.println("После снятия -50.0: " + account.getBalance());
    }
}