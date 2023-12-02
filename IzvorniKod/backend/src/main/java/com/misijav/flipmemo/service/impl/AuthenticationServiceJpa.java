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
import com.misijav.flipmemo.service.AuthenticationService;
import jakarta.mail.internet.MimeMessage;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.mail.javamail.MimeMessageHelper;

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
    @Autowired
    private final JavaMailSender javaMailSender;

    public AuthenticationServiceJpa(AccountRepository accountRepository,
                                    AuthenticationManager authenticationManager,
                                    AccountDTOMapper accountDTOMapper,
                                    PasswordEncoder passwordEncoder,
                                    JWTUtil jwtUtil,
                                    JavaMailSender javaMailSender) {
        this.accountRepository = accountRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.accountDTOMapper = accountDTOMapper;
        this.javaMailSender = javaMailSender;
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
    public AuthenticationResponse refresh(Authentication authentication) {
        Account principal = (Account)authentication.getPrincipal();
        Optional<Account> savedAccount = accountRepository.findByEmail(principal.getEmail());
        String token = jwtUtil.issueToken(principal.getUsername(), savedAccount.get().getTokenVersion());
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

        String randomPassword = generateRandomPassword();

        // Create new Account entity
        Account newUser = new Account(
                request.email(),
                request.firstName(),
                request.lastName(),
                passwordEncoder.encode(randomPassword),
                Roles.UNVERIFIED_USER
        );

        // save user to database
        accountRepository.save(newUser);

        // send verification email
        sendVerificationEmail(request.firstName(), request.email(), randomPassword);
    }

    /**
     * Generates a secure random password of 8 characters
     * (two lower case characters, two uppercase characters, two digits, and two special characters)
     * @return random password
     */
    private String generateRandomPassword() {
        PasswordGenerator generator = new PasswordGenerator();
        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            @Override
            public String getErrorCode() {
                return null;
            }
            @Override
            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule specialCharRule = new CharacterRule(specialChars);
        specialCharRule.setNumberOfCharacters(2);

        return generator.generatePassword(8, upperCaseRule, lowerCaseRule,
                digitRule, specialCharRule);
    }

    private void sendVerificationEmail(String userFirstName, String userEmail, String userPassword) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        // TODO change login address
        String loginAddress = "https://misija-v.onrender.com/";
        String htmlMsg = "<h2>Dear " + userFirstName + ",</h2>\n\n" +
                "<p>Thank you for registering an account on <strong>FlipMemo</strong>, the language learning application.</p>" +
                "\n\n<p style=\"margin: 5px 0;\"><strong>Your login credentials:</strong></p>" +
                "\n<p style=\"margin: 5px 0;\"><strong>Username: </strong>" + userEmail + "</p>" +
                "\n<p style=\"margin: 5px 0;\"><strong>Password: </strong>" + userPassword + "</p>" +
                "\n\n<p>We strongly recommend changing your initial password on your first login for enhanced " +
                "account security. To access your account, log in <a href="+loginAddress+">here</a>.</p>" +
                "\n\n<p style=\"font-style: italic;\">Best regards,</p>" +
                "\n<p style=\"font-style: italic;\">The FlipMemo Team</p>";
        try {
            helper.setText(htmlMsg, true);
            helper.setTo(userEmail);
            helper.setSubject("FlipMemo - Registration Successful");
            helper.setFrom("misijav.flipmemo@gmail.com");
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
