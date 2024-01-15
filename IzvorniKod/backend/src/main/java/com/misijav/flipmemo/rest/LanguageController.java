package com.misijav.flipmemo.rest;

import com.misijav.flipmemo.dao.LanguageRepository;
import com.misijav.flipmemo.model.Language;
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

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    public ResponseEntity<List<Language>> getAllLangs() {
        List<Language> languages = languageService.getAllLanguages();

        if (!languages.isEmpty()) {
            return new ResponseEntity<>(languages, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
    }

    @PostMapping
    public ResponseEntity<Language> addLanguage(@RequestBody Language language) {
        Language addedLanguage = languageService.addLanguage(language);

        if (addedLanguage != null) {
            return new ResponseEntity<>(addedLanguage, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/modifyLanguage/{lang-code}")
    public void updateLang(@RequestBody LanguageModificationRequest langModifyRequest,
                           @PathVariable String langCode) {
        languageService.updateLanguage(langCode, langModifyRequest);
    }

    @DeleteMapping("/deleteLanguage/{lang-code}")
    public void deleteLang(@PathVariable String langCode) {
        languageService.deleteLanguage(langCode);
    }


}
