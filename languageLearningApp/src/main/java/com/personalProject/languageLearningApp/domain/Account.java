package com.personalProject.languageLearningApp.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Account {
    private Integer id;
    private String username;
    private String password;
    private String token;
    private String email;
    private Timestamp tokenExpire;
    private String accountType;
    private String language;
}
