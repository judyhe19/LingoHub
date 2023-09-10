package com.personalProject.languageLearningApp.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Relation {
    private Integer id;
    private String fromResource;
    private Integer fromResourceId;
    private String toResource;
    private Integer toResourceId;
}
