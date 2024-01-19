package com.misijav.flipmemo;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountTest {

    @Test
    public void testGettersAndSetters() {
        Account account = new Account("test@example.com", "John", "Doe", "password", Roles.USER);

        Assertions.assertEquals("test@example.com", account.getEmail());
        Assertions.assertEquals("John", account.getFirstName());
        Assertions.assertEquals("Doe", account.getLastName());
        Assertions.assertEquals("password", account.getPassword());
        Assertions.assertEquals(Roles.USER, account.getRole());

        account.setEmail("newemail@example.com");
        account.setFirstName("Jane");
        account.setLastName("Smith");
        account.setPassword("newpassword");
        account.setRole(Roles.ADMIN);

        Assertions.assertEquals("newemail@example.com", account.getEmail());
        Assertions.assertEquals("Jane", account.getFirstName());
        Assertions.assertEquals("Smith", account.getLastName());
        Assertions.assertEquals("newpassword", account.getPassword());
        Assertions.assertEquals(Roles.ADMIN, account.getRole());
    }

    @Test
    public void testTokenVersion() {
        Account account = new Account("test@example.com", "John", "Doe", "password", Roles.USER);

        Assertions.assertEquals(0L, account.getTokenVersion());

        account.incrementTokenVersion();
        Assertions.assertEquals(1L, account.getTokenVersion());

        account.setTokenVersion(5L);
        Assertions.assertEquals(5L, account.getTokenVersion());
    }

    @Test
    public void testToString() {
        Account account = new Account("test@example.com", "John", "Doe", "password", Roles.USER);

        String expectedToString = "Account{" +
                "id=null" +
                ", email='test@example.com'" +
                ", firstName='John'" +
                ", lastName='Doe'" +
                ", password='password'" +
                ", role=USER" +
                ", tokenVersion=0" +
                '}';

        Assertions.assertEquals(expectedToString, account.toString());
    }
}