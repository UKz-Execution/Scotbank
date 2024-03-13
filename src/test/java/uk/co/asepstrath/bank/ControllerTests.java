package uk.co.asepstrath.bank;

import io.jooby.Context;
import io.jooby.test.MockContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import uk.co.asepstrath.bank.webcontrollers.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ControllerTests {

    @BeforeEach
    public void setUp() {
        Context mocked = new MockContext();

        WebController controller = new WebController();
        WebController controllerLogger = new WebController(Mockito.mock(Logger.class));
    }

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
}
