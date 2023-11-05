package com.misijav.flipmemo.rest;

import com.misijav.flipmemo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountUserDetailsService implements UserDetailsService {

    @Autowired
    private final AccountService racunService;

    public AccountUserDetailsService(AccountService racunService) {
        this.racunService = racunService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return racunService.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("No email '" + email + "'")
        );
    }
}
