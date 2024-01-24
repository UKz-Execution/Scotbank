package uk.co.asepstrath.bank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountTests {

    @Test
    public void createAccount(){
        Account a = new Account();
        Assertions.assertNotNull(a);
    }

    @Test
    public void newAccount(){
        Account a = new Account();
        Assertions.assertEquals(0, a.getBalance());
    }

    @Test
    public void addFunds(){
        Account a = new Account();
        a.deposit(20);
        Assertions.assertEquals(20, a.getBalance());
        a.deposit(50);
        Assertions.assertEquals(70, a.getBalance());
    }

    @Test
    public void withdraw(){
        Account a = new Account();
        a.deposit(40);
        a.withdraw(20);
        Assertions.assertEquals(20, a.getBalance());
    }
}
