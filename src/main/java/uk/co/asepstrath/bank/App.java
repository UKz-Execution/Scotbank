package uk.co.asepstrath.bank;

import uk.co.asepstrath.bank.accounts.AccountsAPI;
import uk.co.asepstrath.bank.database.DatabaseAPI;
import io.jooby.Jooby;
import io.jooby.handlebars.HandlebarsModule;
import io.jooby.helper.UniRestExtension;
import io.jooby.hikari.HikariModule;
import org.slf4j.Logger;
import uk.co.asepstrath.bank.transactions.TransactionsAPI;
import uk.co.asepstrath.bank.webcontrollers.*;

import javax.sql.DataSource;
import java.util.ArrayList;

public class App extends Jooby {

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
        assets("/service_worker.js", "/service_worker.js");

        /*
        Now we set up our controllers and their dependencies
         */
        DataSource ds = require(DataSource.class);
        Logger log = getLog();

        mvc(new AccountController(log));
        mvc(new HomeController(log));
        mvc(new LoginController(log));
        mvc(new SpendingController(log));
        mvc(new TransactionController(log));

        /*
        Finally we register our application lifecycle methods
         */
        onStarted(this::onStart);
        onStop(this::onStop);
    }

    public static void main(final String[] args) {
        runApp(args, App::new);
    }

    /*
    This function will be called when the application starts up,
    it should be used to ensure that the DB is properly setup
     */
    public void onStart() throws Exception {
        Logger log = getLog();
        log.info("Starting Up...");

        // Fetch DB Source
        DataSource db = require(DataSource.class);
        DatabaseAPI.initDatabase(db);
        AccountsAPI.loadData(log);
        TransactionsAPI.loadData(log);
    }

    /*
    This function will be called when the application shuts down
     */
        public void onStop() {
            System.out.println("Shutting Down...");
        }
    }