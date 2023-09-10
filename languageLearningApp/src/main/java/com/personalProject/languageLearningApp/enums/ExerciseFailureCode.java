package com.personalProject.languageLearningApp.enums;

import lombok.Getter;

public enum ExerciseFailureCode {
    CREATION_FAILURE(1, "Exercise Creation Failed, please try again"),
    NO_VALID_EXERCISE(2, "Exercise not found, please try again or choose a new exercise type");

    @Getter
    int code;
    @Getter
    String message;

    ExerciseFailureCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
