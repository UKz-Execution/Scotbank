package uk.co.asepstrath.bank.webcontrollers;

import io.jooby.Context;
import io.jooby.ModelAndView;
import io.jooby.annotation.GET;
import io.jooby.annotation.Path;
import org.slf4j.Logger;
import uk.co.asepstrath.bank.accounts.Account;
import uk.co.asepstrath.bank.transactions.Transaction;
import uk.co.asepstrath.bank.transactions.TransactionsAPI;
import uk.co.asepstrath.bank.users.User;


import java.math.BigDecimal;
import java.util.ArrayList;
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
        if (!isLoggedIn(context)) {
            context.sendRedirect("/login");
            return null;
        }

        User user = getCurrentUser(context);
        Map<String, Object> model = new HashMap<>();
        if (user.getAccounts().size() > 0) {
            Account account = user.getAccounts().get(0);
            String username = account.getName();
            String checkingAccountNumber = account.getId().toString();
            BigDecimal checkingBalance = TransactionsAPI.calculateCurrentAmount(account);

            model.put("username", username);
            model.put("checkingAccountNumber", checkingAccountNumber);
            model.put("checkingBalance", checkingBalance);
        }
        return new ModelAndView("account.hbs", model);
    }
}
