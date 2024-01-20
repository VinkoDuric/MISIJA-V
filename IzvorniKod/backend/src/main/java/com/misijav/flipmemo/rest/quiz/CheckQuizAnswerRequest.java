package com.misijav.flipmemo.rest.quiz;

import com.misijav.flipmemo.model.LearningMode;

public record CheckQuizAnswerRequest(
        LearningMode learningMode,
        Long dictId,
        String answer
) {}
