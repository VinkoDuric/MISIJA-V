package com.misijav.flipmemo.service.impl;

import com.misijav.flipmemo.dao.WordRepository;
import com.misijav.flipmemo.exception.ResourceNotFoundException;
import com.misijav.flipmemo.model.Word;
import com.misijav.flipmemo.rest.WordModificationRequest;
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
    public void addWord(Word word) {
        // Save word to the repository
        wordRepository.save(word);
    }

    @Override
    public Word updateWord(Long id, WordModificationRequest wordDetails) {
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

        return wordRepository.save(word);
    }

     @Override
     public void deleteWord(Long id) {
        wordRepository.deleteById(id);
     }


    public Optional<Word> getWordById(Long id) {
        return wordRepository.findById(id);
    }
}
