package com.misijav.flipmemo.model;

import jakarta.persistence.*;

@Entity
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String wordName;
    private String wordDescription;

    private Word() {}

    public Word(String wordName, String wordDescription) {
        this.wordName = wordName;
        this.wordDescription = wordDescription;
    }

    public Long getId() { return id; }

    public String getWordName() { return wordName; }

    public String getWordDescription() { return wordDescription; }

    public void setWordName(String wordName) { this.wordName = wordName; }

    public void setWordDescription(String wordDescription) {
        this.wordDescription = wordDescription;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", wordName='" + wordName + '\'' +
                ", wordDescription='" + wordDescription + '\'' +
                '}';
    }
}
