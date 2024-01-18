package com.misijav.flipmemo.service;

import com.misijav.flipmemo.model.Dictionary;
import com.misijav.flipmemo.model.Word;
import com.misijav.flipmemo.rest.dict.DictionaryModificationRequest;
import com.misijav.flipmemo.rest.dict.DictionaryRequest;

import java.util.List;
import java.util.Optional;

public interface DictionaryService {

    /**
     * Add new dictionary.
     * @param request new dictionary request
     * @return added dictionary
     */
    Dictionary addDictionary(DictionaryRequest request);

    /**
     * Find a dictionary by id.
     * @param id dictionary id
     * @return dictionary if it's found
     */
    Optional<Dictionary> findByDictId(Long id);

    /**
     * Delete dictionary by id
     * @param id dictionary id
     */
    void deleteDictionary(Long id);

    /**
     * Update a dictionary by id.
     * @param id dictionary id
     * @param request dictionary updated data
     * @return updated dictionary
     */
    Dictionary updateDictionary(Long id, DictionaryModificationRequest request);

    /**
     * Get all dictionaries
     * @return list of all languages
     */
    List<Dictionary> getAllDicts();

    /**
     * Add word to a dictionary.
     * @param id dictionary id
     * @param word word to add
     */
    void addWordToDict(Long id, Word word);

    /**
     * Delete a word from dictionary
     * @param id dictionary id
     * @param wordId word id
     */
    void deleteWordFromDict(Long id, Long wordId);

    /**
     * Get dictionaries from language
     * @param langCode language code
     * @return list of dictionaries
     */
    List<Dictionary> getDictsByLangCode(String langCode);
}
