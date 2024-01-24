package uk.co.asepstrath.bank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountTests {

    @Test
    public void createAccount(){
        Account a = new Account();
        Assertions.assertTrue(a != null);
    }

    @Test
    public void newAccount(){
        Account a = new Account();
        Assertions.assertTrue(a.getBalance() == 0);
    }
// blah blah blah
//Pink frogs
}
