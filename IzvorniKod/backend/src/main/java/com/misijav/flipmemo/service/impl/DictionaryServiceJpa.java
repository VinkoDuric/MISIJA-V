package com.misijav.flipmemo.service.impl;

import com.misijav.flipmemo.dao.DictionaryRepository;
import com.misijav.flipmemo.dao.LanguageRepository;
import com.misijav.flipmemo.dao.WordRepository;
import com.misijav.flipmemo.exception.ResourceConflictException;
import com.misijav.flipmemo.exception.ResourceNotFoundException;
import com.misijav.flipmemo.model.Dictionary;
import com.misijav.flipmemo.model.Language;
import com.misijav.flipmemo.model.Word;
import com.misijav.flipmemo.rest.dict.DictionaryModificationRequest;
import com.misijav.flipmemo.rest.dict.DictionaryRequest;
import com.misijav.flipmemo.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
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
    public Long addDictionary(DictionaryRequest request) {
        Language dictLang = languageRepository.findByLangCode(request.langCode())
                .orElseThrow(() -> new ResourceNotFoundException("Language not found for this langCode: " + request.langCode()));

        Dictionary newDictionary = new Dictionary();
        newDictionary.setDictName(request.dictName());
        newDictionary.setDictLang(dictLang);
        Dictionary addedDict = dictionaryRepository.save(newDictionary);

        return addedDict.getDictionaryId();
    }

    @Override
    public Optional<Dictionary> findByDictId(Long id) {
        return dictionaryRepository.findDictById(id);
    }

    @Override
    public void deleteDictionary(Long id) {
        Dictionary dictionary = dictionaryRepository.findDictById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dictionary not found with id " + id));

        dictionaryRepository.delete(dictionary);
    }

    @Override
    public Dictionary updateDictionary(Long id, DictionaryModificationRequest request) {
        Dictionary dictionary = dictionaryRepository.findDictById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dictionary not found with id " + id));

        Language language = languageRepository.findByLanguageName(request.dictLang())
                .orElseThrow(() -> new ResourceNotFoundException("Language with name " + request.dictLang() + " not found."));

        if(request.dictName() != null && request.dictName().isEmpty()) {
            if(dictionaryRepository.findByDictName(request.dictName()).isPresent() &&
                   !(dictionary.getDictName().equals(request.dictName()))) {
                throw new ResourceConflictException("Dictionary name is already in use.");
            }
            dictionary.setDictLang(language);
        }

        if(request.dictImage() != null && !request.dictImage().isEmpty()) {
            dictionary.setDictImage(request.dictImage());
        }

        if(request.dictLang() != null){
            dictionary.setDictLang(language);
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
        Optional<Dictionary> optionalDictionary = dictionaryRepository.findDictById(id);

        if (optionalDictionary.isPresent()) {
            Dictionary dictionary = optionalDictionary.get();
            dictionary.addWord(word);
            dictionaryRepository.save(dictionary);
        }
    }

    @Override
    public void deleteWordFromDict(Long id, Long wordId) {
        Optional<Dictionary> optionalDictionary = dictionaryRepository.findDictById(id);
        Optional<Word> optionalWord = wordRepository.findWordByWordId(wordId);

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
        Language language = languageRepository.findByLangCode(langCode).orElseThrow(
                () -> new ResourceNotFoundException("Language with code " + langCode + " not found.")
        );

        return dictionaryRepository.findByDictLang(language);
    }
}
