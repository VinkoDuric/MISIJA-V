package com.misijav.flipmemo.service.impl;

import com.misijav.flipmemo.dto.AccountDTO;
import com.misijav.flipmemo.dto.AccountDTOMapper;
import com.misijav.flipmemo.jwt.JWTUtil;
import com.misijav.flipmemo.model.Account;
import com.misijav.flipmemo.rest.auth.AuthenticationRequest;
import com.misijav.flipmemo.rest.auth.AuthenticationResponse;
import com.misijav.flipmemo.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceJpa implements AuthenticationService {
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JWTUtil jwtUtil;
    @Autowired
    private final AccountDTOMapper accountDTOMapper;

    public AuthenticationServiceJpa(AuthenticationManager authenticationManager, JWTUtil jwtUtil, AccountDTOMapper accountDTOMapper) {
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
        String token = jwtUtil.issueToken(principal.getUsername(), principal.getRole().name());
        AccountDTO accountDTO = accountDTOMapper.apply(principal);
        return new AuthenticationResponse(token, accountDTO);
    }
}
