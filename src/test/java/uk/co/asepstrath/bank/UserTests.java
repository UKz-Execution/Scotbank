package uk.co.asepstrath.bank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.co.asepstrath.bank.accounts.Account;
import uk.co.asepstrath.bank.users.SuperUser;
import uk.co.asepstrath.bank.users.User;

import java.util.ArrayList;

public class UserTests {
    private static User user;
    private static SuperUser superUser;

    @BeforeAll
    public static void createUsers() {
        user = new User("user", "password");
        superUser = new SuperUser("superuser", "password");

        user = new User("user", "password", new ArrayList<>());
        superUser = new SuperUser("superuser", "password", new ArrayList<>());
    }

    @Test
    public void testGetters() {
        Assertions.assertEquals("user", user.getUsername());
        Assertions.assertEquals("superuser", superUser.getUsername());
        Assertions.assertTrue(user.isCorrectPassword("password"));
        Assertions.assertTrue(superUser.isCorrectPassword("password"));
        Assertions.assertFalse(user.isCorrectPassword("invalid"));
        Assertions.assertFalse(superUser.isCorrectPassword("invalid"));

        Assertions.assertEquals(0, user.getAccounts().size());
        Assertions.assertEquals(0, superUser.getAccounts().size());
    }

    @Test
    public void testAccountManagement() {
        Account account = new Account();
        user.addAccount(account);
        superUser.addAccount(account);
        Assertions.assertEquals(1, user.getAccounts().size());
        Assertions.assertEquals(1, superUser.getAccounts().size());
        Assertions.assertEquals("user,password,false," + account.getId().toString(), user.toString());
        Assertions.assertEquals("superuser,password,true," + account.getId().toString(), superUser.toString());

        user.removeAccount(account);
        superUser.removeAccount(account);
        Assertions.assertEquals(0, user.getAccounts().size());
        Assertions.assertEquals(0, superUser.getAccounts().size());

        user.addAccount(account);
        superUser.addAccount(account);
        user.removeAccount(account.getId());
        superUser.removeAccount(account.getId());
        Assertions.assertEquals(0, user.getAccounts().size());
        Assertions.assertEquals(0, superUser.getAccounts().size());
    }

    @Test
    public void testObjectMethods() {
        Assertions.assertEquals("user,password,false", user.toString());
        Assertions.assertEquals("superuser,password,true", superUser.toString());



        Assertions.assertEquals(user, new User("user", "different"));
        Assertions.assertEquals(user, user);
        Assertions.assertEquals(user, new SuperUser("user","different"));

        Assertions.assertEquals(superUser, new SuperUser("superuser", "different"));
        Assertions.assertEquals(superUser, superUser);
        Assertions.assertEquals(superUser, new User("superuser","different"));

        Assertions.assertNotEquals(user, new User("different", "password"));
        Assertions.assertNotEquals(superUser, new SuperUser("different", "password"));

        Assertions.assertEquals(user.hashCode(), new User("user", "different").hashCode());
    }

}
