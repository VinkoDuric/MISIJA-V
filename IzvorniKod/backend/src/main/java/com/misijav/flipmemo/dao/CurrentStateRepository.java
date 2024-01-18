package com.misijav.flipmemo.dao;

import com.misijav.flipmemo.model.CurrentState;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CurrentStateRepository extends CrudRepository<CurrentState, Long> {

    //List<CurrentState> findByUserIdAndPotNumber(Long userId, int potNumber);

    Optional<CurrentState> findByUserId(Long userId);
}
