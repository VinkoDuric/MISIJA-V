package com.misijav.flipmemo.service;

import com.misijav.flipmemo.rest.quiz.QuizAnswer;
import com.misijav.flipmemo.rest.quiz.QuizQuestion;

public interface QuizService {
    QuizQuestion getQuizQuestion(Long dictId, Long id);
    boolean checkAnswer(Long userId, Long wordId, String type, QuizAnswer answer);
}
