package com.misijav.flipmemo.dao;

import com.misijav.flipmemo.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findUserById(Long id);
}
