package com.misijav.flipmemo.rest.dict;

import com.misijav.flipmemo.model.Language;

public record DictionaryModificationRequest(
        Long id,
        String dictName,
        String dictImage,
        Language dictLang
) {
}
