package com.misijav.flipmemo.rest;

public record LanguageModificationRequest(
        Long id,
        String languageName,
        String languageImage
) {}
