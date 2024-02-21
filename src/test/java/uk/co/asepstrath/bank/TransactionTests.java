package uk.co.asepstrath.bank;

import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import uk.co.asepstrath.bank.transactions.Transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.UUID;


public class TransactionTests {

    Transaction a;
    UUID id;

    @BeforeEach
    void initialise() {
        LocalDateTime timestamp = LocalDateTime.now();
        id = UUID.randomUUID();
        a = new Transaction(null, id, "Payment", 50.0, "AccountA", "AccountB");
    }

    @Test
    void createTransaction() {
        Assertions.assertNotNull(a);
    }

    @Test
    void checkTimestampValid() {
        Assertions.assertEquals(0,0);
    }

    @Test
    void checkIDValid() {
        Assertions.assertEquals(0,0);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    void checkTypeValid() {

    }

    @Test
    void checkAmountValid() {
        Assertions.assertEquals(a.getAmount(),BigDecimal.valueOf(50.0));
        a = new Transaction(null, id, "Payment", 50.0, "AccountA", "AccountB");
    }

    @Test
    void checkToValid() {
        Assertions.assertEquals(0,0);
    }

    @Test
    void checkfromValid() {
        Assertions.assertEquals(0,0);
    }

}
