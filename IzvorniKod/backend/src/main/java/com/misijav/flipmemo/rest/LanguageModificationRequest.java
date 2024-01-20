package com.misijav.flipmemo.rest;

public record LanguageModificationRequest(
        String langCode,
        String languageName,
        String languageImage
) {}
