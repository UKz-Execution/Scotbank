package uk.co.asepstrath.bank.accounts;

import kong.unirest.core.GenericType;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import org.slf4j.Logger;
import uk.co.asepstrath.bank.database.DatabaseAPI;

import java.sql.*;
import java.util.List;

public class AccountsAPI {

    public AccountsAPI() {

    }

    public static void loadData(Logger logger) throws SQLException {
        Account[] accountResponse = Unirest.get("https://api.asep-strath.co.uk/api/accounts").asObject(Account[].class).getBody();

        try (DatabaseAPI connection = DatabaseAPI.open()) {

            for (Account account : accountResponse) {
                connection.createAccount(account);
                logger.info("Account created: {name: " + account.getName() + ", startingBalance: " + account.getStartingBalance());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
