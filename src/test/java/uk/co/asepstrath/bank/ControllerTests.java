package uk.co.asepstrath.bank;

import io.jooby.Context;
import io.jooby.ModelAndView;
import io.jooby.StatusCode;
import io.jooby.test.MockContext;
import io.jooby.test.MockRouter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.slf4j.Logger;
import uk.co.asepstrath.bank.webcontrollers.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

        //HomeController controller = new HomeController();
    }

    @Test
    public void createLoginPage() {


        LoginController controller = new LoginController();
    }

    @Test
    public void loginWithCredentials() {


        //LoginController controller = new LoginController();
    }

    @Test
    public void spendingPage() {


        SpendingController controller = new SpendingController();
    }

    @Test
    public void transactionPage() {



        TransactionController controller = new TransactionController();
    }

    @Test
    public void webController() {


        WebController controller = new WebController();

//        Assertions.assertEquals();
    }
}
