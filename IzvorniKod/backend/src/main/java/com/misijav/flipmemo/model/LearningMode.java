package com.misijav.flipmemo.model;

public enum LearningMode {
    ORIGINAL_TRANSLATED("Original word with multiple choice of translated words"),
    TRANSLATED_ORIGINAL("Translated word with multiple choice of original words"),
    AUDIO_RESPONSE("Original audio with original word user input"),
    ORIGINAL_AUDIO("Original word with original word audio input from user");

    LearningMode(String description) {
    }
}
