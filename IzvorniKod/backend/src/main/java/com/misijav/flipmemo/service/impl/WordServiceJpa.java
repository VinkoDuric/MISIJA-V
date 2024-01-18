package com.misijav.flipmemo.service.impl;

import com.misijav.flipmemo.dao.DictionaryRepository;
import com.misijav.flipmemo.dao.WordRepository;
import com.misijav.flipmemo.exception.ResourceConflictException;
import com.misijav.flipmemo.exception.ResourceNotFoundException;
import com.misijav.flipmemo.model.Dictionary;
import com.misijav.flipmemo.model.Word;
import com.misijav.flipmemo.rest.word.WordModificationRequest;
import com.misijav.flipmemo.rest.word.WordRequest;
import com.misijav.flipmemo.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WordServiceJpa implements WordService {
    private final WordRepository wordRepository;
    private final DictionaryRepository dictionaryRepository;

    @Autowired
    public WordServiceJpa(WordRepository wordRepository, DictionaryRepository dictionaryRepository) {
        this.wordRepository = wordRepository;
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    public Word addWord(WordRequest request) {

        if (request.originalWord() == null || request.originalWord().isEmpty()) {
            throw new IllegalArgumentException("Word name must be provided.");
        }
        if (request.translatedWord() == null || request.translatedWord().isEmpty()) {
            throw new IllegalArgumentException("Word translation must be provided.");
        }
        if (request.wordLanguageCode() == null || request.wordLanguageCode().isEmpty()) {
            throw new IllegalArgumentException("Word language code must be provided.");
        }
        if (request.wordDescription() == null || request.wordDescription().isEmpty()) {
            throw new IllegalArgumentException("Word description must be provided.");
        }
        if (request.wordSynonyms() == null || request.wordSynonyms().isEmpty()) {
            throw new IllegalArgumentException("Word synonyms must be provided.");
        }
        if (request.dictionaryIds() == null || request.dictionaryIds().isEmpty()) {
            throw new IllegalArgumentException("Word dictionaries must be provided.");
        }

        // Check if word is unique
        if (wordRepository.findByOriginalWord(request.originalWord()).isPresent()) {
            throw new ResourceConflictException("Word already exists with the same name.");
        }

        // Create new Word
        Word newWord = new Word();
        newWord.setOriginalWord(request.originalWord());
        newWord.setTranslatedWord(request.translatedWord());
        newWord.setWordLanguageCode(request.wordLanguageCode());
        newWord.setWordDescription(request.wordDescription());
        newWord.setSynonyms(request.wordSynonyms());

        // Fetch dictionaries and associate them with the Word
        for (Long dictId : request.dictionaryIds()) {
            Dictionary dictionary = dictionaryRepository.findById(dictId)
                    .orElseThrow(() -> new ResourceNotFoundException("Dictionary not found for this id: " + dictId));
            newWord.addDictionary(dictionary);
        }

        // Save word to the repository
        return wordRepository.save(newWord);
    }

    @Override
    public void updateWord(Long id, WordModificationRequest wordDetails) {
        // Check if word exists
        Word word = wordRepository.findWordByWordId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Word not found for this id: " + id));

        // Update wordLanguageCode if present
        if (wordDetails.wordLanguageCode() != null && !wordDetails.wordLanguageCode().isEmpty()) {
            word.setWordLanguageCode(wordDetails.wordLanguageCode());
        }

        // Update originalWord if present
        if (wordDetails.originalWord() != null && !wordDetails.originalWord().isEmpty()) {
            word.setOriginalWord(wordDetails.originalWord());
        }

        // Update translatedWord if present
        if (wordDetails.translatedWord() != null && !wordDetails.translatedWord().isEmpty()) {
            word.setTranslatedWord(wordDetails.translatedWord());
        }

        // Update wordDescription if present
        if (wordDetails.wordDescription() != null && !wordDetails.wordDescription().isEmpty()) {
            word.setWordDescription(wordDetails.wordDescription());
        }

        // Add synonyms if present
        if (wordDetails.wordSynonyms() != null && !wordDetails.wordSynonyms().isEmpty()) {
            for (String synonym : wordDetails.wordSynonyms()) {
                word.addWordSynonym(synonym);
            }
        }

        // Update if present
        if (wordDetails.dictionaryIds() != null && !wordDetails.dictionaryIds().isEmpty()) {
           for (Long dictId : wordDetails.dictionaryIds()) {
               Dictionary dictionary = dictionaryRepository.findById(dictId)
                       .orElseThrow(() -> new ResourceNotFoundException("Dictionary not found for this id: " + dictId));
               word.addDictionary(dictionary);
           }
        }

        wordRepository.save(word);
    }

     @Override
     public void deleteWord(Long id) {
         // Check if word exists
         Word word = wordRepository.findWordByWordId(id)
                 .orElseThrow(() -> new ResourceNotFoundException("Word not found for this id: " + id));

         wordRepository.delete(word);
     }

    public Optional<Word> getWordById(Long id) {
        return wordRepository.findById(id);
    }
}
