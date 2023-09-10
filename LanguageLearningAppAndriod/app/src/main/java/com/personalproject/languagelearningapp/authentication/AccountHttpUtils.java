package com.personalproject.languagelearningapp.authentication;


import android.util.Log;
import com.google.gson.reflect.TypeToken;
import com.personalproject.languagelearningapp.common.HttpUtils;
import com.personalproject.languagelearningapp.common.ResultResponse;

import java.lang.reflect.Type;

public class AccountHttpUtils {

    private static Account getAccount(String response) {
        Type type = new TypeToken<ResultResponse<Account>>(){}.getType();
        ResultResponse<Account> resultResponse = HttpUtils.gson.fromJson(response, type);
        if (resultResponse.getData() == null) {
            return null;
        }
        Log.d("ACCOUNT RETURNED", resultResponse.getData().getEmail());
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

    public static Account register(Account account) {
        String s = HttpUtils.postRequest("/account/register", account);
        Log.d("STRING RETURNED", s);

        return getAccount(s);
    }

    public static Account getByAccountId(Integer accountId) {
        String s = HttpUtils.getRequest("/account/info/id/" + accountId);
        return getAccount(s);
    }

    public static Account getByUsername(String username) {
        String s = HttpUtils.getRequest("/account/info/username/" + username);
        return getAccount(s);
    }

    public static Account getByEmail(String email) {
        String s = HttpUtils.getRequest("/account/info/email/" + email);
        return getAccount(s);
    }

    public static String loginAndGetToken(Account account) {
        String s = HttpUtils.postRequest("/auth/login", account);
        return getString(s);
    }

    public static ResultResponse<String> login(Account account) {
        String s = HttpUtils.postRequest("/auth/login", account);
        Type type = new TypeToken<ResultResponse<String>>(){}.getType();
        return HttpUtils.gson.fromJson(s, type);
    }

    public static Boolean resetPassword(Account account) {
        String s = HttpUtils.postRequest("/account/resetPassword/" + account.getPassword(), account);
        return getBoolean(s);
    }

    public static Boolean updateLanguage(Account account) {
        String s = HttpUtils.postRequest("/account/updateLanguage/" + account.getLanguage(), account);
        return getBoolean(s);
    }

    public static Boolean doAuth(Account account) {
        String s = HttpUtils.postRequest("/auth/doAuth", account);
        return getBoolean(s);
    }
}
