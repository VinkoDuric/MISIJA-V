package com.misijav.flipmemo.dao;

import com.misijav.flipmemo.model.Pot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PotRepository extends CrudRepository<Pot, Long> {
    List<Pot> findByUserId(Long userId);
}
