package com.misijav.flipmemo.rest.quiz;

import java.util.List;

public record QuizQuestion(
        String word,
        List<String> multipleAnswerWords
) {}
