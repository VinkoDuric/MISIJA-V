package com.misijav.flipmemo.service;

/**
 * This service is responsible for managing the learning states of users,
 * particularly their progress through different 'pots' or levels of word familiarity.
 */
public interface CurrentStateService {

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
