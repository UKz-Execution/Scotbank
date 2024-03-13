package uk.co.asepstrath.bank.webcontrollers;

import io.jooby.Context;
import io.jooby.Session;
import org.slf4j.Logger;
import uk.co.asepstrath.bank.accounts.Account;
import uk.co.asepstrath.bank.database.DatabaseAPI;

import javax.sql.DataSource;
import java.util.UUID;

public class WebController {
    protected final DataSource dataSource;
    protected final Logger logger;

    public WebController(DataSource dataSource, Logger logger) {
        this.dataSource = dataSource;
        this.logger = logger;
    }

    protected boolean isLoggedIn(Context context) {
        return getCurrentAccount(context) != null;
    }

    protected Account getCurrentAccount(Context context) {
        String id = context.session().get("uuid").value("");
        if (id.equals("")) return null;
        UUID uuid = UUID.fromString(id);
        try (DatabaseAPI connection = DatabaseAPI.open()) {
            return connection.getAccountById(uuid);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    protected void setCurrentAccount(Context context, Account account) {
        if (account == null) context.session().put("uuid", "");
        else context.session().put("uuid", account.getId().toString());
    }

}
