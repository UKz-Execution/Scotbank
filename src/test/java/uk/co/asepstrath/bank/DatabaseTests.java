package uk.co.asepstrath.bank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.co.asepstrath.bank.accounts.Account;
import uk.co.asepstrath.bank.database.DatabaseAPI;

import javax.sql.DataSource;
import io.jooby.Jooby;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseTests extends Jooby{
    private DatabaseAPI db;
    private final DataSource ds = require(DataSource.class);
    private final UUID id = UUID.randomUUID();
    @Test
    void initialise() throws Exception { // Check database connection and creation
        DatabaseAPI.initDatabase(ds);
        Assertions.assertNotNull(ds);
    }

    @Test
    void testUpdateName() throws SQLException { // Check name updates correctly via id
        db.updateAccountName(id, "John Smith");
        Account a = db.getAccountById(id);
        Assertions.assertEquals(id, a.id);
        Assertions.assertEquals("John Smith", a.getName());
    }

    @Test
    void testUpdateBalance() throws SQLException { // Check balance updates correctly via id
        db.updateAccountBalance(id, BigDecimal.valueOf(50.0));
        Account a  = db.getAccountById(id);
        Assertions.assertEquals(BigDecimal.valueOf(50.0), a.getBalance());
    }

    @Test
    void testCreateAccount() throws SQLException { // Check account creation
        UUID id = UUID.randomUUID();
        Account temp = new Account(id, "Charlie Higgins", 50.0, false);
        db.createAccount(temp);
        Assertions.assertNotNull(db.getAccountById(id));
    }

    @Test
    void testGetAllAccounts() throws SQLException { // Checks get all accounts is not null
        Assertions.assertNotNull(db.getAllAccounts());
    }
}
