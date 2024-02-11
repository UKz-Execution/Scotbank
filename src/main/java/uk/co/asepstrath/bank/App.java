package uk.co.asepstrath.bank;

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
        assets("/service_worker.js", "/service_worker.js");

        /*
        Now we set up our controllers and their dependencies
         */
        DataSource ds = require(DataSource.class);
        Logger log = getLog();

        mvc(new ExampleController(ds, log));

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

        // Task 3 - Populating a Dataset. This function will be replaced by API later!
        /*
        accountData = new ArrayList<Account>();
        accountData.add(new Account("Rachel", new BigDecimal("50.00")));
        accountData.add(new Account("Monica", new BigDecimal("100.00")));
        accountData.add(new Account("Phoebe", new BigDecimal("76.00")));
        accountData.add(new Account("Joey", new BigDecimal("23.90")));
        accountData.add(new Account("Chandler", new BigDecimal("3.00")));
        accountData.add(new Account("Ross", new BigDecimal("54.32")));
        */

        // Fetch DB Source
        DataSource db = require(DataSource.class);

        try (Connection connection = db.getConnection()) {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS employees (\n"
                    + " id integer NOT NULL PRIMARY KEY AUTO_INCREMENT,\n"
                    + " name text NOT NULL,\n"
                    + " phone integer NOT NULL,\n"
                    + " salary decimal NOT NULL);");



            PreparedStatement prep = connection.prepareStatement(
                    "INSERT INTO employees (name, phone, salary) "
                            + "VALUES (?,?,?)");
            prep.setString(1, "Bob");
            prep.setInt(2, 666666666);
            prep.setDouble(3, 35000.00);
            prep.executeUpdate();


            ResultSet rs = stmt.executeQuery("SELECT * FROM employees");
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnNumber = rsmd.getColumnCount();
            while (rs.next()){
                System.out.println("");
                for (int i = 1; i <= columnNumber ; i++) {
                    if (i > 1) System.out.println("-----");
                    String columnValue = rs.getString(i);
                    System.out.println(rsmd.getColumnName(i) + " -> " + columnValue );
                }
                System.out.println("");

            }
            rs.close();



        } catch (SQLException e) {
            log.error("Database Creation Error", e);
        }
    }

    /*
    This function will be called when the application shuts down
     */
        public void onStop() {
            System.out.println("Shutting Down...");
        }
    }