package com.misijav.flipmemo.service;

import com.misijav.flipmemo.model.Language;
import com.misijav.flipmemo.rest.LanguageModificationRequest;

import java.util.List;
import java.util.Optional;

public interface LanguageService {
    /**
     * Add new language.
     * @param language new language
     * @return added language
     */
    Language addLanguage(Language language);

    /**
     * Find a language by id.
     * @param id language's id
     * @return language if it's found
     */
    Optional<Language> findByLanguageId(Long id);

    /**
     * Delete language by id.
     * @param id language's id
     */
    void deleteLanguage(Long id);

    /**
     * Update a language by id.
     * @param id language's id
     * @param request language's updated data
     * @return updated language
     */
    Language updateLanguage(Long id, LanguageModificationRequest request);

    /**
     * Get all languages.
     * @return list of all languages
     */
    List<Language> getAllLanguages();
}
