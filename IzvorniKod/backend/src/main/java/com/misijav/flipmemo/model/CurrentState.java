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

    private int numberOfPots;

    public CurrentState() {}

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

    public void setNumberOfPots(int numberOfPots) {
        this.numberOfPots = numberOfPots;
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

    public int getNumberOfPots() { return numberOfPots; }
}
