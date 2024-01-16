package com.misijav.flipmemo.rest;

import com.misijav.flipmemo.model.Language;

import java.util.ArrayList;

public record DictionaryModificationRequest(
        Long id,
        String dictName,
        String dictImage,
        Language dictLang
) {
}
