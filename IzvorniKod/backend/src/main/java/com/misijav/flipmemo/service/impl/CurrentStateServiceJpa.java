package com.misijav.flipmemo.service.impl;

import com.misijav.flipmemo.dao.AccountRepository;
import com.misijav.flipmemo.dao.DictionaryRepository;
import com.misijav.flipmemo.dao.PotRepository;
import com.misijav.flipmemo.exception.ResourceNotFoundException;
import com.misijav.flipmemo.model.*;
import com.misijav.flipmemo.service.CurrentStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CurrentStateServiceJpa implements CurrentStateService {
    private final DictionaryRepository dictionaryRepository;
    private final PotRepository potRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public CurrentStateServiceJpa(DictionaryRepository dictionaryRepository,
                                  PotRepository potRepository,
                                  AccountRepository accountRepository) {
        this.dictionaryRepository = dictionaryRepository;
        this.potRepository = potRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void initializePotsForUser(Long userId, Long dictionaryId) {
        Dictionary dictionary = dictionaryRepository.findById(dictionaryId)
                .orElseThrow(() -> new ResourceNotFoundException("Dictionary not found with id: "+ dictionaryId));
        Account user = accountRepository.findUserById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id " + userId));

        // Create pots for this user and dictionary
        int numberOfPots = 6;  // Number of pots
        for (int potNum = 1; potNum <= numberOfPots; potNum++) {
            Pot newPot = new Pot(user, potNum, dictionary);
            if (potNum == 1) {  // For the first pot, add all words from the dictionary
                for (Word word : dictionary.getDictWords()) {
                    newPot.addWord(word);
                }
            }
            newPot.setMaxPotNumber(numberOfPots);
            potRepository.save(newPot);
        }
    }
}
