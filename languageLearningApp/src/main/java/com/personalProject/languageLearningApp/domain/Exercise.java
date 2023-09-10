package com.personalProject.languageLearningApp.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Exercise {
    private Integer id;
    private String question;
    private String answer;
    private Boolean multipleChoice;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;

    private Integer creatorAccountId;
    private Timestamp createTime;
}
