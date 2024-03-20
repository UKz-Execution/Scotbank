package uk.co.asepstrath.bank;

import io.jooby.StatusCode;
import io.jooby.test.MockContext;
import io.jooby.test.MockRouter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import uk.co.asepstrath.bank.accounts.Account;
import uk.co.asepstrath.bank.users.SuperUser;
import uk.co.asepstrath.bank.users.User;
import uk.co.asepstrath.bank.users.Users;
import uk.co.asepstrath.bank.webcontrollers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ControllerTests {
    MockContext mocked;
    MockRouter router;
    WebController webController;
    WebController controllerLogger;

    @BeforeEach
    public void setUp() {
        mocked = new MockContext();
        router = new MockRouter(new App());

        webController = new WebController();
        controllerLogger = new WebController(Mockito.mock(Logger.class));
    }

    @Test
    public void accountPage() {
        router.get("/account", rsp -> {
            Assertions.assertEquals("/login", rsp.getHeaders().get("location"));
            Assertions.assertEquals(StatusCode.FOUND, rsp.getStatusCode());
        });

        mocked.session().put("username", "PhebeLehner");
        mocked.session().put("password", "pass123");
        List<Account> accs = new ArrayList<>();
        accs.add(new Account(UUID.fromString("d07f1095-46ea-4233-a6f6-6bbe6a6b4a21"), "Phebe Lehner", 149.04, false));
        Users.storeUserData(new User("PhebeLehner","pass123", accs));


//        router.get("/account", mocked, rsp -> {
//            Assertions.assertEquals("account.hbs", rsp.value().toString());
//            Assertions.assertEquals(StatusCode.OK, rsp.getStatusCode());
//        });

        AccountController controller = new AccountController();

    }

    @Test
    public void homePage() {
        router.get("/home", rsp -> {
            Assertions.assertEquals("home.hbs", rsp.value().toString());
            Assertions.assertEquals(StatusCode.OK, rsp.getStatusCode());
        });

        router.get("/", rsp -> {
            Assertions.assertEquals("home.hbs", rsp.value().toString());
            Assertions.assertEquals(StatusCode.OK, rsp.getStatusCode());
        });

        HomeController controller = new HomeController();
    }

    @Test
    public void createLoginPage() {
        router.get("/login", rsp -> {
            Assertions.assertEquals("login.hbs", rsp.value().toString());
            Assertions.assertEquals(StatusCode.OK, rsp.getStatusCode());
        });

        LoginController controller = new LoginController();
    }

    @Test
    public void loginWithCredentials() {


        LoginController controller = new LoginController();
    }

    @Test
    public void spendingPage() {
        router.get("/spending", rsp -> {
            Assertions.assertEquals("/login", rsp.getHeaders().get("location"));
            Assertions.assertEquals(StatusCode.FOUND, rsp.getStatusCode());
        });

        mocked.session().put("username", "PhebeLehner");
        mocked.session().put("password", "pass123");
        List<Account> accs = new ArrayList<>();
        accs.add(new Account(UUID.fromString("d07f1095-46ea-4233-a6f6-6bbe6a6b4a21"), "Phebe Lehner", 149.04, false));
        Users.storeUserData(new User("PhebeLehner","pass123", accs));


        router.get("/spending", mocked, rsp -> {
            Assertions.assertEquals("spending.hbs", rsp.value().toString());
            Assertions.assertEquals(StatusCode.OK, rsp.getStatusCode());
        });

        SpendingController controller = new SpendingController();
    }

    @Test
    public void transactionPage() {
        router.get("/transaction", rsp -> {
            Assertions.assertEquals("/login", rsp.getHeaders().get("location"));
            Assertions.assertEquals(StatusCode.FOUND, rsp.getStatusCode());
        });

        mocked.session().put("username", "PhebeLehner");
        mocked.session().put("password", "pass123");
        List<Account> accs = new ArrayList<>();
        accs.add(new Account(UUID.fromString("d07f1095-46ea-4233-a6f6-6bbe6a6b4a21"), "Phebe Lehner", 149.04, false));
        Users.storeUserData(new User("PhebeLehner","pass123", accs));


        router.get("/transaction", mocked, rsp -> {
            Assertions.assertEquals("transaction.hbs", rsp.value().toString());
            Assertions.assertEquals(StatusCode.OK, rsp.getStatusCode());
        });

        TransactionController controller = new TransactionController();

        controller.transactionsPage(mocked);
    }

    @Test
    public void managerController() {
        router.get("/account/manager", rsp -> {
            Assertions.assertEquals("/login", rsp.getHeaders().get("location"));
            Assertions.assertEquals(StatusCode.FOUND, rsp.getStatusCode());
        });

        router.get("/account/manager/view", rsp -> {
            Assertions.assertEquals("/login", rsp.getHeaders().get("location"));
            Assertions.assertEquals(StatusCode.FOUND, rsp.getStatusCode());
        });

        mocked.session().put("username", "admin");
        mocked.session().put("password", "admin");
        //List<Account> accs = new ArrayList<Account>();
        //accs.add(new Account());
        Users.storeUserData(new SuperUser("admin","admin"));


        router.get("/account/manager", mocked, rsp -> {
            Assertions.assertEquals("allAccounts.hbs", rsp.value().toString());
            Assertions.assertEquals(StatusCode.OK, rsp.getStatusCode());
        });
        /*
        router.get("/account/manager/view", mocked, rsp -> {
            Assertions.assertEquals("account.hbs", rsp.value().toString());
            Assertions.assertEquals(StatusCode.OK, rsp.getStatusCode());
        });*/


        ManagerController controller = new ManagerController();
    }

    @Test
    public void webController() {


        // At the moment all methods in WebController are protected so idk if they contribute to coverage
        WebController controller = new WebController();
        WebController controllerLogger = new WebController(Mockito.mock(Logger.class));

//        Assertions.assertFalse();

//        Assertions.assertEquals();
    }
}
