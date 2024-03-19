package uk.co.asepstrath.bank.users;

import uk.co.asepstrath.bank.accounts.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private final String username, password;
    private final List<Account> accounts;

    public User(String username, String password) {
        this(username, password, new ArrayList<>());
    }

    public User(String username, String password, List<Account> accounts) {
        this.username = username;
        this.password = password;
        this.accounts = accounts;
    }

    public String getUsername() {
        return username;
    }

    public boolean isCorrectPassword(String password) {
        return this.password.equals(password);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public boolean removeAccount(Account account) {
        return removeAccount(account.getId());
    }

    public boolean removeAccount(UUID uuid) {
        return accounts.removeIf(account -> account.getId().equals(uuid));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(username + ",false");
        for (Account account : accounts) {
            builder.append(",");
            builder.append(account.getId().toString());
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof User user)) return false;
        return user.getUsername().equals(username);
    }

    @Override
    public int hashCode() {
        return 17 * 31 + username.hashCode();
    }

}
