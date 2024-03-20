package uk.co.asepstrath.bank;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.asepstrath.bank.accounts.Account;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountTests {

    private static Account a;

    @BeforeAll
    public static void initialise() {
        a = new Account();
        a = new Account(UUID.randomUUID(), "Test Account", 0, false);
        a = new Account(UUID.fromString("635e583f-0af2-47cb-9625-5b66ba30e188"), "Test Account", BigDecimal.valueOf(0), false);
    }

    @BeforeEach
    public void resetBalance() {
        a.withdraw(a.getBalance());
    }

    @Test
    public void createAccount(){
        Assertions.assertNotNull(a);
    }

    @Test //Test 1
    public void newAccount(){
        Assertions.assertEquals(a.getBalance().compareTo(BigDecimal.valueOf(0)),0);
    }

    @Test //Test 2
    public void addFunds(){
        a.deposit(20);
        Assertions.assertEquals(a.getBalance().compareTo(BigDecimal.valueOf(20)),0);
        a.deposit(50);
        Assertions.assertEquals(a.getBalance().compareTo(BigDecimal.valueOf(70)),0);
    }

    @Test //Test 3
    public void withdraw(){
        a.deposit(40);
        a.withdraw(20);
        Assertions.assertEquals(a.getBalance().compareTo(BigDecimal.valueOf(20)),0);
    }

    @Test //Test 4
    public void overdraft(){
        a.deposit(30); //Starting balance
        Assertions.assertThrows(ArithmeticException.class, () -> a.withdraw(100));
    }

    @Test //Test 5
    public void mulDeposit(){
        a.deposit(20); //Starting balance
        a.deposit(10);
        a.deposit(10);
        a.deposit(10);
        a.deposit(10);
        a.deposit(10);
        a.withdraw(20);
        a.withdraw(20);
        a.withdraw(20);
        Assertions.assertEquals(a.getBalance().compareTo(BigDecimal.valueOf(10)),0);
    }

    @Test //Test 6
    public void decBalance(){
        a.deposit(5.45); //Starting balance
        a.deposit(17.56);
        Assertions.assertEquals(a.getBalance().compareTo(BigDecimal.valueOf(23.01)),0);

    }

    @Test
    public void testGetters() {
        Assertions.assertEquals("635e583f-0af2-47cb-9625-5b66ba30e188", a.getId().toString());
        Assertions.assertEquals("Test Account", a.getName());
        Assertions.assertEquals(BigDecimal.ZERO, a.getStartingBalance());
        Assertions.assertFalse(a.isRoundUpEnabled());
        Assertions.assertEquals("Name: Test Account, Balance: 0.0", a.toString());
    }

    @Test //Test for task 4
    public void exposeData(){
//        Account a = new Account("John", new BigDecimal("102.80"));
//        Assertions.assertEquals(a.toString(), "Name: John, Balance: 102.80");
    }

    @Test
    public void testNullParameters() {
        try {
            new Account(null, "John Doe", BigDecimal.valueOf(100), BigDecimal.valueOf(100), true);
            Assertions.fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            // Expected
            Assertions.assertEquals("Account data cannot be null.", e.getMessage());
        }
    }

    @Test
    public void testNegativeBalance() {
        // Act & Assert
        try {
            new Account(UUID.randomUUID(), "John Doe", BigDecimal.valueOf(-100), BigDecimal.valueOf(100), true);
            Assertions.fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            // Expected
            Assertions.assertEquals("Account balance cannot be less than 0", e.getMessage());
        }
    }

}
