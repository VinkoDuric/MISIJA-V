package com.misijav.flipmemo.service.impl;

import com.misijav.flipmemo.dao.CurrentStateRepository;
import com.misijav.flipmemo.dao.DictionaryRepository;
import com.misijav.flipmemo.dao.PotRepository;
import com.misijav.flipmemo.exception.ResourceNotFoundException;
import com.misijav.flipmemo.model.CurrentState;
import com.misijav.flipmemo.model.LearningMode;
import com.misijav.flipmemo.model.Pot;
import com.misijav.flipmemo.rest.quiz.QuizAnswer;
import com.misijav.flipmemo.rest.quiz.QuizQuestion;
import com.misijav.flipmemo.service.CurrentStateService;
import com.misijav.flipmemo.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceJpa implements QuizService {
    private final DictionaryRepository dictionaryRepository;
    private final CurrentStateRepository currentStateRepository;
    private final PotRepository potRepository;
    private final CurrentStateService currentStateService;

    @Autowired
    public QuizServiceJpa(DictionaryRepository dictionaryRepository,
                          CurrentStateRepository currentStateRepository,
                          PotRepository potRepository,
                          CurrentStateService currentStateService) {
        this.dictionaryRepository = dictionaryRepository;
        this.currentStateRepository = currentStateRepository;
        this.potRepository = potRepository;
        this.currentStateService = currentStateService;
    }

    @Override
    public QuizQuestion getQuizQuestion(Long dictId, Long userId) {
        CurrentState currentState = currentStateRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Current state not found for user with id: " + userId));

        List<Pot> pots = potRepository.findByUserId(userId);
        // If pots are not yet initialized, initialize them
        if (pots == null) {
            currentStateService.initializePotsForUser(userId, dictId);
        }

        // create Answer
        QuizQuestion quizQuestion = null;

        if (currentState.getLearningMode().equals(LearningMode.ORIGINAL_TRANSLATED)) {
            // TODO return original word (eng) with multiple translated words (hrv)
        } else if (currentState.getLearningMode().equals(LearningMode.TRANSLATED_ORIGINAL)) {
            // TODO return translated word (hrv) with multiple original words (eng)
        } else if (currentState.getLearningMode().equals(LearningMode.AUDIO_RESPONSE)) {
            // TODO return original word audio (user should write answer)
        } else if (currentState.getLearningMode().equals(LearningMode.ORIGINAL_AUDIO)) {
            // TODO return original word (eng) (user should record audio (with original word (eng)))
        }
        return quizQuestion;
    }

    @Override
    public boolean checkAnswer(Long userId, Long wordId, String type, QuizAnswer answer) {
        CurrentState currentState = currentStateRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Current state not found for user with id: " + userId));

        // TODO
        // ... if true move word to next pot
        return false;
    }
}
