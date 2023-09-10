package com.personalProject.languageLearningApp.exception;

public class ExerciseException extends Exception {
    int code;
    String message;

    public ExerciseException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
