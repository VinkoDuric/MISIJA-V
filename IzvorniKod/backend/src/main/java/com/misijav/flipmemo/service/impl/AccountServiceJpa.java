package com.misijav.flipmemo.service.impl;

import com.misijav.flipmemo.dao.AccountRepository;
import com.misijav.flipmemo.exception.RequestValidationException;
import com.misijav.flipmemo.exception.ResourceConflictException;
import com.misijav.flipmemo.exception.ResourceNotFoundException;
import com.misijav.flipmemo.model.Account;
import com.misijav.flipmemo.model.Roles;
import com.misijav.flipmemo.rest.AccountModificationRequest;
import com.misijav.flipmemo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AccountServiceJpa implements AccountService {

    private final AccountRepository accountRepository;
    private  final PasswordEncoder passwordEncoder;

@Autowired
    public AccountServiceJpa(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id " + id));

        accountRepository.delete(account);
    }

    @Override
    public void updateAccount(Long id, AccountModificationRequest request) {
        Account account = accountRepository.findUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id " + id));

        // check if new email is valid
        String regexPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher m = pattern.matcher(request.email());
        if (!m.matches()) {
            throw new RequestValidationException("Email is not valid.");
        }

        // check if new email is unique
        if (accountRepository.findByEmail(request.email()).isPresent() &&
                !(account.getEmail().equals(request.email()))) {
            throw new ResourceConflictException("Email is already in use.");
        }

        // Update the existing Account entity
        account.setEmail(request.email());
        account.setFirstName(request.firstName());
        account.setLastName(request.lastName());

        account.setPassword(passwordEncoder.encode(request.password()));
        if (account.getTokenVersion() == 0) {
            account.setRole(Roles.USER);
        }
        account.setTokenVersion(account.getTokenVersion() + 1);

        // Save the updated user account to the database
        accountRepository.save(account);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }
}
