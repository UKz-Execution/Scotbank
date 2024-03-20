package uk.co.asepstrath.bank;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import uk.co.asepstrath.bank.accounts.Account;
import uk.co.asepstrath.bank.database.DatabaseAPI;
import uk.co.asepstrath.bank.transactions.Transaction;

import javax.sql.DataSource;

public class DatabaseTests {

    @Test
    public void testFailedConnect() {
        Assertions.assertThrows(Exception.class, () -> DatabaseAPI.initDatabase(Mockito.mock(DataSource.class)));
    }

    @Test
    public void assertFailures() {
        Assertions.assertThrows(Exception.class, () -> DatabaseAPI.open().createAccount(new Account()));
        Assertions.assertThrows(Exception.class, () -> DatabaseAPI.open().createTransaction(Mockito.mock(Transaction.class)));
    }

}
