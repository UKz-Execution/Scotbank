package uk.co.asepstrath.bank.accounts;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

public class Account {

    public UUID id;
    public String name;
    public BigDecimal startingBalance, balance;
    public boolean roundUpEnabled;

    public Account() {
        this(UUID.randomUUID(), "", 0, 0, false);
    }

    public Account(UUID id, String name, double currentBalance, boolean roundUpEnabled) {
        this(id, name, 0, currentBalance, roundUpEnabled);
    }

    public Account(UUID id, String name, double startingBalance, double currentBalance, boolean roundUpEnabled) {
        this(id, name, BigDecimal.valueOf(startingBalance), BigDecimal.valueOf(currentBalance), roundUpEnabled);
    }

    public Account(UUID id, String name, BigDecimal currentBalance, boolean roundUpEnabled) {
        this(id, name, BigDecimal.ZERO, currentBalance, roundUpEnabled);
    }

    public Account(UUID id, String name, BigDecimal startingBalance, BigDecimal currentBalance, boolean roundUpEnabled) {
        if (id == null || name == null || startingBalance == null || currentBalance == null)
            throw new RuntimeException("Account data cannot be null.");
        if (startingBalance.compareTo(BigDecimal.valueOf(0)) < 0 || currentBalance.compareTo(BigDecimal.valueOf(0)) < 0)
            throw new RuntimeException("Account balance cannot be less than 0");
        this.id = id;
        this.name = name;
        this.startingBalance = startingBalance;
        this.balance = currentBalance;
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

    public UUID getId() {
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
