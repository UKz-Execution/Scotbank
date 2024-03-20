package uk.co.asepstrath.bank.users;

import uk.co.asepstrath.bank.accounts.Account;

import java.util.List;

public class SuperUser extends User {

    public SuperUser(String username, String password) {
        super(username, password);
    }

    public SuperUser(String username, String password, List<Account> accounts) {
        super(username, password, accounts);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(username + "," + password + ",true");
        for (Account account : accounts) {
            builder.append(",");
            builder.append(account.getId().toString());
        }
        return builder.toString();
    }
}
