package uk.co.asepstrath.bank;

import uk.co.asepstrath.bank.example.ExampleController;
import io.jooby.Jooby;
import io.jooby.handlebars.HandlebarsModule;
import io.jooby.helper.UniRestExtension;
import io.jooby.hikari.HikariModule;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class App extends Jooby {

    private ArrayList<Account> accountData;

    {
        /*
        This section is used for setting up the Jooby Framework modules
         */
        install(new UniRestExtension());
        install(new HandlebarsModule());
        install(new HikariModule("mem"));

        /*
        This will host any files in src/main/resources/assets on <host>/assets
        For example in the dice template (dice.hbs) it references "assets/dice.png" which is in resources/assets folder
         */
        assets("/assets/*", "/assets");
        assets("/views/*", "/views");
        assets("/service_worker.js","/service_worker.js");

        /*
        Now we set up our controllers and their dependencies
         */
        DataSource ds = require(DataSource.class);
        Logger log = getLog();

        mvc(new ExampleController(ds,log));

        /*
        Finally we register our application lifecycle methods
         */
        onStarted(() -> onStart());
        onStop(() -> onStop());
    }

    public static void main(final String[] args) {
        runApp(args, App::new);
    }

    /*
    This function will be called when the application starts up,
    it should be used to ensure that the DB is properly setup
     */
    public void onStart() {
        Logger log = getLog();
        log.info("Starting Up...");

        // Fetch DB Source
        DataSource ds = require(DataSource.class);

        /* Task 3 - Populating a Dataset. This function will be replaced by API later!
        accountData = new ArrayList<Account>();
        accountData.add(new Account("Rachel", new BigDecimal("50.00")));
        accountData.add(new Account("Monica", new BigDecimal("100.00")));
        accountData.add(new Account("Phoebe", new BigDecimal("76.00")));
        accountData.add(new Account("Joey", new BigDecimal("23.90")));
        accountData.add(new Account("Chandler", new BigDecimal("3.00")));
        accountData.add(new Account("Ross", new BigDecimal("54.32")));
        */



        // Open Connection to DB
        try (Connection connection = ds.getConnection()) {
            //
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE `Account Details` (`Key` varchar(255),`Value` varchar(255))");
            stmt.executeUpdate("INSERT INTO Example " + "VALUES ('WelcomeMessage', 'Welcome to A Bank')");

        } catch (SQLException e) {
            log.error("Database Creation Error",e);
        }


    }

    /*
    This function will be called when the application shuts down
     */
    public void onStop() {
        System.out.println("Shutting Down...");
    }

}
