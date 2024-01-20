package com.misijav.flipmemo.service.impl;

import com.misijav.flipmemo.dao.AccountRepository;
import com.misijav.flipmemo.dao.CurrentStateRepository;
import com.misijav.flipmemo.dao.DictionaryRepository;
import com.misijav.flipmemo.dao.PotRepository;
import com.misijav.flipmemo.exception.ResourceNotFoundException;
import com.misijav.flipmemo.model.*;
import com.misijav.flipmemo.service.CurrentStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrentStateServiceJpa implements CurrentStateService {
    private final CurrentStateRepository currentStateRepository;
    private final DictionaryRepository dictionaryRepository;
    private final PotRepository potRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public CurrentStateServiceJpa(CurrentStateRepository currentStateRepository,
                                  DictionaryRepository dictionaryRepository,
                                  PotRepository potRepository,
                                  AccountRepository accountRepository) {
        this.currentStateRepository = currentStateRepository;
        this.dictionaryRepository = dictionaryRepository;
        this.potRepository = potRepository;
        this.accountRepository = accountRepository;
    }

    public void initializePotsForUser(Long userId, Long dictionaryId) {
        Dictionary dictionary = dictionaryRepository.findById(dictionaryId)
                .orElseThrow(() -> new ResourceNotFoundException("Dictionary not found with id: "+ dictionaryId));
        Account user = accountRepository.findUserById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id " + userId));

        // Create pots for this user and dictionary
        int numberOfPots = 3;  // Number of pots
        for (int potNum = 1; potNum <= numberOfPots; potNum++) {
            Pot newPot = new Pot(user, potNum, dictionary);
            if (potNum == 1) {  // For the first pot, add all words from the dictionary
                for (Word word : dictionary.getDictWords()) {
                    newPot.addWord(word);
                }
            }
            potRepository.save(newPot);
        }

        CurrentState currentState = currentStateRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Current state not found for user with id: " + userId));
        currentState.setNumberOfPots(numberOfPots);
        currentStateRepository.save(currentState);
    }

    @Override
    public Optional<CurrentState> findByUserId(Long userId) {
        return currentStateRepository.findByUserId(userId);
    }
}
