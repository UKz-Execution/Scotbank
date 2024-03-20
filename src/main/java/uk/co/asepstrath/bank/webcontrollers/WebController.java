package uk.co.asepstrath.bank.webcontrollers;

import io.jooby.Context;
import org.slf4j.Logger;
import uk.co.asepstrath.bank.users.SuperUser;
import uk.co.asepstrath.bank.users.User;
import uk.co.asepstrath.bank.users.Users;

public class WebController {
    private final Logger logger;

    public WebController() {
        logger = null;
    }

    public WebController(Logger logger) {
        this.logger = logger;
    }

    protected boolean isLoggedIn(Context context) {
        return !context.session().get("username").value("").equals("");
    }

    protected boolean isSuperUser(Context context) {
        return getCurrentUser(context) instanceof SuperUser;
    }

    protected User getCurrentUser(Context context) {
        String username = context.session().get("username").value("");
        if (username.equals("")) return null;
        return Users.getUser(username);
    }

    protected boolean login(Context context, String username, String password) {
        User user = Users.getUser(username);
        if (user == null || !user.isCorrectPassword(password)) return false;
        context.session().put("username", user.getUsername());
        return true;
    }

    protected void logout(Context context) {
        context.session().put("username", "");
    }

    protected void log(String value) {
        if (logger != null) logger.info(value);
    }

    protected void logError(String value) {
        if (logger != null) logger.error(value);
    }

}
