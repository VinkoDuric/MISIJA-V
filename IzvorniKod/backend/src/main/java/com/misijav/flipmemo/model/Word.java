package com.misijav.flipmemo.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long wordId;
    private String wordName;
    private String wordDescription;

    @ManyToMany
    /*
    @JoinTable(
            name = "dictionaryWord",
            joinColumns = @JoinColumn(name = "wordId"),
            inverseJoinColumns = @JoinColumn(name = "dictionaryId")
    ) */
    private List<Dictionary> dictionaries = new ArrayList<>();

    @ManyToMany(mappedBy = "words")
    private List<Pot> pots = new ArrayList<>();

    protected Word() {}

    public Word(String wordName, String wordDescription) {
        this.wordName = wordName;
        this.wordDescription = wordDescription;
    }

    public Long getWordId() { return wordId; }

    public String getWordName() { return wordName; }

    public String getWordDescription() { return wordDescription; }

    public ArrayList<Dictionary> getDictionaries() {
        return (ArrayList<Dictionary>) dictionaries;
    }

    public ArrayList<Pot> getPots() {
        return (ArrayList<Pot>) pots;
    }

    public void setWordName(String wordName) { this.wordName = wordName; }

    public void setWordDescription(String wordDescription) {
        this.wordDescription = wordDescription;
    }

    public void setDictionaries(ArrayList<Dictionary> dictionaries) {
        this.dictionaries = dictionaries;
    }

    public void setPots(ArrayList<Pot> pots) {
        this.pots = pots;
    }

    @Override
    public String toString() {
        return "Word{" +
                "wordId=" + wordId +
                ", wordName='" + wordName + '\'' +
                ", wordDescription='" + wordDescription + '\'' +
                ", dictionaries=" + dictionaries +
                ", pots=" + pots +
                '}';
    }

    public void addDictionary(Dictionary dictionary) {
        if (dictionaries == null) {
            dictionaries = new ArrayList<>();
        }
        dictionaries.add(dictionary);
    }
}
