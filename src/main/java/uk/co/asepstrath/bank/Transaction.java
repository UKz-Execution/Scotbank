package uk.co.asepstrath.bank;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Transaction {
    public Date timestamp;
    public UUID id;
    public String type;
    public BigDecimal amount;
    public String to;
    public String from;

    public Transaction (Date timestamp, UUID id, String type, double amount, String to, String from) {
        this.timestamp = timestamp;
        this.id = id;
        this.type = type;
        this.amount = BigDecimal.valueOf(amount);
        this.to = to;
        this.from = from;
    }

    public Date getTimestamp() {
        return timestamp;
    }
    public UUID getId() {
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
