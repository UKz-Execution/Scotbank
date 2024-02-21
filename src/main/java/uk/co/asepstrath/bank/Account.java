package uk.co.asepstrath.bank;

import java.math.BigDecimal;
import java.util.UUID;

public class Account {

    public UUID id;
    public String name;
    public BigDecimal startingBalance;

    public boolean roundUpEnabled;
    private BigDecimal balance = new BigDecimal(0);

    public Account() {
        name = "";
    }

    public Account( UUID id, String name, double startingBalance, boolean roundUpEnabled) {
        try {
            this.id = id;
            this.name = name;
        } catch (NullPointerException e) {
            System.err.println(e.getMessage() + "ID or Name cannot be Null.");
        }
        if (startingBalance < 0) {
            throw new ArithmeticException();
    } else {
        this.startingBalance = BigDecimal.valueOf(startingBalance);
    }
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
