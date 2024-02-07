package uk.co.asepstrath.bank;

import java.math.BigDecimal;

public class Account {

    public String id;
    public String name;
    public BigDecimal startingBalance;

    public boolean roundUpEnabled;
    private BigDecimal balance = new BigDecimal(0);

    public Account() {
        name = "";
    }

    public Account(String id, String name, int startingBalance, boolean roundUpEnabled) {
        this.id = id;
        this.name = name;
        this.startingBalance = BigDecimal.valueOf(startingBalance);
        this.balance = BigDecimal.valueOf(startingBalance);
        this.roundUpEnabled = roundUpEnabled;
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
        return name;
    }

    public String getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public BigDecimal getStartingBalance() {
        return startingBalance;
    }

    public boolean isRoundUpEnabled() {
        return roundUpEnabled;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Balance: " + balance;
    }

}
