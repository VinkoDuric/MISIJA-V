package com.misijav.flipmemo.service.impl;

import com.misijav.flipmemo.dao.CurrentStateRepository;
import com.misijav.flipmemo.dao.PotRepository;
import com.misijav.flipmemo.dao.WordRepository;
import com.misijav.flipmemo.exception.ResourceNotFoundException;
import com.misijav.flipmemo.model.CurrentState;
import com.misijav.flipmemo.model.LearningMode;
import com.misijav.flipmemo.model.Pot;
import com.misijav.flipmemo.model.Word;
import com.misijav.flipmemo.rest.quiz.GetQuizQuestionRequest;
import com.misijav.flipmemo.rest.quiz.CheckQuizAnswerRequest;
import com.misijav.flipmemo.rest.quiz.GetQuizQuestionResponse;
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
    private final CurrentStateRepository currentStateRepository;
    private final PotRepository potRepository;
    private final CurrentStateService currentStateService;
    private final WordRepository wordRepository;

    @Autowired
    public QuizServiceJpa(CurrentStateRepository currentStateRepository,
                          PotRepository potRepository,
                          CurrentStateService currentStateService,
                          WordRepository wordRepository) {
        this.currentStateRepository = currentStateRepository;
        this.potRepository = potRepository;
        this.currentStateService = currentStateService;
        this.wordRepository = wordRepository;
    }

    @Override
    public GetQuizQuestionResponse getQuizQuestion(Long dictId, Long userId, GetQuizQuestionRequest request) {
        CurrentState currentState = currentStateRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Current state not found for user with id: " + userId));

        List<Pot> userPots = potRepository.findByUserId(userId);
        // If pots are not yet initialized, initialize them
        if (userPots == null) {
            currentStateService.initializePotsForUser(userId, dictId);
            userPots = potRepository.findByUserId(userId);
        }
        // TODO if user chooses different language then what?
        if (!Objects.equals(currentState.getDictionary().getDictionaryId(), dictId)) {
            // TODO ...
        }

        LearningMode learningMode = request.learningMode();

        // Determine which pot to use
        Pot selectedPot = determineRelevantPot(userPots);

        if (learningMode.equals(LearningMode.ORIGINAL_TRANSLATED)) {
            // return original word (eng) with multiple translated words (hrv)
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
            return new GetQuizQuestionResponse(question, answerChoices);

        } else if (learningMode.equals(LearningMode.TRANSLATED_ORIGINAL)) {
            // return translated word (hrv) with multiple original words (eng)
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
            return new GetQuizQuestionResponse(question, answerChoices);
        } else if (learningMode.equals(LearningMode.AUDIO_RESPONSE)) {
            // return original word audio (user should write answer)
            return null;
        } else if (learningMode.equals(LearningMode.ORIGINAL_AUDIO)) {
            // return original word (eng) (user should record audio (with original word (eng)))
            Word originalWord = selectWordForQuiz(selectedPot, userPots, currentState);
            String question = originalWord.getOriginalWord();
            return new GetQuizQuestionResponse(question, null);
        }
        return null;
    }

    private Pot determineRelevantPot(List<Pot> userPots) {
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
            potWords = determineRelevantPot(userPots).getWords();
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
    public int checkAnswer(Long userId, Long wordId, CheckQuizAnswerRequest request) {
        CurrentState currentState = currentStateRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Current state not found for user with id: " + userId));

        Word word = wordRepository.findWordByWordId(wordId)
                .orElseThrow(() -> new ResourceNotFoundException("Word not found with id: " + wordId));

        List<Pot> userPots = potRepository.findByUserId(userId);
        if (userPots == null) {
            throw new ResourceNotFoundException("Pots not found for user with id: " + userId);
        }

        LearningMode learningMode = request.learningMode();

        if (learningMode.equals(LearningMode.ORIGINAL_TRANSLATED)) {
            // check if original word (eng) matches with translated word (hrv)
            if (word.getTranslatedWord().equals(request.answer())) {  // if word translated == answer
                moveWordToNextPot(userId, word, currentState);  // Move word to next pot
                return 10;
            } else {
                moveWordToFirstPot(userId, word);  // Move word to first pot
                return 0;
            }
        } else if (learningMode.equals(LearningMode.TRANSLATED_ORIGINAL)) {
            // check if translated word (hrv) matches with original word (eng)
            if (word.getOriginalWord().equals(request.answer())) {  // if original word == answer
                moveWordToNextPot(userId, word, currentState);
                return 10;
            } else {
                moveWordToFirstPot(userId, word);
                return 0;
            }
        } else if (learningMode.equals(LearningMode.AUDIO_RESPONSE)) {
            // check if original word is equal to user written answer
            if (word.getOriginalWord().equals(request.answer())) {
                moveWordToNextPot(userId, word, currentState);
                return 10;
            } else {
                moveWordToFirstPot(userId, word);
                return 0;
            }
        } else if (learningMode.equals(LearningMode.ORIGINAL_AUDIO)) {
            // check if audio equals original word (eng)
            // return random value from 0 to 10
            Random random = new Random();
            int evaluation = random.nextInt(11);
            if (evaluation >= 5) {
                moveWordToNextPot(userId, word, currentState);
            } else {
                moveWordToFirstPot(userId, word);
            }
            return evaluation;
        }
        return 0;
    }

    private void moveWordToNextPot(Long userId, Word word, CurrentState currentState) {
        Pot currentPot = potRepository.findByUserIdAndWordId(userId, word.getWordId())
                .orElseThrow(() -> new ResourceNotFoundException("No pot found for user "
                        + userId + ", and word " + word.getWordId()));

        currentPot.removeWord(word);
        potRepository.save(currentPot);

        if (currentState.getNumberOfPots() > currentPot.getPotNumber()) {  // if it is not last pot
            Pot nextPot = potRepository.findByUserIdAndPotNumber(userId, currentPot.getPotNumber() + 1)
                    .orElseThrow(() -> new ResourceNotFoundException("No pot found for user "
                            + userId + ", and pot number " + currentPot.getPotNumber()));

            nextPot.addWord(word);
            potRepository.save(nextPot);
        }  // else : word is removed from last pot
    }

    private void moveWordToFirstPot(Long userId, Word word) {
        Pot currentPot = potRepository.findByUserIdAndWordId(userId, word.getWordId())
                .orElseThrow(() -> new ResourceNotFoundException("No pot found for user "
                        + userId + ", and word " + word.getWordId()));

        currentPot.removeWord(word);  // Remove word from current pot
        potRepository.save(currentPot);

        Pot firstPot = potRepository.findByUserIdAndPotNumber(userId, 1)
                .orElseThrow(() -> new ResourceNotFoundException("No pot found for user "
                        + userId + ", and pot number 1"));

        firstPot.addWord(word);  // Add word to first pot
        potRepository.save(firstPot);
    }
}
