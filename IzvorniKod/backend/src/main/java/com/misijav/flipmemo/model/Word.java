package com.misijav.flipmemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long wordId;

    private String wordLanguageCode;
    @NotEmpty
    private String originalWord;
    @NotEmpty
    private String translatedWord;

    @ElementCollection
    private List<String> wordDescription = new ArrayList<>();

    @ElementCollection
    private List<String> wordSynonyms = new ArrayList<>();

    @ManyToMany
    private List<Dictionary> dictionaries = new ArrayList<>();

    @ManyToMany(mappedBy = "words")
    private List<Pot> pots;

    @ElementCollection(fetch = FetchType.LAZY)
    /*
    @CollectionTable(name = "word_review_times", joinColumns = @JoinColumn(name = "word_id"))
    @MapKeyJoinColumn(name = "pot_id")
    @Column(name = "last_reviewed")

     */
    private Map<Pot, LocalDateTime> lastReviewedTimes = new HashMap<>();

    public Word() {}

    public Word(String wordLanguageCode, String originalWord, String translatedWord, List<String> wordDescription) {
        this.wordLanguageCode = wordLanguageCode;
        this.originalWord = originalWord;
        this.translatedWord = translatedWord;
        this.wordDescription = wordDescription;
    }

    public Long getWordId() { return wordId; }

    public String getWordLanguageCode() { return wordLanguageCode; }

    public String getOriginalWord() { return originalWord; }

    public String getTranslatedWord() { return translatedWord; }

    public List<String> getWordSynonyms() { return wordSynonyms; }

    public List<String> getWordDescription() { return wordDescription; }

    public List<Long> getDictionaries() {
        List<Long> dictionariesId = new ArrayList<>();
        for (Dictionary dict : dictionaries) {
            dictionariesId.add(dict.getDictionaryId());
        }
        return dictionariesId;
    }

    public List<Pot> getPots() {
        return pots;
    }

    public Map<Pot, LocalDateTime> getLastReviewedTimes() { return lastReviewedTimes; }

    public void setWordLanguageCode(String wordLanguage) { this.wordLanguageCode = wordLanguage; }

    public void setOriginalWord(String originalWord) { this.originalWord = originalWord; }

    public void setTranslatedWord(String translatedWord) { this.translatedWord = translatedWord; }

    public void setSynonyms(List<String> wordSynonyms) { this.wordSynonyms = wordSynonyms; }

    public void setDictionaries(List<Dictionary> dictionaries) { this.dictionaries = dictionaries; }

    public void setPots(List<Pot> pots) { this.pots = pots; }

    public void setWordDescription(List<String> wordDescription) { this.wordDescription = wordDescription; }

    public void setLastReviewedTimes(Map<Pot, LocalDateTime> lastReviewedTimes) {
        this.lastReviewedTimes = lastReviewedTimes;
    }

    public void addDictionary(Dictionary dictionary) { this.dictionaries.add(dictionary); }

    public void addWordSynonym(String synonym) { this.wordSynonyms.add(synonym); }

    @Override
    public String toString() {
        return "Word{" +
                "wordId=" + wordId +
                ", wordLanguageCode='" + wordLanguageCode + '\'' +
                ", originalWord='" + originalWord + '\'' +
                ", translatedWord='" + translatedWord + '\'' +
                ", wordDescription=" + wordDescription +
                ", wordSynonyms=" + wordSynonyms +
                ", dictionaries=" + dictionaries +
                ", pots=" + pots +
                '}';
    }
}
