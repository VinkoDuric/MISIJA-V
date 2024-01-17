package com.misijav.flipmemo.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;

@Entity
public class Dictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String dictName;

    private String dictImage;

    @ManyToOne  // TODO check if this is the right one
    private Language dictLang;

    @ManyToMany  // TODO check if this is the right one
    private ArrayList<Word> dictWords;

    private Dictionary() {}

    public Dictionary(String dictName, String dictImage, Language dictLang) {
        this.dictName = dictName;
        this.dictImage = dictImage;
        this.dictLang = dictLang;
        this.dictWords = new ArrayList<Word>();
    }

    public String getDictName() {
        return dictName;
    }

    public String getDictImage() {
        return dictImage;
    }

    public Language getDictLang() {
        return dictLang;
    }

    public ArrayList<Word> getDictWords() {
        return dictWords;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public void setDictImage(String dictImage) {
        this.dictImage = dictImage;
    }

    public void setDictLang(Language dictLang) {
        this.dictLang = dictLang;
    }

    public void setDictWords(ArrayList<Word> dictWords) {
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
