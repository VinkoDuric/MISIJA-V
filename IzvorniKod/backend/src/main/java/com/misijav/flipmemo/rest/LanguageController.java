package com.misijav.flipmemo.rest;

import com.misijav.flipmemo.model.Language;
import com.misijav.flipmemo.service.DictionaryService;
import com.misijav.flipmemo.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/v1/languages")
public class LanguageController {

    private final LanguageService languageService;
    private final DictionaryService dictionaryService;

    @Autowired
    public LanguageController(LanguageService languageService, DictionaryService dictionaryService) {
        this.languageService = languageService;
        this.dictionaryService = dictionaryService;
    }

    @GetMapping
    public ResponseEntity<List<Language>> GET() {
        List<Language> languages = languageService.getAllLanguages();

        if (!languages.isEmpty()) {
            return new ResponseEntity<>(languages, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
    }

    @PostMapping
    public ResponseEntity<Language> POST(@RequestBody Language language) {
        Language addedLanguage = languageService.addLanguage(language);

        if (addedLanguage != null) {
            return new ResponseEntity<>(addedLanguage, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{lang-code}")
    public void PUT(@RequestBody LanguageModificationRequest langModifyRequest,
                           @PathVariable String langCode) {
        languageService.updateLanguage(langCode, langModifyRequest);
    }

    @DeleteMapping("/{lang-code}")
    public void DELETE(@PathVariable String langCode) {
        languageService.deleteLanguage(langCode);
    }

    @GetMapping("/{lang-code}")
    public void GET(@PathVariable String langCode) {
        dictionaryService.getDictsByLangCode(langCode);
    }
}
