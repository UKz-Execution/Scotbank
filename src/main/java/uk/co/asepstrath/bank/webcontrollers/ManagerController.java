package uk.co.asepstrath.bank.webcontrollers;

import io.jooby.Context;
import io.jooby.ModelAndView;
import io.jooby.annotation.*;
import org.slf4j.Logger;
import uk.co.asepstrath.bank.accounts.Account;
import uk.co.asepstrath.bank.database.DatabaseAPI;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Path("/account/manager")
public class ManagerController extends WebController {

    public ManagerController() {
        super();
    }

    public ManagerController(Logger logger) {
        super(logger);
    }

    @GET("")
    public ModelAndView managerPage(Context context) {
        if (!isSuperUser(context)) {
            context.sendRedirect("/login");
            return null;
        }

        Map<String, Object> model = new HashMap<>();

        StringBuilder builder = new StringBuilder();

        try (DatabaseAPI conn = DatabaseAPI.open()) {
            for (Account account : conn.getAllAccounts()) {
                builder.append("<tr><td><a href=\"/account/manager/view?uuid=");
                builder.append(account.getId());
                builder.append("\">");
                builder.append(account.getId());
                builder.append("</a></td><td>");
                builder.append(account.getName());
                builder.append("</td><td>");
                builder.append(account.getBalance());
                builder.append("</td></tr>");
            }
        } catch (Exception e) {
            builder.append("<tr><td></td><td>Error loading data!</td><td></td></tr>");
        }

        model.put("accountData", builder.toString());

        return new ModelAndView("allAccounts.hbs", model);
    }

    @GET("/view")
    public ModelAndView viewAccount(Context context, @QueryParam String uuid) {
        if (!isSuperUser(context)) {
            context.sendRedirect("/login");
            return null;
        }

        UUID id;
        try {
            id = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            return managerPage(context);
        }

        Map<String, Object> model = new HashMap<>();

        log("here");

        try (DatabaseAPI conn = DatabaseAPI.open()) {
            Account account = conn.getAccountById(id);

            String username = account.getName();
            String checkingAccountNumber = account.getId().toString();
            BigDecimal checkingBalance = account.getBalance();

            model.put("username", username);
            model.put("checkingAccountNumber", checkingAccountNumber);
            model.put("checkingBalance", checkingBalance);
        } catch (Exception e) {
            model.put("username", "Unable to load data");
        }

        return new ModelAndView("account.hbs", model);
    }
}
