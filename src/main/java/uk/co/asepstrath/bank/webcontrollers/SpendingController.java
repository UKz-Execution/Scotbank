package uk.co.asepstrath.bank.webcontrollers;

import io.jooby.Context;
import io.jooby.ModelAndView;
import io.jooby.annotation.GET;
import io.jooby.annotation.Path;
import org.slf4j.Logger;
import uk.co.asepstrath.bank.users.User;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Path("/spending")
public class SpendingController extends WebController {
    public SpendingController() {
        super();
    }

    public SpendingController(Logger logger) {
        super(logger);
    }

    @GET("")
    public ModelAndView spendingPage(Context context) {
        if (!isLoggedIn(context)) {
            context.sendRedirect("/login");
            return null;
        }


        User user = getCurrentUser(context);
        double totalMoneyIn = 4500.50;
        double totalMoneyOut = 3200.75;
        double monthMoneyIn = 1780.90;
        double monthMoneyOut = 1487.20;

        Map<String, Object> model = new HashMap<>();
        model.put("totalMoneyIn", totalMoneyIn);
        model.put("totalMoneyOut", totalMoneyOut);
        model.put("monthMoneyIn", monthMoneyIn);
        model.put("monthMoneyOut", monthMoneyOut);
        return new ModelAndView("spending.hbs", model);
    }
}
