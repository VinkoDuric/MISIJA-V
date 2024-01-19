package com.misijav.flipmemo.rest.word;

import java.util.List;

public record WordRequest(
    String wordLanguageCode,
    String originalWord,
    String translatedWord,
    List<String> wordDescription,
    List<String> wordSynonyms,
    List<Long> dictionaryIds
) {}
