package com.misijav.flipmemo.service.impl;

import com.misijav.flipmemo.dao.WordRepository;
import com.misijav.flipmemo.exception.ResourceConflictException;
import com.misijav.flipmemo.exception.ResourceNotFoundException;
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

    @Autowired
    public WordServiceJpa(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Override
    public void addWord(WordRequest request) {
        // Check if wordName and wordDescription are present and not null or empty
        if (request.wordName() == null || request.wordName().isEmpty()) {
            throw new IllegalArgumentException("Word name must be provided.");
        }
        if (request.wordDescription() == null || request.wordDescription().isEmpty()) {
            throw new IllegalArgumentException("Word description must be provided.");
        }

        // Check if word is unique
        if (wordRepository.findByWordName(request.wordName()).isPresent()) {
            throw new ResourceConflictException("Word already exists with the same name.");
        }

        Word newWord = new Word(request.wordName(), request.wordDescription());

        // Save word to the repository
        wordRepository.save(newWord);
    }

    @Override
    public void updateWord(Long id, WordModificationRequest wordDetails) {
        // Check if word exists
        Word word = wordRepository.findWordById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Word not found for this id: " + id));

        // Update wordName if present
        if (wordDetails.wordName() != null && !wordDetails.wordName().isEmpty()) {
            word.setWordName(wordDetails.wordName());
        }

        // Update wordDescription if present
        if (wordDetails.wordDescription() != null && !wordDetails.wordDescription().isEmpty()) {
            word.setWordDescription(wordDetails.wordDescription());
        }

        wordRepository.save(word);
    }

     @Override
     public void deleteWord(Long id) {
         // Check if word exists
         Word word = wordRepository.findWordById(id)
                 .orElseThrow(() -> new ResourceNotFoundException("Word not found for this id: " + id));

         wordRepository.delete(word);
     }

    public Optional<Word> getWordById(Long id) {
        return wordRepository.findById(id);
    }
}
