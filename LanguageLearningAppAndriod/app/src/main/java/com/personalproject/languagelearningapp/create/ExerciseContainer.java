package com.personalproject.languagelearningapp.create;

import com.personalproject.languagelearningapp.authentication.Account;

public class ExerciseContainer {
    private Topic topic;
    private ExerciseType exerciseType;
    private Exercise exercise;
    private Account creator;

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Account getCreator() {
        return creator;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }


}