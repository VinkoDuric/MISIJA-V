package com.misijav.flipmemo.service.impl;

import com.misijav.flipmemo.dao.DictionaryRepository;
import com.misijav.flipmemo.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizServiceJpa implements QuizService {
    private final DictionaryRepository dictionaryRepository;

    @Autowired
    public QuizServiceJpa(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    /*
    @Override
    public QuizQuestion getQuizQuestion(Long dictionaryId, Long userId) {
        return null;
    }


    @Override
    public boolean checkAnswer(Long wordId, String type, Answer answer) {
        return false;
    }
     */
}
