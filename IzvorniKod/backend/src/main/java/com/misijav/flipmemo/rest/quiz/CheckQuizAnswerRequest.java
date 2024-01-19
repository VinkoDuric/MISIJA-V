package com.misijav.flipmemo.rest.quiz;

import com.misijav.flipmemo.model.LearningMode;

public record CheckQuizAnswerRequest(
        LearningMode learningMode,
        String answer
) {}
