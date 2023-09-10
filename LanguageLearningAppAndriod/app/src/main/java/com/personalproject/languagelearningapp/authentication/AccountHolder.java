package com.personalproject.languagelearningapp.authentication;

import android.content.SharedPreferences;
import com.personalproject.languagelearningapp.common.HttpUtils;

import java.sql.Timestamp;

public class AccountHolder {
    public static final String accountFileSharePreferenceKey = "local.account.stored.info";
    public static final String accountSharePreferenceKey = "local.account.stored.info.account";

    public static Integer accountId;
    public static Account currentAccount;

    public static void onChange(Account account, SharedPreferences sharedPreferences) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(accountSharePreferenceKey, HttpUtils.gson.toJson(account));
        accountId = account.getId();
        replaceAccount(account);
        edit.apply();
    }

    public static Account getAccount() {
        return currentAccount;
    }

    public static void replaceAccount(Account account) {
        currentAccount = account;
    }

    public static boolean needLogin() {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        return currentTime.after(currentAccount.getTokenExpire());
    }
}
