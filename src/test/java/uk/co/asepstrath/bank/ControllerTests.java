package uk.co.asepstrath.bank;

import io.jooby.*;
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
    }

    @Test
    public void webController() {


        WebController controller = new WebController();

//        Assertions.assertEquals();
    }
}
