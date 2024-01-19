package com.misijav.flipmemo.dao;

import com.misijav.flipmemo.model.Pot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PotRepository extends CrudRepository<Pot, Long> {
    List<Pot> findByUserId(Long userId);

    @Query("SELECT p FROM Pot p JOIN p.words w WHERE p.user.id = :userId AND w.wordId = :wordId")
    Optional<Pot> findByUserIdAndWordId(Long userId, Long wordId);

    Optional<Pot> findByUserIdAndPotNumber(Long userId, int potNumber);
}
