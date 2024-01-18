package com.misijav.flipmemo.dao;

import com.misijav.flipmemo.model.Word;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WordRepository extends CrudRepository<Word, Long> {

    Optional<Word> findByOriginalWord(String wordName);

    Optional<Word> findWordByWordId(Long id);
}
