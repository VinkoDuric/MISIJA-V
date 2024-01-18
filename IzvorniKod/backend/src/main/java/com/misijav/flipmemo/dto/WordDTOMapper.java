package com.misijav.flipmemo.dto;

import com.misijav.flipmemo.model.Word;

import java.util.function.Function;
import java.util.stream.Collectors;

public class WordDTOMapper implements Function<Word, WordDTO> {
    @Override
    public WordDTO apply(Word word) {
        String synonyms = String.join(", ", word.getWordSynonyms());
        String dictionaries = word.getDictionaries().stream().map(String::valueOf)
                .collect(Collectors.joining(", "));

        return new WordDTO(
                word.getWordId(),
                word.getWordLanguageCode(),
                word.getOriginalWord(),
                word.getTranslatedWord(),
                word.getWordDescription(),
                synonyms,
                dictionaries
        );
    }
}
