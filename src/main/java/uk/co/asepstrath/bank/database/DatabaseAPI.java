package uk.co.asepstrath.bank.database;

import uk.co.asepstrath.bank.accounts.Account;
import uk.co.asepstrath.bank.transactions.Transaction;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;
import java.time.LocalDateTime;

public class DatabaseAPI implements AutoCloseable {
    private static DataSource db = null;

    private final Connection conn;

    public static void initDatabase(DataSource src) throws Exception {
        db = src;
        try (DatabaseAPI conn = open()) {
            conn.initialiseDatabase();
        }
    }

    private DatabaseAPI(Connection conn) {
        this.conn = conn;
    }

    public static DatabaseAPI open() { // Unsure if this method is needed, leaving it just in case
        if (db == null) {
            throw new RuntimeException("Unable to connect to database, app has not started.");
        }

        try {
            Connection connection = db.getConnection();
            return new DatabaseAPI(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        conn.close();
    }

    private void initialiseDatabase() throws SQLException {
        try (Statement createTable = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)){
        createTable.executeUpdate("""
                CREATE TABLE IF NOT EXISTS accounts (
                 id varchar(36) NOT NULL,
                 `name` text NOT NULL,
                 startingBalance decimal NOT NULL,
                 balance double NOT NULL,
                 roundUpEnabled boolean NOT NULL);""");

        createTable.executeUpdate("""
                CREATE TABLE IF NOT EXISTS transactions (
                 `timestamp` text NOT NULL,
                 id varchar(36) NOT NULL,
                 type text NOT NULL,
                 amount double NOT NULL,
                 `to` text,
                 `from` text);""");
        }
    }


    public void createAccount(Account account) throws SQLException { // Stores an account in the database
        String sql = "INSERT INTO accounts (id, `name`, startingBalance, balance, roundUpEnabled) VALUES (?,?,?,?,?)";
        try (PreparedStatement prep = conn.prepareStatement(sql)) {
            prep.setString(1, account.getId().toString());
            prep.setString(2, account.getName());
            prep.setBigDecimal(3, account.getStartingBalance());
            prep.setBigDecimal(4, account.getBalance());
            prep.setBoolean(5, account.isRoundUpEnabled());
            prep.executeUpdate();
        }
    }

    public void createTransaction(Transaction transaction) throws SQLException { // Stores a transaction in the database
        String sql = "INSERT INTO transactions (`timestamp`, id, type, amount, `to`, `from`) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement prep = conn.prepareStatement(sql)) {
            prep.setString(1, transaction.getTimestamp().toString());
            prep.setString(2, transaction.getId().toString());
            prep.setString(3, transaction.getType());
            prep.setBigDecimal(4, transaction.getAmount());
            prep.setString(5, transaction.getTo());
            prep.setString(6, transaction.getFrom());
            prep.executeUpdate();
        }
    }

    public void updateAccountName(Account account) throws SQLException {
        updateAccountName(account.getId(), account.getName());
    }

    public void updateAccountName(UUID id, String name) throws SQLException { // Updates name according to account id
        try (PreparedStatement statement = conn.prepareStatement("UPDATE accounts SET `name` = ? WHERE id = ?")) {
            statement.setString(1, name);
            statement.setString(2, id.toString());
            statement.executeUpdate();
        }
    }

    public void updateAccountBalance(Account account) throws SQLException {
        updateAccountBalance(account.getId(), account.getBalance());
    }

    public void updateAccountBalance(UUID id, BigDecimal balance) throws SQLException { // Updates balance according to account id
        try (PreparedStatement statement = conn.prepareStatement("UPDATE accounts SET balance = ? WHERE id = ?;")) {
            statement.setBigDecimal(1, balance);
            statement.setString(2, id.toString());
            statement.executeUpdate();
        }
    }

    public ArrayList<Account> getAllAccounts() throws SQLException { // Fetches all accounts stored in database
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts");
            return getAccountsFromResult(resultSet);
        }
    }

    public ArrayList<Transaction> getAllTransactions() throws SQLException { // Fetches all transactions stored in database
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM transactions");
            return getTransactionsFromResult(resultSet);
        }
    }

    public Account getAccountById(UUID id) throws SQLException { // Fetches account by id
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM accounts WHERE id = ?")) {
            statement.setString(1, id.toString());
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Account> accountData = getAccountsFromResult(resultSet);
            if (accountData.size() != 1) return null;
            return accountData.get(0);
        }
    }

    public Transaction getTransactionById(UUID id) throws SQLException { // Fetches transaction by id
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM transactions WHERE id = ?")) {
            statement.setString(1, id.toString());
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Transaction> transactionData = getTransactionsFromResult(resultSet);
            if (transactionData.size() != 1) return null;
            return transactionData.get(0);
        }
    }

    private ArrayList<Account> getAccountsFromResult(ResultSet resultSet) throws SQLException {
        ArrayList<Account> accounts = new ArrayList<>();
        while (resultSet.next()) {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            BigDecimal startingBalance = resultSet.getBigDecimal("startingBalance");
            BigDecimal balance = resultSet.getBigDecimal("balance");
            boolean roundUpEnabled = resultSet.getBoolean("roundUpEnabled");
            accounts.add(new Account(id, name, startingBalance, balance, roundUpEnabled));
        }
        return accounts;
    }

    private ArrayList<Transaction> getTransactionsFromResult(ResultSet resultSet) throws SQLException {
        ArrayList<Transaction> transactions = new ArrayList<>();
        while (resultSet.next()) {
            LocalDateTime timestamp = LocalDateTime.parse(resultSet.getString("timestamp"));
            UUID id = UUID.fromString(resultSet.getString("id"));
            String type = resultSet.getString("type");
            BigDecimal amount = resultSet.getBigDecimal("amount");
            String to = resultSet.getString("to");
            String from = resultSet.getString("from");
            transactions.add(new Transaction(timestamp, id, type, amount, to, from));
        }
        return transactions;
    }
}
