package com.misijav.flipmemo.service;

import com.misijav.flipmemo.rest.quiz.CheckQuizAnswerRequest;
import com.misijav.flipmemo.rest.quiz.GetQuizQuestionRequest;
import com.misijav.flipmemo.rest.quiz.GetQuizQuestionResponse;

public interface QuizService {

    /**
     * Generates a quiz question for a given user based on the specified dictionary.
     *
     * @param dictId The ID of the dictionary to generate questions from.
     * @param userId The ID of the user taking the quiz.
     * @param request Additional request parameters, including Learning Mode
     * @return A response containing the quiz question.
     */
    GetQuizQuestionResponse getQuizQuestion(Long dictId, Long userId, GetQuizQuestionRequest request);

    /**
     * Checks the answer submitted by a user for a quiz question.
     *
     * @param userId The ID of the user submitting the answer.
     * @param wordId The ID of the word that the quiz question was based on.
     * @param answer The user's answer to the quiz question.
     * @return evaluation of the answer.
     */
    int checkAnswer(Long userId, Long wordId, CheckQuizAnswerRequest answer);
}
