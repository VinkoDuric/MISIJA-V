package com.misijav.flipmemo.rest;

import com.misijav.flipmemo.exception.ResourceNotFoundException;
import com.misijav.flipmemo.model.Account;
import com.misijav.flipmemo.model.Roles;
import com.misijav.flipmemo.rest.auth.AuthenticationRequest;
import com.misijav.flipmemo.rest.auth.AuthenticationResponse;
import com.misijav.flipmemo.service.AccountService;
import com.misijav.flipmemo.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("api/v1/account")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AccountController(AccountService accountService, AuthenticationService authenticationService) {
        this.accountService = accountService;
        this.authenticationService = authenticationService;
    }

    @DeleteMapping
    public ResponseEntity<?> DELETE(@AuthenticationPrincipal UserDetails userDetails,
                                    HttpServletResponse response) {
        Account requester = (Account) userDetails;

        // delete user account
        logger.info("Received request to delete account from user with email: {}", requester.getEmail());
        accountService.deleteAccount(requester.getId());
        logger.info("Account with email {} deleted successfully.", requester.getEmail());

        // Create new cookie to invalidate session
        ResponseCookie invalidatedCookie = ResponseCookie.from("auth", "")
                .httpOnly(true)
                .secure(false)
                .path("/api/v1")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, invalidatedCookie.toString());

        return ResponseEntity.ok("User account " + requester.getEmail() + " deleted successfully.");
    }

    @PutMapping
    public ResponseEntity<?> PUT(@AuthenticationPrincipal UserDetails userDetails,
                                 @RequestBody AccountModificationRequest accModifyRequest,
                                 HttpServletResponse response) {
        Account requester = (Account) userDetails;

        // Update user data
        logger.info("Received request to modify account from user {}", requester.getEmail());
        Account updatedUserAccount = accountService.updateAccount(requester.getId(), accModifyRequest);
        logger.info("Successfully modified user account {} to {}", requester.getEmail(), updatedUserAccount.getEmail());

        // If the user changes the password, create a new cookie
        if (accModifyRequest.password() != null && !accModifyRequest.password().isEmpty()) {
            // Create auth request with new user data
            AuthenticationRequest authenticationRequest = new AuthenticationRequest(updatedUserAccount.getEmail(),
                    accModifyRequest.password());

            // Log in user with new credentials
            AuthenticationResponse responseData = authenticationService.login(authenticationRequest);

            // Create new cookie
            ResponseCookie cookie = ResponseCookie.from("auth", responseData.token())
                    .httpOnly(true)
                    .secure(false)
                    .path("/api/v1")
                    .maxAge(Duration.ofHours(15))
                    .sameSite("Lax")
                    .build();

            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity.ok()
                    .body(responseData.account());
        }

        // If the user changes the email, delete the old cookie (user needs to log in again)
        else if (accModifyRequest.email() != null && !accModifyRequest.email().isEmpty()) {
            // Create new cookie to invalidate session
            ResponseCookie invalidatedCookie = ResponseCookie.from("auth", "")
                    .httpOnly(true)
                    .secure(false)
                    .path("/api/v1")
                    .maxAge(0)
                    .sameSite("Lax")
                    .build();

            response.setHeader(HttpHeaders.SET_COOKIE, invalidatedCookie.toString());

            return ResponseEntity.ok("User " + updatedUserAccount.getEmail() + " needs to log in again.");
        }

        // If the user changes just firstName and/or lastName, send updated user info to frontend
        AuthenticationResponse responseData = authenticationService.refresh(
                SecurityContextHolder.getContext().getAuthentication());

        return ResponseEntity.ok()
                .body(responseData.account());
    }

    @DeleteMapping("/deleteAccount/{email}")
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal UserDetails userDetails,
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
            return ResponseEntity.ok("User account " + email + " deleted successfully.");
        } else {
            throw new AccessDeniedException("The user is not an ADMIN!");
        }
    }

    @PutMapping("/modifyAccount/{email}")
    public ResponseEntity<?> modifyAccount(@AuthenticationPrincipal UserDetails userDetails,
                                              @RequestBody AccountModificationRequest request,
                                              @PathVariable String email) {
        Account requester = (Account) userDetails;
        logger.info("Received request to modify account {} from admin {}", email, requester.getEmail());

        // Check if user exists
        Account target = accountService.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User " + email + " not found."));

        // Check if the requester is an ADMIN
        if (requester.getRole() == Roles.ADMIN) {
            Account updatedUserAccount = accountService.updateAccount(target.getId(), request);
            logger.info("Successfully modified account from {} to {}", email, updatedUserAccount.getEmail());
            return ResponseEntity.ok("Account modified successfully.");
        } else {
            throw new AccessDeniedException("The user is not an ADMIN!");
        }
    }
}
