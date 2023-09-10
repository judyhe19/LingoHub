package com.personalproject.languagelearningapp.create;


import android.util.Log;
import com.google.gson.reflect.TypeToken;
import com.personalproject.languagelearningapp.authentication.Account;
import com.personalproject.languagelearningapp.common.HttpUtils;
import com.personalproject.languagelearningapp.common.ResultResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GrammarExerciseHttpUtils {

    private static ArrayList<Exercise> getExercises(String response) {
        Type type = new TypeToken<ResultResponse<ArrayList<Exercise>>>(){}.getType();
        ResultResponse<ArrayList<Exercise>> resultResponse = HttpUtils.gson.fromJson(response, type);
        if (resultResponse.getData() == null) {
            return null;
        }
        Log.d("EXERCISE 0 QUESTION", resultResponse.getData().get(0).getQuestion());
        return resultResponse.getData();
    }

    private static Topic[] getTopics(String response) {
        Type type = new TypeToken<ResultResponse<Topic[]>>(){}.getType();
        ResultResponse<Topic[]> resultResponse = HttpUtils.gson.fromJson(response, type);
        if (resultResponse.getData() == null) {
            return null;
        }
        return resultResponse.getData();
    }

    private static ExerciseType[] getExerciseTypes(String response) {
        Type type = new TypeToken<ResultResponse<ExerciseType[]>>(){}.getType();
        ResultResponse<ExerciseType[]> resultResponse = HttpUtils.gson.fromJson(response, type);
        if (resultResponse.getData() == null) {
            return null;
        }
        return resultResponse.getData();
    }

    private static ExerciseContainer createExercise(String response) {
        Type type = new TypeToken<ResultResponse<ExerciseContainer>>(){}.getType();
        ResultResponse<ExerciseContainer> resultResponse = HttpUtils.gson.fromJson(response, type);
        if (resultResponse.getData() == null) {
            return null;
        }
        return resultResponse.getData();
    }

    private static String getString(String response) {
        Type type = new TypeToken<ResultResponse<String>>(){}.getType();
        ResultResponse<String> resultResponse = HttpUtils.gson.fromJson(response, type);
        return resultResponse.getData();
    }

    private static Boolean getBoolean(String response) {
        Type type = new TypeToken<ResultResponse<Boolean>>(){}.getType();
        ResultResponse<Boolean> resultResponse = HttpUtils.gson.fromJson(response, type);
        return resultResponse.getData();
    }

    public static Topic[] getTopics(Account account) {
        String s = HttpUtils.postRequest("/exercise/info/allTopics/", account);
        Log.d("STRING RETURNED", s);

        return getTopics(s);
    }

    public static ExerciseType[] getTypes(Account account) {
        String s = HttpUtils.postRequest("/exercise/info/allTypes/", account);
        Log.d("STRING RETURNED", s);

        return getExerciseTypes(s);
    }

    public static ExerciseContainer createExercise(ExerciseContainer exerciseContainer, String topic, String type) {
        String s = HttpUtils.postRequest("/exercise/add/" + topic +"/"+type, exerciseContainer);
        Log.d("STRING RETURNED", s);
        return createExercise(s);
    }

    public static ArrayList<Exercise> findExercises(Integer topic, Integer type) {
        String s = HttpUtils.getRequest("/exercise/findExercises/" + topic +"/"+type);
        Log.d("STRING RETURNED", s);
        return getExercises(s);
    }


//    public static Account getByAccountId(Integer accountId) {
//        String s = HttpUtils.getRequest("/account/info/id/" + accountId);
//        return getAccount(s);
//    }
//
//    public static Account getByUsername(String username) {
//        String s = HttpUtils.getRequest("/account/info/username/" + username);
//        return getAccount(s);
//    }
//
//    public static Account getByEmail(String email) {
//        String s = HttpUtils.getRequest("/account/info/email/" + email);
//        return getAccount(s);
//    }
//
//    public static String loginAndGetToken(Account account) {
//        String s = HttpUtils.postRequest("/auth/login", account);
//        return getString(s);
//    }
//
//    public static ResultResponse<String> login(Account account) {
//        String s = HttpUtils.postRequest("/auth/login", account);
//        Type type = new TypeToken<ResultResponse<String>>(){}.getType();
//        return HttpUtils.gson.fromJson(s, type);
//    }
//
//    public static Boolean resetPassword(Account account) {
//        String s = HttpUtils.postRequest("/account/resetPassword/" + account.getPassword(), account);
//        return getBoolean(s);
//    }
//
//    public static Boolean updateLanguage(Account account) {
//        String s = HttpUtils.postRequest("/account/updateLanguage/" + account.getLanguage(), account);
//        return getBoolean(s);
//    }
//
//    public static Boolean doAuth(Account account) {
//        String s = HttpUtils.postRequest("/auth/doAuth", account);
//        return getBoolean(s);
//    }
}
