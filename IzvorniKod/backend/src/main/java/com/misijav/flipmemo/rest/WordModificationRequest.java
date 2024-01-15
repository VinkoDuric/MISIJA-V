package com.misijav.flipmemo.rest;

import jakarta.validation.constraints.NotNull;

public record WordModificationRequest(
    @NotNull(message = "Word id must not be null")
    Long id,
    String wordName,
    String wordDescription
) {}
