package uk.co.asepstrath.bank;

import io.jooby.*;
import io.jooby.test.MockContext;
import io.jooby.test.MockRouter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.slf4j.Logger;
import uk.co.asepstrath.bank.transactions.Transaction;
import org.mockito.Mockito;
import org.slf4j.Logger;
import uk.co.asepstrath.bank.webcontrollers.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;

public class ControllerTests {
    MockContext mocked;
    MockRouter router;

    @BeforeEach
    public void setUp() {
        mocked = new MockContext();
        router = new MockRouter(new App());

        WebController controller = new WebController();
        WebController controllerLogger = new WebController(Mockito.mock(Logger.class));
    }

    @Test
    public void accountPage() {
        router.get("/account", rsp -> {
            assertEquals("/login", rsp.getHeaders().get("location"));
            assertEquals(StatusCode.FOUND, rsp.getStatusCode());
        });



        /*AccountController controller = new AccountController();
        controller.accountPage(mocked);
        verify(controller).accountPage(ArgumentMatchers.eq(mocked));*/
    }

    @Test
    public void homePage() {
        router.get("/home", rsp -> {
            assertEquals("home.hbs", rsp.value().toString());
            assertEquals(StatusCode.OK, rsp.getStatusCode());
        });

        router.get("/", rsp -> {
            assertEquals("home.hbs", rsp.value().toString());
            assertEquals(StatusCode.OK, rsp.getStatusCode());
        });

        HomeController controller = new HomeController();
    }

    @Test
    public void createLoginPage() {
        router.get("/login", rsp -> {
            assertEquals("login.hbs", rsp.value().toString());
            assertEquals(StatusCode.OK, rsp.getStatusCode());
        });

        /*mocked.setBody("bob");

        router.post("/login", mocked, rsp -> {
            assertEquals("login.hbs", rsp.value().toString());
            assertEquals(StatusCode.OK, rsp.getStatusCode());
        });*/

        LoginController controller = new LoginController();
    }

    @Test
    public void loginWithCredentials() {


        LoginController controller = new LoginController();
    }

    @Test
    public void spendingPage() {
        router.get("/spending", rsp -> {
            assertEquals("/login", rsp.getHeaders().get("location"));
            assertEquals(StatusCode.FOUND, rsp.getStatusCode());
        });

        SpendingController controller = new SpendingController();
    }

    @Test
    public void transactionPage() {
        router.get("/transaction", rsp -> {
            assertEquals("/login", rsp.getHeaders().get("location"));
            assertEquals(StatusCode.FOUND, rsp.getStatusCode());
        });


        TransactionController controller = new TransactionController();

        controller.transactionsPage(mocked);
    }

    @Test
    public void webController() {


        // At the moment all methods in WebController are protected so idk if they contribute to coverage
        WebController controller = new WebController();
        WebController controllerLogger = new WebController(Mockito.mock(Logger.class));

//        Assertions.assertFalse();

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
