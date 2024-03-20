package uk.co.asepstrath.bank.webcontrollers;

import io.jooby.Context;
import io.jooby.ModelAndView;
import io.jooby.annotation.GET;
import io.jooby.annotation.Path;
import io.jooby.annotation.QueryParam;
import org.slf4j.Logger;
import uk.co.asepstrath.bank.database.DatabaseAPI;
import uk.co.asepstrath.bank.transactions.Transaction;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

        Map<String, Object> model = new HashMap<>();

        StringBuilder builder = new StringBuilder();

        try (DatabaseAPI conn = DatabaseAPI.open()) {
            for (Transaction transaction : conn.getAllTransactions()) {
                builder.append("<tr><td><a href=\"/transaction/details?uuid=");
                builder.append(transaction.getId());
                builder.append("\">");
                builder.append(transaction.getId());
                builder.append("</a></td><td>");
                builder.append(transaction.getTimestamp().toString());
                builder.append("</td><td>");
                builder.append(transaction.getType());
                builder.append("</td><td>");
                builder.append(transaction.getFrom());
                builder.append("</td><td>");
                builder.append(transaction.getTo());
                builder.append("</td><td>");
                builder.append(transaction.getAmount());
                builder.append("</td></tr>");
            }
        } catch (Exception e) {
            builder.append("<tr><td></td><td></td><td>Error loading data!</td><td></td><td></td><td></td></tr>");
        }

        model.put("transactionData", builder.toString());

        return new ModelAndView("transaction.hbs", model);
    }

    @GET("/details")
    public ModelAndView viewTransaction(Context context, @QueryParam String uuid) {

        if (!isLoggedIn(context)) {
            context.sendRedirect("/login");
            return null;
        }

        UUID id;
        try {
            id = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            return transactionsPage(context);
        }

        Map<String, Object> model = new HashMap<>();

        try (DatabaseAPI conn = DatabaseAPI.open()) {
            Transaction transaction = conn.getTransactionById(id);
            if (transaction == null) return transactionsPage(context);
            model.put("id", transaction.getId());
            model.put("time", transaction.getTimestamp().toString());
            model.put("amount", transaction.getAmount());
            model.put("type", transaction.getType());
            model.put("to", transaction.getTo());
            model.put("from", transaction.getFrom());

        } catch (Exception e) {
            return transactionsPage(context);
        }

        return new ModelAndView("transactionDetail.hbs", model);
    }
}
