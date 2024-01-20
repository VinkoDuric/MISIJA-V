package com.misijav.flipmemo.dao;

import com.misijav.flipmemo.model.Dictionary;
import com.misijav.flipmemo.model.Language;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DictionaryRepository extends CrudRepository<Dictionary, Long> {

    Optional<Dictionary> findByDictName(String dictName);

    Optional<Dictionary> findDictById(Long id);

    List<Dictionary> findByDictLang(Language language);
}
