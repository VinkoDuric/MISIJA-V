package com.misijav.flipmemo.service.impl;

import com.misijav.flipmemo.dao.AccountRepository;
import com.misijav.flipmemo.dto.AccountDTO;
import com.misijav.flipmemo.dto.AccountDTOMapper;
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
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthenticationServiceJpa implements AuthenticationService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JWTUtil jwtUtil;
    @Autowired
    private final AccountDTOMapper accountDTOMapper;
    //@Autowired
    //private JavaMailSender javaMailSender;

    public AuthenticationServiceJpa(AccountRepository accountRepository,
                                    AuthenticationManager authenticationManager,
                                    JWTUtil jwtUtil,
                                    AccountDTOMapper accountDTOMapper) {
        this.accountRepository = accountRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
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
    public RegistrationResponse register(RegistrationRequest request) {
        // check if email is valid
        String regexPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher m = pattern.matcher(request.getEmail());
        if (!m.matches()) {
            return new RegistrationResponse("Email not valid");
        }

        // check if email is unique
        if (accountRepository.findByEmail(request.getEmail()).isPresent()) {
            return new RegistrationResponse("Email already in use");
        }

        // TODO hash password
        String hashedPassword = request.getPassword();

        // Create new Account entity
        Account newUser = new Account(
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                hashedPassword,
                Roles.USER
        );

        // save user to database
        accountRepository.save(newUser);

        // TODO generate verification token
        String verificationToken = "verificationtoken";

        // send verification email
        sendVerificationEmail(request.getEmail(), verificationToken);

        return new RegistrationResponse("You have been successfully registered. To activate your account check your " +
                "email and confirm your registration.");
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
