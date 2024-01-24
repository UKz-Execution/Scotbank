package uk.co.asepstrath.bank;

public class Account {
    private double balance;

    public void deposit(double amount) {
        balance = balance + amount;

    }

    public boolean withdraw(double amount){
        if (balance <= amount) {
            balance = balance - amount;
            return true;
        } else
            return false;
    }

    public double getBalance() {
        return balance;
    }

}
