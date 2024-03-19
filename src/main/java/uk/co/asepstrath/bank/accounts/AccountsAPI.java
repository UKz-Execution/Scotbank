package uk.co.asepstrath.bank.accounts;

import kong.unirest.core.Unirest;
import org.slf4j.Logger;
import uk.co.asepstrath.bank.database.DatabaseAPI;

public class AccountsAPI {

    public AccountsAPI() {

    }

    public static void loadData(Logger logger) {
        Account[] accountResponse = Unirest.get("https://api.asep-strath.co.uk/api/accounts").asObject(Account[].class).getBody();

        try (DatabaseAPI connection = DatabaseAPI.open()) {

            for (Account account : accountResponse) {
                account.balance = account.startingBalance;
                connection.createAccount(account);
                logger.info("Account created: {name: " + account.getName() + ", startingBalance: " + account.getStartingBalance() + "}");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
