package com.misijav.flipmemo.dao;

import com.misijav.flipmemo.model.Pot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PotRepository extends CrudRepository<Pot, Long> {
    List<Pot> findByUserIdAndDictionaryId(Long userId, Long dictionaryId);

    @Query("SELECT p FROM Pot p JOIN p.words w WHERE p.user.id = :userId AND w.wordId = :wordId AND p.dictionary.id = :dictId")
    Optional<Pot> findByUserIdWordIdAndDictId(Long userId, Long wordId, Long dictId);

    @Query("SELECT p FROM Pot p WHERE p.user.id = :userId AND p.potNumber = :potNumber AND p.dictionary.id = :dictId")
    Optional<Pot> findByUserIdPotNumberAndDictId(Long userId, int potNumber, Long dictId);
}
