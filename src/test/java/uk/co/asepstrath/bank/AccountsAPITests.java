package uk.co.asepstrath.bank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.co.asepstrath.bank.accounts.AccountsAPI;

public class AccountsAPITests {

    @Test
    public void init() {
        Assertions.assertThrows(RuntimeException.class, AccountsAPI::loadData);
    }

}
