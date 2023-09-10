package com.personalProject.languageLearningApp.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ExerciseType {
    private Integer id;
    private String name;
    private Integer creatorAccountId;
    private Timestamp createTime;
}
