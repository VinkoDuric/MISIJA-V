package com.misijav.flipmemo.service.impl;

import com.misijav.flipmemo.dao.DictionaryRepository;
import com.misijav.flipmemo.dao.LanguageRepository;
import com.misijav.flipmemo.exception.ResourceConflictException;
import com.misijav.flipmemo.exception.ResourceNotFoundException;
import com.misijav.flipmemo.model.Dictionary;
import com.misijav.flipmemo.model.Language;
import com.misijav.flipmemo.rest.LanguageModificationRequest;
import com.misijav.flipmemo.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LanguageServiceJpa implements LanguageService {

    private final LanguageRepository languageRepository;
    private final DictionaryRepository dictionaryRepository;

    @Autowired
    public LanguageServiceJpa(LanguageRepository languageRepository, DictionaryRepository dictionaryRepository) {
        this.languageRepository = languageRepository;
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    public void addLanguage(LanguageModificationRequest language) {
        Optional<Language> existingLang = languageRepository.findByLangCode(language.langCode());
        if (existingLang.isPresent()) {
            throw new ResourceConflictException("Language with code <" + language.langCode() +"> already exists.");
        }
        Language lang = new Language(language.langCode(), language.languageName(), language.languageImage());
        languageRepository.save(lang);
    }

    @Override
    public Optional<Language> findByLangCode(String langCode) {
        return languageRepository.findByLangCode(langCode);
    }

    @Override
    public void deleteLanguage(String langCode) {
        Language language = languageRepository.findByLangCode(langCode)
                .orElseThrow(() -> new ResourceNotFoundException("Language not found with code " + langCode));

        List<Dictionary> dictionaries = dictionaryRepository.findByDictLang(language);
        dictionaries.forEach(dictionaryRepository::delete);
        languageRepository.delete(language);
    }

    @Override
    public Language updateLanguage(String langCode, LanguageModificationRequest request) {
        Language language = languageRepository.findByLangCode(langCode)
                .orElseThrow(() -> new ResourceNotFoundException("Language not found with code " + langCode));

        if(request.languageName() != null && !request.languageName().isEmpty()) {
            if (languageRepository.findByLanguageName(request.languageName()).isPresent() &&
                    !(language.getLanguageName().equals(request.languageName()))) {
                throw new ResourceConflictException("Language name is already in use.");
            }

            language.setLanguageName(request.languageName());
        }

        if(request.languageImage() != null && !request.languageImage().isEmpty()) {
            language.setLanguageImage(request.languageImage());
        }
        return languageRepository.save(language);
    }

    @Override
    public List<Language> getAllLanguages() {
        Iterable<Language> languagesIterable = languageRepository.findAll();
        List<Language> languagesList = new ArrayList<>();

        languagesIterable.forEach(languagesList::add);

        return languagesList;
    }
}
