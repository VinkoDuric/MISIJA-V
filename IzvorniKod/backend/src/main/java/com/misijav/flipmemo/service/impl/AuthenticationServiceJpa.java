package com.misijav.flipmemo.service.impl;

import com.misijav.flipmemo.dao.AccountRepository;
import com.misijav.flipmemo.dto.AccountDTO;
import com.misijav.flipmemo.dto.AccountDTOMapper;
import com.misijav.flipmemo.exception.RequestValidationException;
import com.misijav.flipmemo.exception.ResourceConflictException;
import com.misijav.flipmemo.jwt.JWTUtil;
import com.misijav.flipmemo.model.Account;
import com.misijav.flipmemo.model.Roles;
import com.misijav.flipmemo.rest.auth.AuthenticationRequest;
import com.misijav.flipmemo.rest.auth.AuthenticationResponse;
import com.misijav.flipmemo.rest.auth.RegistrationRequest;
import com.misijav.flipmemo.rest.auth.RegistrationResponse;
import com.misijav.flipmemo.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthenticationServiceJpa implements AuthenticationService {

    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final AccountDTOMapper accountDTOMapper;
    @Autowired
    private  final PasswordEncoder passwordEncoder;
    @Autowired
    private final JWTUtil jwtUtil;

    //@Autowired
    //private JavaMailSender javaMailSender;

    public AuthenticationServiceJpa(AccountRepository accountRepository,
                                    AuthenticationManager authenticationManager,
                                    AccountDTOMapper accountDTOMapper,
                                    PasswordEncoder passwordEncoder,
                                    JWTUtil jwtUtil) {
        this.accountRepository = accountRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.accountDTOMapper = accountDTOMapper;
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        Account principal = (Account)authentication.getPrincipal();
        String token = jwtUtil.issueToken(principal.getUsername(), principal.getTokenVersion());
        AccountDTO accountDTO = accountDTOMapper.apply(principal);
        return new AuthenticationResponse(token, accountDTO);
    }

    @Override
    public void logout() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        account.incrementTokenVersion();
        accountRepository.save(account);
    }

    @Override
    public void register(RegistrationRequest request) {
        // check if email is valid
        String regexPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher m = pattern.matcher(request.email());
        if (!m.matches()) {
            throw new RequestValidationException("Email is not valid.");
        }

        // check if email is unique
        if (accountRepository.findByEmail(request.email()).isPresent()) {
            throw new ResourceConflictException("Email is already in use.");
        }

        String randomPassword = "some-random-password";

        // Create new Account entity
        Account newUser = new Account(
                request.email(),
                request.firstName(),
                request.lastName(),
                passwordEncoder.encode(randomPassword),
                Roles.USER
        );

        // save user to database
        accountRepository.save(newUser);

        // send verification email
        sendVerificationEmail(request.email(), randomPassword);
    }

    private void sendVerificationEmail(String userEmail, String verificationToken) {
        /*SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Flipmemo - Verification Email");
        // TODO change url
        message.setText("To verify your email, click the following link: " +
                "http://localhost/verify?token=" + verificationToken);
        javaMailSender.send(message);
         */
    }
}
