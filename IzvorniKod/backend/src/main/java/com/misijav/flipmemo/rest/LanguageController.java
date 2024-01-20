package com.misijav.flipmemo.rest;

import com.misijav.flipmemo.dto.DictionaryDTO;
import com.misijav.flipmemo.dto.DictionaryDTOMapper;
import com.misijav.flipmemo.model.Dictionary;
import com.misijav.flipmemo.model.Language;
import com.misijav.flipmemo.service.DictionaryService;
import com.misijav.flipmemo.service.LanguageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/languages")
public class LanguageController {
    private static final Logger logger = LoggerFactory.getLogger(LanguageController.class);

    private final LanguageService languageService;
    private final DictionaryService dictionaryService;

    @Autowired
    public LanguageController(LanguageService languageService, DictionaryService dictionaryService) {
        this.languageService = languageService;
        this.dictionaryService = dictionaryService;
    }

    @GetMapping
    public ResponseEntity<List<Language>> GET() {
        logger.info("Received request to display all currently available languages.");
        List<Language> languages = languageService.getAllLanguages();

        if (!languages.isEmpty()) {
            return new ResponseEntity<>(languages, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
    }

    @PostMapping
    public ResponseEntity<Language> POST(@RequestBody LanguageModificationRequest language) {
        logger.info("Received request to add new language.");
        languageService.addLanguage(language);
        logger.info("Language with name {} and code {} added successfully.",language.languageName(), language.langCode());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{lang-code}")
    public void PUT(@RequestBody LanguageModificationRequest langModifyRequest,
                           @PathVariable(value = "lang-code") String langCode) {
        logger.info("Received request to modify language with code: {}.",langCode);
        languageService.updateLanguage(langCode, langModifyRequest);
        logger.info("Successfully modified language with code: {}.",langCode);
    }

    @DeleteMapping("/{lang-code}")
    public void DELETE(@PathVariable(value = "lang-code") String langCode) {
        logger.info("Received request to delete language with code: {}.",langCode);
        languageService.deleteLanguage(langCode);
        logger.info("Successfully deleted language with code: {}.",langCode);
    }

    @GetMapping("/{lang-code}")
    public List<DictionaryDTO> GET(@PathVariable(value = "lang-code") String langCode) {
        logger.info("Received request to display language with code: {}.", langCode);
        List<Dictionary> dictionaries = dictionaryService.getDictsByLangCode(langCode);
        DictionaryDTOMapper mapper = new DictionaryDTOMapper();
        return dictionaries.stream().map(mapper).collect(Collectors.toList());
    }
}
