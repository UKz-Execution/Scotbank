package uk.co.asepstrath.bank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.co.asepstrath.bank.transactions.Transaction;
import uk.co.asepstrath.bank.transactions.TransactionsAPI;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

public class TransactionsTests {
    private static Transaction t;

    @BeforeAll
    public static void initTransaction() {
        t = new Transaction(
                LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0),
                UUID.fromString("635e583f-0af2-47cb-9625-5b66ba30e188"),
                "amount",
                BigDecimal.ZERO,
                null,
                null
        );
    }

    @Test
    public void testAPIDatabaseLoginFail() {
        Assertions.assertThrows(Exception.class, () -> TransactionsAPI.loadData(null));
    }

    @Test
    public void testGetters() {
        Assertions.assertEquals(t.timestamp, t.getTimestamp());
        Assertions.assertEquals(t.id, t.getId());
        Assertions.assertEquals(t.type, t.getType());
        Assertions.assertEquals(t.amount, t.getAmount());
        Assertions.assertEquals(t.to, t.getTo());
        Assertions.assertEquals(t.from, t.getFrom());
    }

}

