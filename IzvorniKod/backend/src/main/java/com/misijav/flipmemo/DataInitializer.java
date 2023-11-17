package com.misijav.flipmemo;

import com.misijav.flipmemo.dao.AccountRepository;
import com.misijav.flipmemo.model.Account;
import com.misijav.flipmemo.model.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    private AccountRepository racunRepo;

    @Autowired
    private PasswordEncoder pwdEncoder;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        Account racun = new Account(
                "some.mail@gmail.com",
                "Ivica",
                "Ivic",
                pwdEncoder.encode("password"),
                Roles.ADMIN);

        racunRepo.save(racun);
    }
}
