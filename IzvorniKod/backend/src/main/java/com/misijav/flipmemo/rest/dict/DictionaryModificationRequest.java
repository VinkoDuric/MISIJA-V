package com.misijav.flipmemo.rest.dict;

public record DictionaryModificationRequest(
        Long id,
        String dictName,
        String dictImage,
        String dictLang
) {
}
