package com.misijav.flipmemo.dto;

import com.misijav.flipmemo.model.Roles;

public record AccountDTO(
        Long id,
        String email,
        String firstName,
        String lastName,
        Roles role,
        Long tokenVersion
) {}
