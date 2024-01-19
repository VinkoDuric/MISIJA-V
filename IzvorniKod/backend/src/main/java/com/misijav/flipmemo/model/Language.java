package com.misijav.flipmemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Language {
    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;*/

    @Id
    private String langCode;

    @NotEmpty
    private String languageName;

    private String languageImage;

    private Language() {}

    public Language(String langCode, String languageName, String languageImage) {
        this.languageName = languageName;
        this.languageImage = languageImage;
        this.langCode = langCode;
    }

    //public Long getId() { return id; }

    public String getLangCode() {
        return langCode;
    }

    public String getLanguageName() {
        return languageName;
    }

    public String getLanguageImage() {
        return languageImage;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public void setLanguageImage(String languageImage) {
        this.languageImage = languageImage;
    }

    @Override
    public String toString() {
        return "Language{" +
                "langCode=" + langCode +
                ", languageName='" + languageName + '\'' +
                ", languageImage='" + languageImage +
                '}';
    }
}