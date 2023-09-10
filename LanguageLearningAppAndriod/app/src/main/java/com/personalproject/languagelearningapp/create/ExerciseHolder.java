package com.personalproject.languagelearningapp.create;

import android.content.SharedPreferences;
import com.personalproject.languagelearningapp.authentication.Account;
import com.personalproject.languagelearningapp.common.HttpUtils;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ExerciseHolder {

    public static final String accountFileSharePreferenceKey = "local.account.stored.info";
    public static final String exerciseSharePreferenceKey = "local.account.stored.info.exercises";
    public static final String exerciseIdSharePreferenceKey = "local.account.stored.info.exerciseIds";

    public static ArrayList<Integer> exerciseIds = new ArrayList<>();
    public static ArrayList<Exercise> exercises  = new ArrayList<>();

    public static void onEditExercises(Exercise exercise, SharedPreferences sharedPreferences) {
        exerciseIds.add(exercise.getId());
        addExercise(exercise);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(exerciseSharePreferenceKey, HttpUtils.gson.toJson(exercises));
        edit.putString(exerciseIdSharePreferenceKey, HttpUtils.gson.toJson(exerciseIds));
        edit.apply();
    }

    public static ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public static void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

}
