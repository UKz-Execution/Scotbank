package uk.co.asepstrath.bank.webcontrollers;

import io.jooby.*;
import io.jooby.annotation.GET;
import io.jooby.annotation.POST;
import io.jooby.annotation.Path;
import org.slf4j.Logger;
import uk.co.asepstrath.bank.users.SuperUser;

import java.util.HashMap;
import java.util.Map;

@Path("/login")
public class LoginController extends WebController {

    public LoginController() {
        super();
    }

    public LoginController(Logger logger) {
        super(logger);
    }

    @GET("")
    public ModelAndView loginPage(Context context) {
        return createLoginPage(context, false);
    }

    private ModelAndView createLoginPage(Context context, boolean failedAttempt) {
        logout(context);

        Map<String, Object> model = new HashMap<>();
        if (failedAttempt) model.put("invalidCredentials", "Invalid username or password!");

        return new ModelAndView("login.hbs", model);
    }

    @GET("/admin") // Define the admin path
    public ModelAndView adminPage(Context context) {
        return createAdminPage(context, false);
    }

    private ModelAndView createAdminPage(Context context, boolean failedAttempt) {
        if (getCurrentAccount(context) != null) setCurrentAccount(context, null); // todo : try to limit database lookups

        Map<String, Object> model = new HashMap<>();
        if (failedAttempt) model.put("invalidCredentials", "Invalid username or password!");

        return new ModelAndView("admin.hbs", model);
    }

    @POST("")
    public Object loginCredentials(Context context) {

        String username = context.body().value();
        String password = context.body().value();
        log("Login request: {username: " + username + ", password: " + password + "}");

        if (login(context, username, password)) {
            if (getCurrentUser(context) instanceof SuperUser) context.sendRedirect("/account/manager");
            else context.sendRedirect("/account");
            return null;
        }

        return createLoginPage(context, true);
    }

    @POST("/admin")
    public Object loginCredentialsAdmin(Context context) {

        String username = context.body().value();
        String password = context.body().value();
        log("Admin login request: {username: " + username + ", password: " + password + "}");

        if (username.equals("admin") && password.equals("pass")) {
            setCurrentAccount(context, new Account());
            context.sendRedirect("/account/manager");
            return null;
        }

        return createAdminPage(context, true);
    }


}
