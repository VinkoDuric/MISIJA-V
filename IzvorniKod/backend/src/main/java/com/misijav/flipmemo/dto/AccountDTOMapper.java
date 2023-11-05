package com.misijav.flipmemo.dto;

import com.misijav.flipmemo.dto.AccountDTO;
import com.misijav.flipmemo.model.Account;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AccountDTOMapper implements Function<Account, AccountDTO> {
    @Override
    public AccountDTO apply(Account account) {
        return new AccountDTO(
                account.getId(),
                account.getEmail(),
                account.getFirstName(),
                account.getLastName(),
                account.getRole()
        );
    }

}
