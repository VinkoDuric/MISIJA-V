package com.misijav.flipmemo.rest;

public record AccountModificationRequest(
    String email,
    String firstName,
    String lastName,
    String password
) {}
