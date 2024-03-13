package uk.co.asepstrath.bank.webcontrollers;

import io.jooby.Context;
import io.jooby.ModelAndView;
import io.jooby.annotation.GET;
import io.jooby.annotation.Path;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Path("/")
public class HomeController extends WebController {
    public HomeController() {
        super();
    }

    public HomeController(Logger logger) {
        super(logger);
    }

    @GET("")
    public ModelAndView defaultPage(Context context) {
        return homePage(context);
    }

    @GET("home")
    public ModelAndView homePage(Context context) {

        Map<String, Object> model = new HashMap<>();
        if (isLoggedIn(context)) model.put("loginButton", "Logout");
        else model.put("loginButton", "Login");

        return new ModelAndView("home.hbs", model);
    }
}
