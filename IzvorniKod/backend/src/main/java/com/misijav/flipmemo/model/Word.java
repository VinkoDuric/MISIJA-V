package com.misijav.flipmemo.model;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long wordId;
    private String wordName;
    private String wordDescription;

    @ManyToMany(mappedBy = "words")
    private ArrayList<Pot> pots;

    private Word() {}

    public Word(String wordName, String wordDescription) {
        this.wordName = wordName;
        this.wordDescription = wordDescription;
    }

    public Long getWordId() { return wordId; }

    public String getWordName() { return wordName; }

    public String getWordDescription() { return wordDescription; }

    public void setWordName(String wordName) { this.wordName = wordName; }

    public void setWordDescription(String wordDescription) {
        this.wordDescription = wordDescription;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + wordId +
                ", wordName='" + wordName + '\'' +
                ", wordDescription='" + wordDescription + '\'' +
                '}';
    }
}
