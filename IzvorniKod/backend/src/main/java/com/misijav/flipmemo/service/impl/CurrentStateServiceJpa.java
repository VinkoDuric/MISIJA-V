package com.misijav.flipmemo.service.impl;

import com.misijav.flipmemo.dao.CurrentStateRepository;
import com.misijav.flipmemo.exception.ResourceNotFoundException;
import com.misijav.flipmemo.model.Account;
import com.misijav.flipmemo.model.Dictionary;
import com.misijav.flipmemo.model.CurrentState;
import com.misijav.flipmemo.model.LearningMode;
import com.misijav.flipmemo.service.CurrentStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrentStateServiceJpa implements CurrentStateService {

    @Autowired
    private CurrentStateRepository currentStateRepository;

    public void updateLearningMode(Long userId, LearningMode learningMode) {
        // Find the current state for the user
        CurrentState currentState = currentStateRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Current state not found for user with this id:" + userId));
        currentState.setLearningMode(learningMode);
        currentStateRepository.save(currentState);
    }

    @Override
    public void initializeLearningStatesForUser(Account user, Long dictionaryId) {
        // TODO
    }

    @Override
    public Optional<CurrentState> findByUserId(Long userId) {
        return currentStateRepository.findByUserId(userId);
    }
}
