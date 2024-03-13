package uk.co.asepstrath.bank.webcontrollers;

import io.jooby.Context;
import io.jooby.ModelAndView;
import io.jooby.annotation.GET;
import io.jooby.annotation.Path;
import org.slf4j.Logger;
import uk.co.asepstrath.bank.accounts.Account;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Path("/account")
public class AccountController extends WebController {
    public AccountController() {
        super();
    }

    public AccountController(Logger logger) {
        super(logger);
    }

    @GET("")
    public ModelAndView accountPage(Context context) {

        Account account = getCurrentAccount(context);
        if (account == null) {
            context.sendRedirect("/login");
            return null;
        }

        String username = account.getName();
        String checkingAccountNumber = account.getId().toString();
        BigDecimal checkingBalance = account.getBalance();
        String savingsAccountNumber = account.getId().toString();
        BigDecimal savingsBalance = account.getBalance();

        Map<String, Object> model = new HashMap<>();
        model.put("username", username);
        model.put("checkingAccountNumber", checkingAccountNumber);
        model.put("checkingBalance", checkingBalance);
        model.put("savingsAccountNumber", savingsAccountNumber);
        model.put("savingsBalance", savingsBalance);
        return new ModelAndView("account.hbs", model);
    }
}
