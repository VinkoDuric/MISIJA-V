package com.misijav.flipmemo.service.impl;

import com.misijav.flipmemo.dao.DictionaryRepository;
import com.misijav.flipmemo.dao.LanguageRepository;
import com.misijav.flipmemo.dao.WordRepository;
import com.misijav.flipmemo.exception.ResourceConflictException;
import com.misijav.flipmemo.exception.ResourceNotFoundException;
import com.misijav.flipmemo.model.Dictionary;
import com.misijav.flipmemo.model.Language;
import com.misijav.flipmemo.model.Word;
import com.misijav.flipmemo.rest.DictionaryModificationRequest;
import com.misijav.flipmemo.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class DictionaryServiceJpa implements DictionaryService {
    private final DictionaryRepository dictionaryRepository;
    private final WordRepository wordRepository;
    private final LanguageRepository languageRepository;

    @Autowired
    public DictionaryServiceJpa(DictionaryRepository dictionaryRepository, WordRepository wordRepository, LanguageRepository languageRepository) {
        this.dictionaryRepository = dictionaryRepository;
        this.wordRepository = wordRepository;
        this.languageRepository = languageRepository;
    }

    @Override
    public Dictionary addDictionary(Dictionary dictionary) {
        return dictionaryRepository.save(dictionary);
    }

    @Override
    public Optional<Dictionary> findByDictId(Long id) {
        return dictionaryRepository.findByDictId(id);
    }

    @Override
    public void deleteDictionary(Long id) {
        Dictionary dictionary = dictionaryRepository.findByDictId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dictionary not found with id " + id));

        dictionaryRepository.delete(dictionary);
    }

    @Override
    public Dictionary updateDictionary(Long id, DictionaryModificationRequest request) {
        Dictionary dictionary = dictionaryRepository.findByDictId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dictionary not found with id " + id));

        if(request.dictName() != null && request.dictName().isEmpty()) {
            if(dictionaryRepository.findByDictName(request.dictName()).isPresent() &&
                   !(dictionary.getDictName().equals(request.dictName()))) {
                throw new ResourceConflictException("Dictionary name is already in use.");
            }
            dictionary.setDictLang(request.dictLang());
        }

        if(request.dictImage() != null && !request.dictImage().isEmpty()) {
            dictionary.setDictImage(request.dictImage());
        }

        if(request.dictLang() != null){
            dictionary.setDictLang((request.dictLang()));
        }
        return dictionaryRepository.save(dictionary);
    }

    @Override
    public List<Dictionary> getAllDicts() {
        Iterable<Dictionary> dictionaryIterable = dictionaryRepository.findAll();
        List<Dictionary> dictionaryList = new ArrayList<>();

        dictionaryIterable.forEach(dictionaryList::add);

        return dictionaryList;
    }

    @Override
    public void addWordToDict(Long id, Word word) {
        Optional<Dictionary> optionalDictionary = dictionaryRepository.findByDictId(id);

        if (optionalDictionary.isPresent()) {
            Dictionary dictionary = optionalDictionary.get();
            dictionary.addWord(word);
            dictionaryRepository.save(dictionary);
        }
    }

    @Override
    public void deleteWordFromDict(Long id, Long wordId) {
        Optional<Dictionary> optionalDictionary = dictionaryRepository.findByDictId(id);
        Optional<Word> optionalWord = wordRepository.findWordById(wordId);

        if (optionalDictionary.isPresent() && optionalWord.isPresent()) {
            Dictionary dictionary = optionalDictionary.get();
            Word word = optionalWord.get();

            if(dictionary.getDictWords().contains(word)) {
                dictionary.getDictWords().remove(word);

                dictionaryRepository.save(dictionary);
            }
        }
    }

    @Override
    public List<Dictionary> getDictsByLangCode(String langCode) {
        Optional<Language> optionalLanguage = languageRepository.findByLangCode(langCode);

        if (optionalLanguage.isPresent()) {
            Language language = optionalLanguage.get();
            return dictionaryRepository.findByLanguage(language);
        } else {
            return Collections.emptyList();
        }
    }
}
