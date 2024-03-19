package uk.co.asepstrath.bank;

import io.jooby.Context;
import io.jooby.test.MockContext;
import org.junit.jupiter.api.Test;
import uk.co.asepstrath.bank.transactions.Transaction;
import uk.co.asepstrath.bank.webcontrollers.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;

public class ControllerTests {

    @Test
    public void accountPage() {
        Context mocked = new MockContext();

        AccountController controller = new AccountController();

    }

    @Test
    public void homePage() {
        Context mocked = new MockContext();

        HomeController controller = new HomeController();
    }

    @Test
    public void createLoginPage() {
        Context mocked = new MockContext();

        LoginController controller = new LoginController();
    }

    @Test
    public void loginWithCredentials() {
        Context mocked = new MockContext();

        LoginController controller = new LoginController();
    }

    @Test
    public void spendingPage() {
        Context mocked = new MockContext();

        SpendingController controller = new SpendingController();
    }

    @Test
    public void transactionPage() {

        Context mocked = new MockContext();

        TransactionController controller = new TransactionController();
    }

    @Test
    public void webController() {
        Context mocked = new MockContext();

        WebController controller = new WebController();

//        Assertions.assertEquals();
    }

    @Test
    public void calcCurrentAmount(){
        double startingAmount = 100.0;
        String accountUUID = "account123";

        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(LocalDateTime.now(), UUID.randomUUID(), "PAYMENT", BigDecimal.valueOf(50.0), null, "account123"));
        transactions.add(new Transaction(LocalDateTime.now(), UUID.randomUUID(), "DEPOSIT", BigDecimal.valueOf(30.0), "account123", null));
        transactions.add(new Transaction(LocalDateTime.now(), UUID.randomUUID(), "WITHDRAWAL", BigDecimal.valueOf(20.0), "receiver", "account123"));
        transactions.add(new Transaction(LocalDateTime.now(), UUID.randomUUID(), "TRANSFER", BigDecimal.valueOf(10.0), null, "account123"));

        BigDecimal expectedAmount = BigDecimal.valueOf(100.0).subtract(BigDecimal.valueOf(50.0)).add(BigDecimal.valueOf(30.0)).subtract(BigDecimal.valueOf(20.0)).subtract(BigDecimal.valueOf(10.0));
        BigDecimal actualAmount = new AccountController().calculateCurrentAmount(startingAmount, transactions, accountUUID);

        assertEquals(expectedAmount, actualAmount);

    }
}
