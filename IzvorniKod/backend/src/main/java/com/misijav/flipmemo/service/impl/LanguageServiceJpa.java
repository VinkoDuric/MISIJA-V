package com.misijav.flipmemo.service.impl;

import com.misijav.flipmemo.dao.LanguageRepository;
import com.misijav.flipmemo.exception.ResourceConflictException;
import com.misijav.flipmemo.exception.ResourceNotFoundException;
import com.misijav.flipmemo.model.Language;
import com.misijav.flipmemo.rest.LanguageModificationRequest;
import com.misijav.flipmemo.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LanguageServiceJpa implements LanguageService {

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageServiceJpa(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public Language addLanguage(Language language) {
        return languageRepository.save(language);
    }

    @Override
    public Optional<Language> findByLanguageId(Long id) {
        return languageRepository.findByLanguageId(id);
    }

    @Override
    public void deleteLanguage(Long id) {
        Language language = languageRepository.findByLanguageId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Language not found with id " + id));

        languageRepository.delete(language);
    }

    @Override
    public Language updateLanguage(Long id, LanguageModificationRequest request) {
        Language language = languageRepository.findByLanguageId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Language not found with id " + id));

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
