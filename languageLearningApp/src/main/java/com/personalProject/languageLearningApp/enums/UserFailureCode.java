package com.personalProject.languageLearningApp.enums;

import lombok.Getter;

public enum UserFailureCode {
    AUTH_FAILURE(1, "Authentication Failed, wrong username and password combination"),
    NO_VALID_ACCOUNT(2, "No valid account, please sign up"),
    ACCOUNT_EXISTS(3, "Email is already used");

    @Getter
    int code;
    @Getter
    String message;

    UserFailureCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
