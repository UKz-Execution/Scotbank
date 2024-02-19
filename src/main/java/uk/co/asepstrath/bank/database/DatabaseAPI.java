package uk.co.asepstrath.bank.database;

import uk.co.asepstrath.bank.Account;
import uk.co.asepstrath.bank.example.ExampleController;
import io.jooby.Jooby;
import io.jooby.handlebars.HandlebarsModule;
import io.jooby.helper.UniRestExtension;
import io.jooby.hikari.HikariModule;
import org.slf4j.Logger;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseAPI extends Account{
    private static DataSource db = null;

    public static void initDatabase(DataSource src) {
        db = src;
    }

    public void openConnection() {
        if (db == null) {
            throw new RuntimeException("Unable to connect to database, app has not started.");
        }
    }

    public static void storeAccount(Account account) { // Stores an account in the database
        try (Connection conn = db.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "INSERT INTO accounts (id, name, balance) VALUES (?, ?, ?)")) {
            statement.setString(1, String.valueOf(account.getId()));
            statement.setBigDecimal(2, account.getBalance());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Account> getAllAccounts() { // Fetches all accounts stored in database
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = db.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts")) {
            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                int balance = resultSet.getInt("balance");
                accounts.add(new Account(id, name, balance, roundUpEnabled));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public Account getAccountById(UUID id) { // Fetches account by id
        try (Connection conn = db.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM accounts WHERE account_number = ?")) {
            statement.setString(1, String.valueOf(id));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int balance = resultSet.getInt("balance");
                    return new Account(id, name, balance, roundUpEnabled);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account getAccountByName(String name) { // Fetches account by name
        try (Connection conn = db.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM accounts WHERE name = ?")) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int balance = resultSet.getInt("balance");
                    return new Account(id, name, balance, roundUpEnabled);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
