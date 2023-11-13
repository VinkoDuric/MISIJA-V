package com.misijav.flipmemo.service;

import com.misijav.flipmemo.model.Account;

import java.util.Optional;

public interface AccountService {

    /**
     * Find an account by email.
     * @param email account's email
     * @return account if it's found
     */
    Optional<Account> findByEmail(String email);

    /**
     * Delete an account by email
     * @param email account's email
     */
    void deleteAccount(String email);
}
