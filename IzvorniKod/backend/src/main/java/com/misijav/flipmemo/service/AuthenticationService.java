package com.misijav.flipmemo.service;

import com.misijav.flipmemo.rest.auth.AuthenticationRequest;
import com.misijav.flipmemo.rest.auth.AuthenticationResponse;
import com.misijav.flipmemo.rest.auth.RegistrationRequest;
import com.misijav.flipmemo.rest.auth.RegistrationResponse;

public interface AuthenticationService {

    /**
     * Authenticate user, return auth token.
     * @param request request object containing credentials
     * @return authentication response containing token and account info
     */
    AuthenticationResponse login(AuthenticationRequest request);

    /**
     * Invalidate cookie tokens for currently logged in user.
     */
    void logout();

    /**
     * Register a new user.
     * @param request request object containing registration data
     */
    void register(RegistrationRequest request);
}
