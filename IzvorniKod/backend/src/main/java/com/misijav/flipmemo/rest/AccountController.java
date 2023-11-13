package com.misijav.flipmemo.rest;

import com.misijav.flipmemo.model.Account;
import com.misijav.flipmemo.model.Roles;
import com.misijav.flipmemo.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/account")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @DeleteMapping("/deleteAccount/{email}")
    public ResponseEntity<Void> deleteAccount(@AuthenticationPrincipal UserDetails userDetails,
                                              @PathVariable String email) {
        Account requester = (Account) userDetails;

        logger.info("Received request to delete account with email: {}, from user with email: {}", email, requester.getEmail());

        Account target = accountService.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        // Check if the requester is an ADMIN or the target user
        if (requester.getRole() == Roles.ADMIN || requester.getId().equals(target.getId())) {
            accountService.deleteAccount(target.getEmail());
            logger.info("Account with email {} deleted successfully.", email);
            return ResponseEntity.ok().build();
        } else {
            // If the requester is not an ADMIN or the target user, return forbidden
            return ResponseEntity.status(403).build();
        }
    }
}
