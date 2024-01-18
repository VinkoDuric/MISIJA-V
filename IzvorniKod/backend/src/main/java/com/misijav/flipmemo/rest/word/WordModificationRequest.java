package com.misijav.flipmemo.rest.word;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record WordModificationRequest(
    @NotNull(message = "Word id must not be null")
    Long id,
    String wordLanguageCode,
    String originalWord,
    String translatedWord,
    String wordDescription,
    List<String> wordSynonyms,
    List<Long> dictionaryIds
) {}
