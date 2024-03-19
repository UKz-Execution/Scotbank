package uk.co.asepstrath.bank.webcontrollers;

import io.jooby.Context;
import org.slf4j.Logger;
import uk.co.asepstrath.bank.accounts.Account;
import uk.co.asepstrath.bank.database.DatabaseAPI;

import java.util.UUID;

public class WebController {
    private final Logger logger;

    public WebController() {
        logger = null;
    }

    public WebController(Logger logger) {
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
            logError(e.getMessage());
        }
        return null;
    }

    protected void setCurrentAccount(Context context, Account account) {
        if (account == null) context.session().put("uuid", "");
        else context.session().put("uuid", account.getId().toString());
    }

    protected void log(String value) {
        if (logger != null) logger.info(value);
    }

    protected void logError(String value) {
        if (logger != null) logger.error(value);
    }

}
