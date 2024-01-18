package com.misijav.flipmemo.rest.dict;

import com.misijav.flipmemo.exception.ResourceNotFoundException;
import com.misijav.flipmemo.model.Dictionary;
import com.misijav.flipmemo.model.Word;
import com.misijav.flipmemo.service.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/dictionaries")
public class DictionaryController {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);

    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping
    public ResponseEntity<List<Dictionary>> GET() {
        logger.info("Received request to display all currently available dictionaries.");
        List<Dictionary> dictionaries = dictionaryService.getAllDicts();

        if (!dictionaries.isEmpty()) {
            return new ResponseEntity<>(dictionaries, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
    }

    @PostMapping
    public ResponseEntity<?> POST(@RequestBody DictionaryRequest request) {
        logger.info("Received request to add new dictionary with name {}.", request.dictImage());
        Dictionary createdDict = dictionaryService.addDictionary(request);
        logger.info("Dictionary with name {} and langCode {} added successfully.",
                request.dictImage(), request.langCode());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDict);
    }

    @PutMapping("/{dict-id}")
    public void PUT(@RequestBody DictionaryModificationRequest dictionaryModificationRequest,
                           @PathVariable(value = "dict-id") Long id) {
        logger.info("Received request to modify dictionary with id {}.", id);
        dictionaryService.updateDictionary(id, dictionaryModificationRequest);
        logger.info("Dictionary with id {} modified successfully.", id);
    }

    @DeleteMapping("/{dict-id}")
    public void DELETE(@PathVariable(value = "dict-id") Long id){
        logger.info("Received request to delete dictionary with id {}.", id);
        dictionaryService.deleteDictionary(id);
        logger.info("Successfully deleted dictionary with id {}.", id);
    }

    @GetMapping("/{dict-id}")
    public ResponseEntity<ArrayList<Word>> GET(@PathVariable(value = "dict-id") Long id) {
        logger.info("Received request to display directory with id {}.", id);
        Optional<Dictionary> dictionary = dictionaryService.findByDictId(id);

        if(dictionary.isPresent()) {
            Dictionary dict = dictionary.get();
            ArrayList<Word> words = dict.getDictWords();
            return new ResponseEntity<ArrayList<Word>>(words, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{dict-id}")
    public void POST(@PathVariable(value = "dict-id") Long id, @RequestBody Word word) {
        logger.info("Received request to add word with id {} to dictionary with id {}.", word.getWordId(), id);
        dictionaryService.findByDictId(id).
                orElseThrow(() -> new ResourceNotFoundException("Dictionary not found with id: " + id));

        dictionaryService.addWordToDict(id, word);
        logger.info("Successfully added word with id {} to dictionary with id {}.", word.getWordId(), id);
    }

    @DeleteMapping("/{dict-id}/{word-id}")
    public void DELETE(@PathVariable(value = "dict-id") Long id,
                                   @PathVariable(value = "word-id") Long wordId) {
        logger.info("Received request to delete word with id {} from dictionary with id {}.", wordId, id);
        dictionaryService.deleteWordFromDict(id, wordId);
        logger.info("Successfully deleted word with id {} from dictionary with id {}.", wordId, id);
    }
}
