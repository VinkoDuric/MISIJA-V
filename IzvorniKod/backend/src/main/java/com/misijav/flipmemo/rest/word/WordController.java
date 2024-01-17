package com.misijav.flipmemo.rest.word;

import com.misijav.flipmemo.exception.ResourceNotFoundException;
import com.misijav.flipmemo.model.Word;
import com.misijav.flipmemo.service.WordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> POST(@RequestBody WordRequest wordRequest) {
        // Add new word to the repository
        wordService.addWord(wordRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Word added successfully.");
    }

    @PutMapping
    public ResponseEntity<?> PUT(@Valid @RequestBody WordModificationRequest wordModifyRequest) {
        Long wordId = wordModifyRequest.id();
        // Update an existing word
        wordService.updateWord(wordId, wordModifyRequest);
        return ResponseEntity.ok().body("Word updated successfully.");
    }

    // get word information
    @GetMapping("/{word_id}")
    public ResponseEntity<?> GET(@PathVariable(value = "word_id") Long wordId) {
        Word word = wordService.getWordById(wordId)
                .orElseThrow(() -> new ResourceNotFoundException("Word not found for this id: " + wordId));
        return ResponseEntity.ok().body(word);
    }

    @DeleteMapping
    public ResponseEntity<?> DELETE(@Valid @RequestBody WordModificationRequest request) {
        wordService.deleteWord(request.id());
        return ResponseEntity.ok().body("Word deleted successfully.");
    }
}
