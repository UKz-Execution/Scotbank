package uk.co.asepstrath.bank.database;

import uk.co.asepstrath.bank.Account;
import uk.co.asepstrath.bank.example.ExampleController;
import io.jooby.Jooby;
import io.jooby.handlebars.HandlebarsModule;
import io.jooby.helper.UniRestExtension;
import io.jooby.hikari.HikariModule;
import org.slf4j.Logger;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseAPI {
    private static DataSource db = null;

    public static void initDatabase(DataSource src) {
        db = src;
    }

    public void openConnection() {
        if (db == null) throw new RuntimeException("Unable to connect to database, app has not started.");
    }


}
