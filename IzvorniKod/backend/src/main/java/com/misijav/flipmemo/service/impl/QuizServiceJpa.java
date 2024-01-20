package com.misijav.flipmemo.service.impl;

import com.misijav.flipmemo.dao.PotRepository;
import com.misijav.flipmemo.dao.WordRepository;
import com.misijav.flipmemo.exception.QuizCompletionException;
import com.misijav.flipmemo.exception.ResourceNotFoundException;
import com.misijav.flipmemo.model.LearningMode;
import com.misijav.flipmemo.model.Pot;
import com.misijav.flipmemo.model.Word;
import com.misijav.flipmemo.rest.quiz.CheckQuizAnswerRequest;
import com.misijav.flipmemo.rest.quiz.GetQuizQuestionResponse;
import com.misijav.flipmemo.service.CurrentStateService;
import com.misijav.flipmemo.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class QuizServiceJpa implements QuizService {
    private static final Logger logger = LoggerFactory.getLogger(QuizServiceJpa.class);
    private final PotRepository potRepository;
    private final CurrentStateService currentStateService;
    private final WordRepository wordRepository;

    @Autowired
    public QuizServiceJpa(PotRepository potRepository,
                          CurrentStateService currentStateService,
                          WordRepository wordRepository) {
        this.potRepository = potRepository;
        this.currentStateService = currentStateService;
        this.wordRepository = wordRepository;
    }

    @Override
    public GetQuizQuestionResponse getQuizQuestion(Long dictId, Long userId, LearningMode learningMode) {
        // Find list of pots for this user and this dictionary
        List<Pot> userPots = potRepository.findByUserIdAndDictionaryId(userId, dictId);
        // If pots are not yet initialized, initialize them
        if (userPots == null || userPots.isEmpty()) {
            currentStateService.initializePotsForUser(userId, dictId);
            userPots = potRepository.findByUserIdAndDictionaryId(userId, dictId);
        }

        logger.info("userPots = {}", userPots);
        logger.info("First pot = {}", userPots.get(0).getWords());

        if (learningMode.equals(LearningMode.ORIGINAL_TRANSLATED)) {
            // return original word (eng) with multiple translated words (hrv)
            Word originalWord = selectWordForQuiz(userPots);
            String question = originalWord.getOriginalWord();
            logger.info("User id {} dict id {} word id {}.", userId, dictId, originalWord.getWordId());

            List<String> answerChoices = new ArrayList<>();
            answerChoices.add(originalWord.getTranslatedWord());

            for (int numOfAnswers = 1; numOfAnswers < 4; numOfAnswers++) {
                Word answer = selectWordForQuiz(userPots);
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
            Collections.shuffle(answerChoices);
            return new GetQuizQuestionResponse(question, answerChoices);

        } else if (learningMode.equals(LearningMode.TRANSLATED_ORIGINAL)) {
            // return translated word (hrv) with multiple original words (eng)
            Word translatedWord = selectWordForQuiz(userPots);
            String question = translatedWord.getTranslatedWord();

            List<String> answerChoices = new ArrayList<>();
            answerChoices.add(translatedWord.getOriginalWord());

            for (int numOfAnswers = 1; numOfAnswers < 4; numOfAnswers++) {
                Word answer = selectWordForQuiz(userPots);
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
            Collections.shuffle(answerChoices);
            return new GetQuizQuestionResponse(question, answerChoices);
        } else if (learningMode.equals(LearningMode.AUDIO_RESPONSE)) {
            // return original word audio (user should write answer)
            return null;
        } else if (learningMode.equals(LearningMode.ORIGINAL_AUDIO)) {
            // return original word (eng) (user should record audio (with original word (eng)))
            Word originalWord = selectWordForQuiz(userPots);
            String question = originalWord.getOriginalWord();
            return new GetQuizQuestionResponse(question, null);
        }
        return null;
    }

    private void updateWordTimestamp(Word word, Long dictId, Long userId) {
        logger.info("User inside updateWordTimestamp method");
        for (Pot pot : word.getPots()) {
            if (pot.getUser().getId().equals(userId) && pot.getDictionary().getId().equals(dictId)) {
                // Update the last reviewed timestamp for the selected word
                Map<Pot, LocalDateTime> lastReviewedTimes = word.getLastReviewedTimes();
                lastReviewedTimes.put(pot, LocalDateTime.now()); // Update the timestamp for the current pot
                word.setLastReviewedTimes(lastReviewedTimes);

                // Save the updated word
                Word updatedWord = wordRepository.save(word);
                logger.info("updatedWord = {}", updatedWord);
                return;
            }
        }
    }

    private Word selectWordForQuiz(List<Pot> userPots) {
        logger.info("User in selectWordForQuiz");
        logger.info("userPots = {}", userPots);
        logger.info("1 pot = {}", userPots.get(0).getWords());
        logger.info("2 pot = {}", userPots.get(1).getWords());
        logger.info("3 pot = {}", userPots.get(2).getWords());
        logger.info("4 pot = {}", userPots.get(3).getWords());
        logger.info("5 pot = {}", userPots.get(4).getWords());
        logger.info("6 pot = {}", userPots.get(5).getWords());

        // Sort pots by potNumber in descending order (Pot6,..,Pot1)
        userPots.sort((pot1, pot2) -> Integer.compare(pot2.getPotNumber(), pot1.getPotNumber()));

        // Fetch "all" available words
        List<Word> wordList = new ArrayList<>();
        for (Pot potElem : userPots) {
            List<Word> tmpWordList = potElem.getAvailableWords();
            if (tmpWordList != null && !tmpWordList.isEmpty()) {
                wordList.addAll(tmpWordList);
            }

            if (wordList.size() > 75) {
                break;
            }
        }

        logger.info("Fetched words = {}", wordList);

        if (wordList.size() < 5) {
            // Check if there is enough words inside pots
            int wordsLeft = 0;
            for (Pot potElem : userPots) {
                if (potElem.getWords() != null) {
                    wordsLeft += potElem.getWords().size();
                }
            }
            if (wordsLeft < 5) {
                // Quiz is done
                throw new QuizCompletionException("Hura! Naučio si sve riječi! :)");
            } else {
                // No more available words for today
                throw new QuizCompletionException("Naučio si sve dostupne riječi za danas :) vrati se ponovno sutra!");
            }
        }

        // Select random word from the wordList
        Random random = new Random();
        return wordList.get(random.nextInt(wordList.size()));
    }

    @Override
    @Transactional
    public int checkAnswer(Long userId, Long wordId, CheckQuizAnswerRequest request) {
        Word word = wordRepository.findWordByWordId(wordId)
                .orElseThrow(() -> new ResourceNotFoundException("Word not found with id: " + wordId));

        List<Pot> userPots = potRepository.findByUserIdAndDictionaryId(userId, request.dictId());
        if (userPots == null) {
            throw new ResourceNotFoundException("Pots not found for user with id: " + userId +
                    ", and dictionary with id: " + request.dictId());
        }

        LearningMode learningMode = request.learningMode();

        if (learningMode.equals(LearningMode.ORIGINAL_TRANSLATED)) {
            // check if original word (eng) matches with translated word (hrv)
            if (word.getTranslatedWord().equals(request.answer())) {  // if word translated == answer
                moveWordToNextPot(userId, request.dictId(), word);  // Move word to next pot
                updateWordTimestamp(word, request.dictId(), userId);  // update timestamp for word
                return 10;
            } else {
                moveWordToFirstPot(userId, request.dictId(), word);  // Move word to first pot
                updateWordTimestamp(word, request.dictId(), userId);  // update timestamp for word
                return 0;
            }
        } else if (learningMode.equals(LearningMode.TRANSLATED_ORIGINAL)) {
            // check if translated word (hrv) matches with original word (eng)
            if (word.getOriginalWord().equals(request.answer())) {  // if original word == answer
                moveWordToNextPot(userId, request.dictId(), word);
                updateWordTimestamp(word, request.dictId(), userId);  // update timestamp for word
                return 10;
            } else {
                moveWordToFirstPot(userId, request.dictId(), word);
                updateWordTimestamp(word, request.dictId(), userId);  // update timestamp for word
                return 0;
            }
        } else if (learningMode.equals(LearningMode.AUDIO_RESPONSE)) {
            // check if original word is equal to user written answer
            if (word.getOriginalWord().equals(request.answer())) {
                moveWordToNextPot(userId, request.dictId(), word);
                updateWordTimestamp(word, request.dictId(), userId);  // update timestamp for word
                return 10;
            } else {
                moveWordToFirstPot(userId, request.dictId(), word);
                updateWordTimestamp(word, request.dictId(), userId);  // update timestamp for word
                return 0;
            }
        } else if (learningMode.equals(LearningMode.ORIGINAL_AUDIO)) {
            // check if audio equals original word (eng)
            // return random value from 0 to 10
            Random random = new Random();
            int evaluation = random.nextInt(11);
            if (evaluation >= 5) {
                moveWordToNextPot(userId, request.dictId(), word);
            } else {
                moveWordToFirstPot(userId, request.dictId(), word);
            }
            updateWordTimestamp(word, request.dictId(), userId);  // update timestamp for word
            return evaluation;
        }
        return 0;
    }

    private void moveWordToNextPot(Long userId, Long dictId, Word word) {
        logger.info("User in moveWordToNextPot");
        int maxPotNum = 0;
        int currentPotNumber = 0;
        Pot currentPot = null;

        for (Pot pot : word.getPots()) {
            if (pot.getUser().getId().equals(userId) && pot.getDictionary().getId().equals(dictId)) {
                maxPotNum = pot.getMaxPotNumber();
                currentPotNumber = pot.getPotNumber();
                currentPot = pot;
                break;
            }
        }

        if (currentPot != null) {
            currentPot.removeWord(word);
            potRepository.save(currentPot);
            word.getPots().remove(currentPot); // Remove current pot from word's list of pots
        }

        if (maxPotNum > currentPotNumber) {
            int finalCurrentPotNumber = currentPotNumber;
            Pot nextPot = potRepository.findByUserIdPotNumberAndDictId(userId, currentPotNumber + 1, dictId)
                    .orElseThrow(() -> new ResourceNotFoundException("No pot found for user "
                            + userId + ", and pot number " + finalCurrentPotNumber));

            nextPot.addWord(word);
            word.getPots().add(nextPot); // Add next pot to the word's list of pots
            potRepository.save(nextPot);
            wordRepository.save(word);
        }  // else : word is removed from last pot
    }

    private void moveWordToFirstPot(Long userId, Long dictId, Word word) {
        logger.info("User in moveWordToFirstPot");

        for (Pot pot : new ArrayList<>(word.getPots())) {
            if (pot.getUser().getId().equals(userId) && pot.getDictionary().getId().equals(dictId)) {
                pot.removeWord(word);  // Remove word from current pot
                potRepository.save(pot);
                word.getPots().remove(pot);
            }
        }

        Pot firstPot = potRepository.findByUserIdPotNumberAndDictId(userId, 1, dictId)
                .orElseThrow(() -> new ResourceNotFoundException("No pot found for user "
                        + userId + ", and pot number 1"));

        firstPot.addWord(word);  // Add word to first pot
        word.getPots().add(firstPot); // Add this pot to the word's list of pots
        potRepository.save(firstPot);
        wordRepository.save(word);
    }
}
