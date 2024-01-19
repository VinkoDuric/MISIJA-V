package com.misijav.flipmemo.rest.quiz;

import java.util.List;

public record QuizQuestion(
        String question,
        List<String> answerChoices
) {}
