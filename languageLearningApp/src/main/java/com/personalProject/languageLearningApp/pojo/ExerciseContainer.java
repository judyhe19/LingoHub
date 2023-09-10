package com.personalProject.languageLearningApp.pojo;

import com.personalProject.languageLearningApp.domain.Account;
import com.personalProject.languageLearningApp.domain.Exercise;
import com.personalProject.languageLearningApp.domain.ExerciseType;
import com.personalProject.languageLearningApp.domain.Topic;
import lombok.Data;

@Data
public class ExerciseContainer {
    private Topic topic;
    private ExerciseType exerciseType;
    private Exercise exercise;
    private Account creator;
}
