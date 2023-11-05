package com.misijav.flipmemo.rest.auth;

public record AuthenticationRequest(
        String email,
        String password
) {}
