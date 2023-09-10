package com.personalproject.languagelearningapp.create;

import android.content.SharedPreferences;
import com.personalproject.languagelearningapp.common.HttpUtils;

import java.util.ArrayList;

public class TypeHolder {
    public static final String accountFileSharePreferenceKey = "local.account.stored.info";
    public static final String typeSharePreferenceKey = "local.account.stored.info.types";
    public static final String typeIdSharePreferenceKey = "local.account.stored.info.typeIds";

    public static ArrayList<Integer> typeIds = new ArrayList<>();
    public static ArrayList<ExerciseType> types = new ArrayList<>();

    public static void onEditTypes(ExerciseType type, SharedPreferences sharedPreferences) {
        typeIds.add(type.getId());
        addType(type);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(typeSharePreferenceKey, HttpUtils.gson.toJson(types));
        edit.putString(typeIdSharePreferenceKey, HttpUtils.gson.toJson(typeIds));
        edit.apply();
    }

    public static ArrayList<ExerciseType> getTypes() {
        return types;
    }

    public static void addType(ExerciseType type) {
        types.add(type);
    }
}
