package com.personalProject.languageLearningApp.exception;

import lombok.Getter;

@Getter
public class AuthException extends Exception {
    int code;
    String message;

    public AuthException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
