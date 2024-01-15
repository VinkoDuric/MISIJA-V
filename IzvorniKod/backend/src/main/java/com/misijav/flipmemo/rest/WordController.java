package com.misijav.flipmemo.rest;

import com.misijav.flipmemo.exception.ResourceNotFoundException;
import com.misijav.flipmemo.model.Word;
import com.misijav.flipmemo.service.WordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/word")
public class WordController {
    private final WordService wordService;

    @Autowired
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @PostMapping
    public ResponseEntity<?> POST(@RequestBody Word word) {
        // Add new word to the repository
        wordService.addWord(word);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> PUT(@Valid @RequestBody WordModificationRequest wordModifyRequest) {
        Long wordId = wordModifyRequest.id();

        // Update an existing word
        Word updatedWord = wordService.updateWord(wordId, wordModifyRequest);

        return ResponseEntity.ok().body(updatedWord);
    }

    // get word information
    @GetMapping("/{word_id}")
    public ResponseEntity<?> GET(@PathVariable(value = "word_id") Long wordId) {
        Word word = wordService.getWordById(wordId)
                .orElseThrow(() -> new ResourceNotFoundException("Word not found for this id: " + wordId));
        return ResponseEntity.ok().body(word);
    }
}
