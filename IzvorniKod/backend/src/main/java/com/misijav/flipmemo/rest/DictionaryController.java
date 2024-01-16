package com.misijav.flipmemo.rest;

import com.misijav.flipmemo.model.Dictionary;
import com.misijav.flipmemo.model.Language;
import com.misijav.flipmemo.model.Word;
import com.misijav.flipmemo.service.DictionaryService;
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

    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping
    public ResponseEntity<List<Dictionary>> getAllDictionaries() {
        List<Dictionary> dictionaries = dictionaryService.getAllDicts();

        if (!dictionaries.isEmpty()) {
            return new ResponseEntity<>(dictionaries, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
    }

    @PostMapping
    public ResponseEntity<Dictionary> addDictionary(@RequestBody Dictionary dictionary) {
        Dictionary addedDict = dictionaryService.addDictionary(dictionary);

        if(addedDict != null) {
            return new ResponseEntity<>(addedDict, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{dict-id}")
    public void updateDict(@RequestBody DictionaryModificationRequest dictionaryModificationRequest,
                           @PathVariable Long id) {
        dictionaryService.updateDictionary(id, dictionaryModificationRequest);
    }

    @DeleteMapping("/{dict-id}")
    public void deleteDict(@PathVariable Long id){
        dictionaryService.deleteDictionary(id);
    }

    @GetMapping("/{dict-id}")
    public ResponseEntity<ArrayList<Word>> getDict(@PathVariable Long id) {
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
    public void addWordToDictionary(
            @PathVariable Long id,
            @RequestBody Word word) {

        Optional<Dictionary> optionalDictionary = dictionaryService.findByDictId(id);

        if (optionalDictionary.isPresent()) {
            dictionaryService.addWordToDict(id, word);
        }
    }

    @DeleteMapping("/{dict-id}/{word-id}")
    public void deleteWordFromDict(@PathVariable Long id,
                                   @PathVariable Long wordId) {
        dictionaryService.deleteWordFromDict(id, wordId);
    }
}






