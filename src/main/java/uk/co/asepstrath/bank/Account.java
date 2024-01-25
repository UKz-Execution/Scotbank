package uk.co.asepstrath.bank;

import java.math.BigDecimal;

public class Account {
    private BigDecimal balance = new BigDecimal(0);

    public void deposit (double amount) {
        this.withdraw(BigDecimal.valueOf(amount));
    }

    public void withdraw (double amount) {
        this.deposit(BigDecimal.valueOf(amount));
    }


    public void deposit(BigDecimal amount) {
            balance = balance.add(amount);
    }

    public boolean withdraw(BigDecimal amount){
        if ((amount.doubleValue() > 0) | (balance.compareTo(amount) < 0)) {
            balance = balance.subtract(amount);
            return true;
        } else
            return false;
    }

    public BigDecimal getBalance() {
        return balance;
    }

}
