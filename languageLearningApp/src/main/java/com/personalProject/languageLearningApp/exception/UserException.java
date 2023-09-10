package com.personalProject.languageLearningApp.exception;

import lombok.Getter;

@Getter
public class UserException extends Exception{
    int code;
    String message;

    public UserException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
