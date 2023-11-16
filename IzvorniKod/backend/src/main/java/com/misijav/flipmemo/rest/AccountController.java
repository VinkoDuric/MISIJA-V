package com.misijav.flipmemo.rest;

import com.misijav.flipmemo.exception.ResourceNotFoundException;
import com.misijav.flipmemo.model.Account;
import com.misijav.flipmemo.model.Roles;
import com.misijav.flipmemo.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/account")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @DeleteMapping
    public ResponseEntity<Void> DELETE(@AuthenticationPrincipal UserDetails userDetails) {
        Account requester = (Account) userDetails;

        logger.info("Received request to delete account from user with email: {}", requester.getEmail());
        accountService.deleteAccount(requester.getId());
        logger.info("Account with email {} deleted successfully.", requester.getEmail());

        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> PUT(@AuthenticationPrincipal UserDetails userDetails,
                                    @RequestBody AccountModificationRequest request) {
        Account requester = (Account) userDetails;

        logger.info("Received request to modify account from user {}", requester.getEmail());
        accountService.updateAccount(requester.getId(), request);
        logger.info("Successfully modified user account {}", requester.getEmail());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAccount/{email}")
    public ResponseEntity<Void> deleteAccount(@AuthenticationPrincipal UserDetails userDetails,
                                              @PathVariable String email) {
        Account requester = (Account) userDetails;

        logger.info("Received request to delete account with email: {}, from admin {}", email, requester.getEmail());

        // Check if user exists
        Account target = accountService.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User " + email + " not found."));

        // Check if the requester is an ADMIN
        if (requester.getRole() == Roles.ADMIN) {
            accountService.deleteAccount(target.getId());
            logger.info("Account with email {} deleted successfully.", email);
            return ResponseEntity.ok().build();
        } else {
            throw new AccessDeniedException("The user is not an ADMIN!");
        }
    }

    @PutMapping("/modifyAccount/{email}")
    public ResponseEntity<Void> modifyAccount(@AuthenticationPrincipal UserDetails userDetails,
                                              @RequestBody AccountModificationRequest request,
                                              @PathVariable String email) {
        Account requester = (Account) userDetails;
        logger.info("Received request to modify account {} from admin {}", email, requester.getEmail());

        // Check if user exists
        Account target = accountService.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User " + email + " not found."));

        // Check if the requester is an ADMIN
        if (requester.getRole() == Roles.ADMIN) {
            accountService.updateAccount(target.getId(), request);
            logger.info("Successfully modified account from {} to {}", target.getEmail(), request.email());
            return ResponseEntity.ok().build();
        } else {
            throw new AccessDeniedException("The user is not an ADMIN!");
        }
    }
}
