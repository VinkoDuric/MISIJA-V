package com.misijav.flipmemo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
public class Pot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long potId;

    @ManyToOne
    Account user;

    @ManyToMany
    /*
    @JoinTable(
            name = "wordInPot",
            joinColumns = @JoinColumn(name = "potId"),
            inverseJoinColumns = @JoinColumn(name = "wordId")
    )*/
    private ArrayList<Word> words;

    private int potNumber;
    private LocalDateTime lastReviewed;

    private Pot() {}

    public Pot(Account user, int potNumber, LocalDateTime lastReviewed) {
        this.user = user;
        this.potNumber = potNumber;
        this.lastReviewed = lastReviewed;
    }

    public void setAccount(Account user) {
        this.user = user;
    }

    public void setPotNumber(int potNumber) {
        this.potNumber = potNumber;
    }

    public void setLastReviewed(LocalDateTime lastReviewed) {
        this.lastReviewed = lastReviewed;
    }

    public Long getPotId() {
        return potId;
    }

    public Account getUser() {
        return user;
    }

    public int getPotNumber() {
        return potNumber;
    }

    public LocalDateTime getLastReviewed() {
        return lastReviewed;
    }

    // Method to add a word to the pot
    public void addWord(Word word) {
        if (words == null) {
            words = new ArrayList<>();
        }
        words.add(word);
    }

    // Method to remove a word from a pot
    public boolean removeWord(Word word) {
        if (words.contains(word)) {
            return words.remove(word);
        }
        return false;
    }
}
