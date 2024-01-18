package com.misijav.flipmemo.service;

import com.misijav.flipmemo.model.Word;
import com.misijav.flipmemo.rest.word.WordModificationRequest;
import com.misijav.flipmemo.rest.word.WordRequest;

import java.util.Optional;

public interface WordService {

    /**
     * Retrieves a word by its ID.
     * @param wordId The ID of the word to be retrieved.
     * @return An Optional containing the word if found, or an empty Optional otherwise.
     */
    Optional<Word> getWordById(Long wordId);

    /**
     * Adds a new word to the repository.
     * @param request The WordRequest object containing wordName and wordDescription.
     * @return A newly created word
     */
    Word addWord(WordRequest request);

    /**
     * Updates an existing word.
     * @param id The ID of the word to update.
     * @param request The updated details of the word.
     */
    void updateWord(Long id, WordModificationRequest request);

    /**
     * Deletes a word by its ID.
     * @param id The ID of the word to be deleted.
     */
    void deleteWord(Long id);
}
