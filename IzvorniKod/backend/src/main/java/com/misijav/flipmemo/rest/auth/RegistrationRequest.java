package com.misijav.flipmemo.rest.auth;

import com.misijav.flipmemo.model.Roles;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record RegistrationRequest(
        String email,
        String firstName,
        String lastName
) {}
