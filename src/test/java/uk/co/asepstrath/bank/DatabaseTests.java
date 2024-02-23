package uk.co.asepstrath.bank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import uk.co.asepstrath.bank.database.DatabaseAPI;

import javax.sql.DataSource;
import io.jooby.Jooby;
import java.math.BigDecimal;
import java.sql.Connection;

public class DatabaseTests extends Jooby{
    DatabaseAPI d;
    @BeforeEach
    void initialise() throws Exception { // Check database connection and creation
        DataSource db = require(DataSource.class);
        Assertions.assertNotNull(db);
        DatabaseAPI.initDatabase(db);
    }

    @Test
    void createTable() {

    }
}
