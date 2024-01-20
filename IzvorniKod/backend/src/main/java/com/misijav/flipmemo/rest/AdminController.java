package com.misijav.flipmemo.rest;

import com.misijav.flipmemo.model.Account;
import com.misijav.flipmemo.model.Roles;
import com.misijav.flipmemo.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final AccountService accountService;

    @Autowired
    public AdminController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PutMapping("/makeAdmin/{email}")
    public void PUT(@AuthenticationPrincipal UserDetails userDetails,
                                          @PathVariable String email) {
        Account requester = (Account) userDetails;
        logger.info("Received request to modify role to ADMIN for account {} from admin {}", email, requester.getEmail());

        accountService.updateRole(email, Roles.ADMIN);
    }
}