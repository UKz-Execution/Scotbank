package uk.co.asepstrath.bank.example;

import java.math.BigDecimal;

public class Transaction {
    public String id;
    public String type;
    public BigDecimal amount;
    public String to;
    public String from;

    public Transaction (String id, String type, int amount, String to, String from) {
        this.id = id;
        this.type = type;
        this.amount = BigDecimal.valueOf((long)amount);
        this.to = to;
        this.from = from;
    }

    public String getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public String getTo() {
        return to;
    }
    public String getFrom() {
        return from;
    }
}
