package com.misijav.flipmemo.rest.word;

import java.util.List;

public record WordRequest(
    String wordName,
    String wordDescription,
    List<Long> dictionaryIds
) {}
