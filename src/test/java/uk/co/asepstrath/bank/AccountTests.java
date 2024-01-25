package uk.co.asepstrath.bank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountTests {

    @Test
    public void createAccount(){
        Account a = new Account();
        Assertions.assertNotNull(a);
    }

    @Test //Test 1
    public void newAccount(){
        Account a = new Account();
        Assertions.assertEquals(0, a.getBalance());
    }

    @Test //Test 2
    public void addFunds(){
        Account a = new Account();
        a.deposit(20);
        Assertions.assertEquals(20, a.getBalance());
        a.deposit(50);
        Assertions.assertEquals(70, a.getBalance());
    }

    @Test //Test 3
    public void withdraw(){
        Account a = new Account();
        a.deposit(40);
        a.withdraw(20);
        Assertions.assertEquals(20, a.getBalance());
    }

    @Test //Test 4
    public void overdraft(){
        Account a = new Account();
        a.deposit(30); //Starting balance
        Assertions.assertFalse(a.withdraw(100));
    }

    @Test //Test 5
    public void mulDeposit(){
        Account a = new Account();
        a.deposit(20); //Starting balance
        a.deposit(10);
        a.deposit(10);
        a.deposit(10);
        a.deposit(10);
        a.deposit(10);
        a.withdraw(20);
        a.withdraw(20);
        a.withdraw(20);
        Assertions.assertEquals(10,a.getBalance());
    }

    @Test //Test 6
    public void decBalance(){
        Account a = new Account();
        a.deposit(5.45); //Starting balance
        a.withdraw(17.56);
        Assertions.assertEquals(23.01, a.getBalance());
    }
}
