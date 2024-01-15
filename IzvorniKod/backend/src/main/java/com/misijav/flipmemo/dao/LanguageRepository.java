package com.misijav.flipmemo.dao;

import com.misijav.flipmemo.model.Language;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LanguageRepository extends CrudRepository<Language, String> {

    Optional<Language> findByLanguageName(String languageName);

    Optional<Language> findByLangCode(String langCode);
}
