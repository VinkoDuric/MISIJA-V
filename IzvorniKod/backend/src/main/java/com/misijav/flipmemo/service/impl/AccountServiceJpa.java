package com.misijav.flipmemo.service.impl;

import com.misijav.flipmemo.dao.AccountRepository;
import com.misijav.flipmemo.model.Account;
import com.misijav.flipmemo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceJpa implements AccountService {

    @Autowired
    private AccountRepository racunRepo;

    @Override
    public Optional<Account> findByEmail(String email) {
        return racunRepo.findByEmail(email);
    }
}
