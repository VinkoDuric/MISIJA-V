package com.misijav.flipmemo.service.impl;

import com.misijav.flipmemo.dao.CurrentStateRepository;
import com.misijav.flipmemo.dao.DictionaryRepository;
import com.misijav.flipmemo.dao.PotRepository;
import com.misijav.flipmemo.exception.ResourceNotFoundException;
import com.misijav.flipmemo.model.CurrentState;
import com.misijav.flipmemo.model.LearningMode;
import com.misijav.flipmemo.model.Pot;
import com.misijav.flipmemo.model.Word;
import com.misijav.flipmemo.rest.quiz.QuizAnswer;
import com.misijav.flipmemo.rest.quiz.QuizQuestion;
import com.misijav.flipmemo.service.CurrentStateService;
import com.misijav.flipmemo.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

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

        List<Pot> userPots = potRepository.findByUserId(userId);
        // If pots are not yet initialized, initialize them
        if (userPots == null) {
            currentStateService.initializePotsForUser(userId, dictId);
            userPots = potRepository.findByUserId(userId);
        }

        // Determine which pot to use
        Pot selectedPot = determineRelevantPot(userPots, currentState);

        if (currentState.getLearningMode().equals(LearningMode.ORIGINAL_TRANSLATED)) {
            // TODO return original word (eng) with multiple translated words (hrv)
            Word originalWord = selectWordForQuiz(selectedPot, userPots, currentState);
            String question = originalWord.getOriginalWord();

            List<String> answerChoices = new ArrayList<>();
            for (int numOfAnswers = 1; numOfAnswers <= 4; numOfAnswers++) {
                Word answer = selectWordForQuiz(selectedPot, userPots, currentState);
                // check if word is "question unique"
                if (!Objects.equals(answer.getWordId(), originalWord.getWordId())) {  // if answer != question
                    if (!answerChoices.contains(answer.getTranslatedWord())) {  // if answer != answer
                        answerChoices.add(answer.getTranslatedWord());  // Add answer to answer list
                    } else {
                        numOfAnswers--;
                    }
                } else {
                    numOfAnswers--;
                }
            }
            return new QuizQuestion(question, answerChoices);

        } else if (currentState.getLearningMode().equals(LearningMode.TRANSLATED_ORIGINAL)) {
            // TODO return translated word (hrv) with multiple original words (eng)
            Word translatedWord = selectWordForQuiz(selectedPot, userPots, currentState);
            String question = translatedWord.getTranslatedWord();

            List<String> answerChoices = new ArrayList<>();
            for (int numOfAnswers = 1; numOfAnswers <= 4; numOfAnswers++) {
                Word answer = selectWordForQuiz(selectedPot, userPots, currentState);
                // Check if word is "question unique"
                if (!Objects.equals(answer.getWordId(), translatedWord.getWordId())) {
                    if (!answerChoices.contains(answer.getOriginalWord())) {
                        answerChoices.add(answer.getOriginalWord());  // add answer to answer list
                    } else {
                        numOfAnswers--;
                    }
                } else {
                    numOfAnswers--;
                }
            }
        } else if (currentState.getLearningMode().equals(LearningMode.AUDIO_RESPONSE)) {
            // TODO return original word audio (user should write answer)
        } else if (currentState.getLearningMode().equals(LearningMode.ORIGINAL_AUDIO)) {
            // TODO return original word (eng) (user should record audio (with original word (eng)))
        }
        return null;
    }

    private Pot determineRelevantPot(List<Pot> userPots, CurrentState currentState) {
        // Check if first pot is not empty and send "it", if not, send other pot in line
        for (Pot pot : userPots) {
            if (!pot.getWords().isEmpty()) {
                return pot;
            }
        }
        return userPots.get(0);
    }

    private Word selectWordForQuiz(Pot pot, List<Pot> userPots, CurrentState currentState) {
        List<Word> potWords = pot.getWords();

        // Check if the pot is empty
        if (potWords.isEmpty()) {
            // get another pot
            potWords = determineRelevantPot(userPots, currentState).getWords();
        }

        // If pot has small amount of elements (<5), combine with next pot
        if (potWords.size() < 5) {
            int potNumber = pot.getPotNumber();
            Pot nextPot = null;
            for (Pot potElem : userPots) {
                if (potElem.getPotNumber() == potNumber + 1) {
                    nextPot = potElem;
                    break;
                }
            }
            if (nextPot != null) { // If it is last pot
                List<Word> nextPotWords = nextPot.getWords();
                potWords.addAll(nextPotWords);
            }
        }

        // Select random word from the pot
        Random random = new Random();
        return potWords.get(random.nextInt(potWords.size()));
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
