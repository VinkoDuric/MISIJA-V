package com.misijav.flipmemo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
public class LearningMode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long learningModeId;

    private String learningModeDescription;

    // TODO maybe add Enum? or convert this whole class to Enum??

    private LearningMode() {}

    public LearningMode(String learningModeDescription) {
        this.learningModeDescription = learningModeDescription;
    }

    public Long getLearningModeId() {
        return learningModeId;
    }

    public String getLearningModeDescription() {
        return learningModeDescription;
    }

    public void setLearningModeDescription(String learningModeDescription) {
        this.learningModeDescription = learningModeDescription;
    }
}