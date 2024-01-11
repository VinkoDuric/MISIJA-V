package com.misijav.flipmemo.rest;

import com.misijav.flipmemo.dao.AccountRepository;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AdminController(AccountService accountService, AuthenticationService authenticationService) {
        this.accountService = accountService;
        this.authenticationService = authenticationService;
    }

    @Autowired
    AccountRepository racunRepo;
    @PutMapping("/makeAdmin/{email}")
    public void makeAdmin(@AuthenticationPrincipal UserDetails userDetails,
                                          @PathVariable String email) {
        Account requester = (Account) userDetails;
        logger.info("Received request to modify role to ADMIN for account {} from admin {}", email, requester.getEmail());

        accountService.updateRole(email, Roles.ADMIN);
    }
}