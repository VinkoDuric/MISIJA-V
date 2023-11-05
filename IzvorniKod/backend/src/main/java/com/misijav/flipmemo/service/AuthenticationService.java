package com.misijav.flipmemo.service;

import com.misijav.flipmemo.rest.auth.AuthenticationRequest;
import com.misijav.flipmemo.rest.auth.AuthenticationResponse;

public interface AuthenticationService {

    /**
     * Authenticate user, return auth token.
     * @param request request object containing credentials
     * @return authentication response containing token and account info
     */
    public AuthenticationResponse login(AuthenticationRequest request);

    // TODO: Add register method
}
