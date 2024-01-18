package com.misijav.flipmemo.dto;

import java.util.List;

public record WordDTO(
        Long id,
        String wordLanguageCode,
        String originalWord,
        String translatedWord,
        String wordDescription,
        String wordSynonyms,
        String dictionaryIds
) {}
