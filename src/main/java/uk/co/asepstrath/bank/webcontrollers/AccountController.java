package uk.co.asepstrath.bank.webcontrollers;

import io.jooby.Context;
import io.jooby.ModelAndView;
import io.jooby.annotation.GET;
import io.jooby.annotation.Path;
import org.slf4j.Logger;
import uk.co.asepstrath.bank.accounts.Account;
import uk.co.asepstrath.bank.transactions.Transaction;


import javax.sql.DataSource;
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

    @GET("")
    public ModelAndView managerPage(Context context) {

        if (!isLoggedIn(context)) {
            context.sendRedirect("/login");
            return null;
        }

        String username = "Joe Bloggs";
        String checkingAccountNumber = "123456";
        double checkingBalance = 1536.00;
        String savingsAccountNumber = "654321";
        double savingsBalance = 6351.00;

        Map<String, Object> model = new HashMap<>();
        model.put("username", username);
        model.put("checkingAccountNumber", checkingAccountNumber);
        model.put("checkingBalance", checkingBalance);
        model.put("savingsAccountNumber", savingsAccountNumber);
        model.put("savingsBalance", savingsBalance);
        return new ModelAndView("allAccounts.hbs", model);
}

    public BigDecimal calculateCurrentAmount(double startingAmount, ArrayList<Transaction> transactions, String accountUUID) {
        BigDecimal currentAmount = BigDecimal.valueOf(startingAmount);
        for (Transaction transaction : transactions) {
            String transactionType = transaction.getType();
            switch (transactionType) {
                case "PAYMENT":
                case "WITHDRAWAL":
                    if (transaction.getFrom().equals(accountUUID)) {
                        currentAmount = currentAmount.subtract(transaction.getAmount());
                    }
                    break;
                case "DEPOSIT":
                case "COLLECT_ROUNDUPS":
                    if (transaction.getTo().equals(accountUUID)) {
                        currentAmount = transaction.getAmount().add(currentAmount);
                    }
                    break;
                case "TRANSFER":
                    if (transaction.getFrom().equals(accountUUID)) {
                        currentAmount = currentAmount.subtract(transaction.getAmount());
                    } else if (transaction.getTo().equals(accountUUID)) {
                        currentAmount = transaction.getAmount().add(currentAmount);
                    }
                    break;
                default:
                    break;
            }
        }
        return currentAmount;
    }
}
