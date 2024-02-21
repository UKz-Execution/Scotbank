package uk.co.asepstrath.bank.transactions;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    public LocalDateTime timestamp;
    public UUID id;
    public String type;
    public BigDecimal amount;
    public String to;
    public String from;


    public Transaction (LocalDateTime timestamp, UUID id, String type, double amount, String to, String from) {

        this.timestamp = timestamp;
        this.id = id;
        this.type = type;
        this.amount = BigDecimal.valueOf(amount);
        this.to = to;
        this.from = from;
    }

    public LocalDateTime getTimestamp() {
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
