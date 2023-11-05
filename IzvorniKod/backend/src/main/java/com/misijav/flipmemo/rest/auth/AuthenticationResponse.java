package com.misijav.flipmemo.rest.auth;

import com.misijav.flipmemo.dto.AccountDTO;

public record AuthenticationResponse(
        String token,
        AccountDTO account
) {}