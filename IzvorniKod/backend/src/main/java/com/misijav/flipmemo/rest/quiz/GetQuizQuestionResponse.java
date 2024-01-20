package com.misijav.flipmemo.rest.quiz;

import java.util.List;

public record GetQuizQuestionResponse(
        String question,
        List<String> answerChoices
) {}
