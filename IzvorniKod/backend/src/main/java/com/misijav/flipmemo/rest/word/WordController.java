package com.misijav.flipmemo.rest.word;

import com.misijav.flipmemo.dto.WordDTO;
import com.misijav.flipmemo.dto.WordDTOMapper;
import com.misijav.flipmemo.exception.ResourceNotFoundException;
import com.misijav.flipmemo.model.Word;
import com.misijav.flipmemo.service.WordService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/word")
public class WordController {
    private static final Logger logger = LoggerFactory.getLogger(WordController.class);

    private final WordService wordService;

    @Autowired
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @PostMapping
    public ResponseEntity<?> POST(@RequestBody WordRequest wordRequest) {
        logger.info("Received request to add new word with name {} to dictionary.", wordRequest.originalWord());
        Word createdWord = wordService.addWord(wordRequest);  // add new word to dictionary/ies
        WordDTOMapper mapper = new WordDTOMapper();
        WordDTO wordDTO = mapper.apply(createdWord);
        logger.info("Word with name {} added successfully to dictionary.", wordRequest.originalWord());
        return ResponseEntity.status(HttpStatus.CREATED).body(wordDTO);
    }

    @PutMapping
    public ResponseEntity<?> PUT(@Valid @RequestBody WordModificationRequest wordModifyRequest) {
        logger.info("Received request to modify word with id {}.", wordModifyRequest.id());
        wordService.updateWord(wordModifyRequest.id(), wordModifyRequest);  // Update an existing word
        logger.info("Word with id {} modified successfully.", wordModifyRequest.id());
        return ResponseEntity.ok().body("Word updated successfully.");
    }

    @GetMapping("/{word-id}")
    public ResponseEntity<?> GET(@PathVariable(value = "word-id") Long wordId) {
        logger.info("Received request to display word with id {}.", wordId);
        Word word = wordService.getWordById(wordId)
                .orElseThrow(() -> new ResourceNotFoundException("Word not found for this id: " + wordId));
        WordDTOMapper mapper = new WordDTOMapper();
        WordDTO wordDTO = mapper.apply(word);
        return ResponseEntity.ok().body(wordDTO);  // return word information
    }

    @DeleteMapping
    public ResponseEntity<?> DELETE(@Valid @RequestBody WordModificationRequest request) {
        logger.info("Received request to delete word with id {}.", request.id());
        wordService.deleteWord(request.id());
        logger.info("Successfully deleted word with id {}.", request.id());
        return ResponseEntity.ok().body("Word deleted successfully.");
    }
}
