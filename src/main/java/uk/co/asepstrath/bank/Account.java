package uk.co.asepstrath.bank;

import java.math.BigDecimal;

public class Account {
    private BigDecimal balance = new BigDecimal(0);
    private String accName;

    public Account() {
        accName = "";
    }

    public Account(String name, BigDecimal bal) {
        accName = name;
        balance = bal;
    }

    public void deposit (double amount) {
        this.deposit(BigDecimal.valueOf(amount));
    }

    public void withdraw (double amount) {
        this.withdraw(BigDecimal.valueOf(amount));
    }

    public void deposit(BigDecimal amount) {
            balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if ((amount.doubleValue() < 0) | (balance.compareTo(amount) < 0)) {
            throw new ArithmeticException();
        }
            balance = balance.subtract(amount);
        }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getAccName() {
        return accName;
    }

    @Override
    public String toString() {
        return "Name: " + accName + ", Balance: " + balance;
    }

}
