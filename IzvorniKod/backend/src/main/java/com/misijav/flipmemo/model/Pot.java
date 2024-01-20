package com.misijav.flipmemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.temporal.ChronoUnit;

@Entity
public class Pot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long potId;

    @ManyToOne
    private Account user;

    @ManyToMany(mappedBy = "pots")
    private List<Word> words;

    @ManyToOne
    private Dictionary dictionary;

    private int potNumber;
    private int maxPotNumber;
    private int potTimeInterval;  // in days

    public Pot() {}

    public Pot(Account user, int potNumber, Dictionary dictionary) {
        this.user = user;
        this.potNumber = potNumber;
        this.dictionary = dictionary;
        this.potTimeInterval = (int) Math.pow(2, potNumber - 1);  // 1, 2, 4, 8, 16, 32
    }

    public void setAccount(Account user) {
        this.user = user;
    }

    public void setPotNumber(int potNumber) {
        this.potNumber = potNumber;
    }

    public void setMaxPotNumber(int maxPotNumber) { this.maxPotNumber = maxPotNumber; }

    @JsonIgnore
    public void setDictionary(Dictionary dictionary) { this.dictionary = dictionary; }

    public Long getPotId() {
        return potId;
    }

    @JsonIgnore
    public Account getUser() {
        return user;
    }

    public int getPotNumber() {
        return potNumber;
    }

    public int getMaxPotNumber() { return maxPotNumber; }

    public List<Word> getWords() { return words; }

    public Dictionary getDictionary() { return dictionary; }

    public int getPotTimeInterval() { return potTimeInterval; }

    // Method to add a word to the pot
    public void addWord(Word word) {
        if (words == null) {
            words = new ArrayList<>();
        }
        if (!words.contains(word)) {
            words.add(word);
            word.addPot(this); // Add this pot to the word's list of pots
        }
    }

    // Method to remove a word from a pot
    public boolean removeWord(Word word) {
        if (words.contains(word)) {
            return words.remove(word);
        }
        return false;
    }

    // Method to get all available words ("time.now - word.time <= potTimeInterval")
    @Transactional
    public List<Word> getAvailableWords() {
        if (this.words == null) {
            return null;
        }
        List<Word> availableWords = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (Word word : this.words) {
            if (availableWords.size() > 50) { return availableWords; } // future optimisation: adjust this constant
            LocalDateTime lastReviewedTime = word.getLastReviewedTimes().get(this);
            if (lastReviewedTime == null || ChronoUnit.DAYS.between(lastReviewedTime, now) >= this.potTimeInterval) {
                availableWords.add(word);
            }
        }
        return availableWords;
    }

    @Override
    public String toString() {
        return "Pot{" +
                "potId=" + potId +
                ", potNumber=" + potNumber +
                ", potTimeInterval=" + potTimeInterval +
                '}';
    }
}
