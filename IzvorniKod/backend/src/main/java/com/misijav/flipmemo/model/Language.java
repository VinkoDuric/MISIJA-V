package com.misijav.flipmemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
public class Language {
    @Id
    private String langCode;

    @NotEmpty
    private String languageName;

    private String languageImage;

    @OneToMany(mappedBy = "dictLang")
    private List<Dictionary> dictionaries;  // < YEP

    protected Language() {}

    public Language(String langCode, String languageName, String languageImage) {
        this.languageName = languageName;
        this.languageImage = languageImage;
        this.langCode = langCode;
    }

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
