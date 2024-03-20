package uk.co.asepstrath.bank.webcontrollers;

import io.jooby.Context;
import io.jooby.ModelAndView;
import io.jooby.annotation.GET;
import io.jooby.annotation.Path;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Path("/transaction")
public class TransactionController extends WebController {

    public TransactionController() {
        super();
    }

    public TransactionController(Logger logger) {
        super(logger);
    }

    @GET("")
    public ModelAndView transactionsPage(Context context) {

        if (!isLoggedIn(context)) {
            context.sendRedirect("/login");
            return null;
        }

        User user = getCurrentUser(context);
        String id = "1234456";
        double amount = 140.00;
        String to = "Business Account";
        String from = "Personal Account";


        Map<String, Object> model = new HashMap<>();

        model.put("id", id);
        model.put("amount", amount);
        model.put("to", to);
        model.put("from", from);

        return new ModelAndView("transaction.hbs", model);
    }

    @GET("/transactionDetail")
    public ModelAndView transactionsDetailsPage(Context context) {

        if (!isLoggedIn(context)) {
            context.sendRedirect("/login");
            return null;
        }

        String id = "1234456";
        String time = "16:39";
        double amount = 140.00;
        String type = "Business";
        String to = "Business Account";
        String from = "Personal Account";


        Map<String, Object> model = new HashMap<>();

        model.put("id", id);
        model.put("time", time);
        model.put("amount", amount);
        model.put("type", type);
        model.put("to", to);
        model.put("from", from);

        return new ModelAndView("transactionDetail.hbs", model);
    }
}
