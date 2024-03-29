package com.misijav.flipmemo.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
public class Dictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String dictName;

    private String dictImage;

    @ManyToOne
    private Language dictLang;

    @ManyToMany
    @JoinTable(
            name = "dictionary_word",
            joinColumns = @JoinColumn(name = "dictionary_id"),
            inverseJoinColumns = @JoinColumn(name = "word_id")
    )
    private List<Word> dictWords;

    public Dictionary() {}

    public Dictionary(String dictName, String dictImage, Language dictLang) {
        this.dictName = dictName;
        this.dictImage = dictImage;
        this.dictLang = dictLang;
    }

    public Long getDictionaryId() { return this.id; }

    public String getDictName() {
        return dictName;
    }

    public String getDictImage() {
        return dictImage;
    }

    public Language getDictLang() {
        return dictLang;
    }

    public List<Word> getDictWords() { return dictWords; }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public void setDictImage(String dictImage) {
        this.dictImage = dictImage;
    }

    public void setDictLang(Language dictLang) {
        this.dictLang = dictLang;
    }

    public void setDictWords(List<Word> dictWords) {
        this.dictWords = dictWords;
    }

    public void addWord(Word word) {
        this.dictWords.add(word);
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "id=" + id +
                ", dictName='" + dictName + '\'' +
                ", dictImage='" + dictImage + '\'' +
                ", dictLang=" + dictLang +
                ", dictWords=" + dictWords +
                '}';
    }
}
