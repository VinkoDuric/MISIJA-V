package com.misijav.flipmemo.service;

import com.misijav.flipmemo.model.Language;
import com.misijav.flipmemo.rest.LanguageModificationRequest;

import java.util.List;
import java.util.Optional;

public interface LanguageService {
    /**
     * Add new language.
     * @param language new language request
     */
    void addLanguage(LanguageModificationRequest language);

    /**
     * Find a language by code.
     * @param langCode language's code
     * @return language if it's found
     */
    Optional<Language> findByLangCode(String langCode);

    /**
     * Delete language by code.
     * @param langCode language's code
     */
    void deleteLanguage(String langCode);

    /**
     * Update a language by code.
     * @param langCode language's code
     * @param request language's updated data
     * @return updated language
     */
    Language updateLanguage(String langCode, LanguageModificationRequest request);

    /**
     * Get all languages.
     * @return list of all languages
     */
    List<Language> getAllLanguages();
}
