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

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        AuthenticationResponse responseData = authenticationService.login(request);
        ResponseCookie cookie = ResponseCookie.from("auth", responseData.token())
                .httpOnly(true)
                .secure(true)
                .maxAge(Duration.ofMinutes(15))
                .sameSite("Lax")
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok()
                .body(responseData.account());
    }

    @GetMapping("logout")
    public void login(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("auth", "")
                .httpOnly(true)
                .secure(true)
                .maxAge(0)
                .build();

        accountService.logout();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        response.setStatus(HttpStatus.OK.value());
    }

    @GetMapping("refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            throw new BadCredentialsException("Login before you can refresh connection.");
        }

        Optional<Cookie> cookie = Arrays.stream(cookies).filter((c) -> c.getName().equals("auth")).findFirst();

        if (cookie.isEmpty()) {
            throw new BadCredentialsException("Login before you can refresh connection.");
        }

        cookie.get().setMaxAge((int)Duration.ofMinutes(15).getSeconds());
        response.addCookie(cookie.get());
        response.setStatus(HttpStatus.OK.value());
    }
}
