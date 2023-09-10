package com.personalProject.languageLearningApp.exception;

public class ExerciseContainerException extends Exception {
    int code;
    String message;

    public ExerciseContainerException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
