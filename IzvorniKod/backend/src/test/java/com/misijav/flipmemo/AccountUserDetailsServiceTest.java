package com.misijav.flipmemo;

import com.misijav.flipmemo.rest.AccountUserDetailsService;
import com.misijav.flipmemo.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class AccountUserDetailsServiceTest {

    @Mock
    private AccountService accountService;

    private AccountUserDetailsService accountUserDetailsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        accountUserDetailsService = new AccountUserDetailsService(accountService);
    }

    @Test
    public void testLoadUserByUsername_ThrowsException() {
        String email = "test@example.com";

        when(accountService.findByEmail(email)).thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            accountUserDetailsService.loadUserByUsername(email);
        });
    }
}