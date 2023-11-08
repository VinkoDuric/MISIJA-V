package com.misijav.flipmemo.rest.auth;

import com.misijav.flipmemo.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    @Autowired
    private final AuthenticationService authenticationService;
    @Autowired
    private  final  AuthenticationService accountService;

    public AuthenticationController(AuthenticationService accountService, AuthenticationService authenticationService) {
        this.accountService = accountService;
        this.authenticationService = authenticationService;
    }

    private Cookie getAuthCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            throw new BadCredentialsException("Login before you can refresh connection.");
        }

        Optional<Cookie> cookie = Arrays.stream(cookies).filter((c) -> c.getName().equals("auth")).findFirst();
        return cookie.orElse(null);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        AuthenticationResponse responseData = authenticationService.login(request);
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

    @GetMapping("logout")
    public void login(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = getAuthCookie(request);

        if (cookie == null) {
            throw new BadCredentialsException("Login before you can refresh connection.");
        }
        accountService.logout();

        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.setStatus(HttpStatus.OK.value());
    }

    @GetMapping("refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = getAuthCookie(request);

        if (cookie == null) {
            throw new BadCredentialsException("Login before you can refresh connection.");
        }

        cookie.setMaxAge((int)Duration.ofMinutes(15).getSeconds());
        response.addCookie(cookie);
        response.setStatus(HttpStatus.OK.value());
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        authenticationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegistrationResponse("You have been successfully registered. " +
                        "To activate your account check your email and confirm your registration."));
    }
}
