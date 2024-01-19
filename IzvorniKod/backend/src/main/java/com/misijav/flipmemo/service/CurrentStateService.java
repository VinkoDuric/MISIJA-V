package com.misijav.flipmemo.service;

import com.misijav.flipmemo.model.CurrentState;
import com.misijav.flipmemo.model.LearningMode;

import java.util.Optional;

/**
 * This service is responsible for managing the learning states of users,
 * particularly their progress through different 'pots' or levels of word familiarity.
 */
public interface CurrentStateService {

    /**
     * Finds the current learning state of a specific user.
     *
     * @param userId The unique identifier of the user.
     * @return An Optional containing the user's current learning state if found.
     */
    Optional<CurrentState> findByUserId(Long userId);

    /**
     * Updates the learning mode for a specific user.
     * This method alters the user's current state to reflect the new learning mode.
     *
     * @param userId The unique identifier of the user.
     * @param learningMode The learning mode to be set for the user.
     */
    void updateLearningMode(Long userId, LearningMode learningMode);

    /**
     * Initializes pots for a given user with a selected dictionary.
     * This method will create a starting learning state for each word in the dictionary,
     * typically placing all words in the first pot, signifying the start of the learning process.
     *
     * @param userId The user for whom learning states need to be initialized.
     * @param dictionaryId The dictionary from which words will be taken to create learning states.
     */
    void initializePotsForUser(Long userId, Long dictionaryId);
}
