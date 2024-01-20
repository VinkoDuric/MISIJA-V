package com.misijav.flipmemo.dto;
import com.misijav.flipmemo.model.Dictionary;


import java.util.function.Function;

public class DictionaryDTOMapper implements Function<Dictionary, DictionaryDTO> {
    @Override
    public DictionaryDTO apply(Dictionary dictionary) {
        return new DictionaryDTO(
                dictionary.getDictionaryId(),
                dictionary.getDictName()
        );
    }
}