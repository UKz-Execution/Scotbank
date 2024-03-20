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

    public Transaction() {

    }

    public Transaction (LocalDateTime timestamp, UUID id, String type, BigDecimal amount, String to, String from) {

        this.timestamp = timestamp;
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.to = to;
        this.from = from;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setId (UUID id) {
        this.id = id;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = BigDecimal.valueOf(amount);
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setFrom(String from) {
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
