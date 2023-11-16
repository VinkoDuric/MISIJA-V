package com.misijav.flipmemo.service;

import com.misijav.flipmemo.model.Account;
import com.misijav.flipmemo.rest.AccountModificationRequest;

import java.util.Optional;

public interface AccountService {

    /**
     * Find an account by email.
     * @param email account's email
     * @return account if it's found
     */
    Optional<Account> findByEmail(String email);

    /**
     * Delete an account by id
     * @param id account's id
     */
    void deleteAccount(Long id);

    /**
     * Update an account by id
     * @param id account's id
     * @param request account's updated data
     */
    void updateAccount(Long id, AccountModificationRequest request);
}
