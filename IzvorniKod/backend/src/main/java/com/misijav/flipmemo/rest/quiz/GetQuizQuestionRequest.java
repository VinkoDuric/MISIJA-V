package com.misijav.flipmemo.rest.quiz;

import com.misijav.flipmemo.model.LearningMode;

public record GetQuizQuestionRequest(
        LearningMode learningMode
) {}
