package com.misijav.flipmemo.model;

import jakarta.persistence.*;

@Entity
public class CurrentState {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Account user;

    @ManyToOne
    private Dictionary dictionary;

    @ManyToOne
    private LearningMode learningMode;

    private CurrentState() {}

    public CurrentState(Account user, Dictionary dictionary) {
        this.user = user;
        this.dictionary = dictionary;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void setLearningMode(LearningMode learningMode) {
        this.learningMode = learningMode;
    }

    public Long getId() {
        return id;
    }

    public Account getUser() {
        return user;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public LearningMode getLearningMode() {
        return learningMode;
    }
}
