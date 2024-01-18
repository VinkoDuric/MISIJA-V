package com.misijav.flipmemo.dto;

import com.misijav.flipmemo.model.Word;

import java.util.function.Function;

public class WordDTOMapper implements Function<Word, WordDTO> {
    @Override
    public WordDTO apply(Word word) {
        return new WordDTO(
                word.getWordId(),
                word.getWordLanguageCode(),
                word.getOriginalWord(),
                word.getTranslatedWord(),
                word.getWordDescription()
        );
    }
}
