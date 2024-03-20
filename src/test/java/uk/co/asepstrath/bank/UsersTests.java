package uk.co.asepstrath.bank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.co.asepstrath.bank.users.SuperUser;
import uk.co.asepstrath.bank.users.User;
import uk.co.asepstrath.bank.users.Users;

import java.util.List;

public class UsersTests {

    @BeforeAll
    public static void initUsers() {
        Assertions.assertEquals(0, Users.getUsers().size());
        Assertions.assertThrows(RuntimeException.class, Users::loadUsersFromFile);
        Users.storeUserData(new SuperUser("admin", "admin"));
        Users.storeUserData(new User("user", "password"));
        Assertions.assertEquals(2, Users.getUsers().size());
    }

    @Test
    public void testGetUser() {
        Assertions.assertNull(Users.getUser("notExist"));
        Assertions.assertNotNull(Users.getUser("admin"));
    }

}
