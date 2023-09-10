package com.personalproject.languagelearningapp.authenticationActivities;

import android.content.Intent;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.internal.LinkedTreeMap;
import com.personalproject.languagelearningapp.R;

import android.content.Context;
import android.content.SharedPreferences;
import com.personalproject.languagelearningapp.authentication.Account;
import com.personalproject.languagelearningapp.authentication.AccountHolder;
import com.personalproject.languagelearningapp.authentication.AccountHttpUtils;
import com.personalproject.languagelearningapp.common.HttpUtils;
import com.personalproject.languagelearningapp.create.ExerciseType;
import com.personalproject.languagelearningapp.create.Topic;
import com.personalproject.languagelearningapp.create.TopicHolder;
import com.personalproject.languagelearningapp.create.TypeHolder;
import com.personalproject.languagelearningapp.educator.EducatorHomeActivity;
import com.personalproject.languagelearningapp.learner.LearnerHomeActivity;

import java.util.ArrayList;

import static com.personalproject.languagelearningapp.authentication.AccountHolder.accountFileSharePreferenceKey;
import static com.personalproject.languagelearningapp.authentication.AccountHolder.accountSharePreferenceKey;
import static com.personalproject.languagelearningapp.create.TopicHolder.topicIdSharePreferenceKey;
import static com.personalproject.languagelearningapp.create.TopicHolder.topicSharePreferenceKey;
import static com.personalproject.languagelearningapp.create.TypeHolder.typeIdSharePreferenceKey;
import static com.personalproject.languagelearningapp.create.TypeHolder.typeSharePreferenceKey;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doInitAuth();
            }
        });
    }

    private void doInitAuth() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(accountFileSharePreferenceKey, Context.MODE_PRIVATE);

        String accountString = sharedPreferences.getString(accountSharePreferenceKey, "{}");
        Account account = HttpUtils.gson.fromJson(accountString, Account.class);
        AccountHolder.onChange(account, sharedPreferences);
        if (account == null || account.getId() == null) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        } else {
            new Thread(() -> {
                Boolean authResult = AccountHttpUtils.doAuth(account);
                assert authResult != null;
                if (authResult) {
                    onLoginSuccess(account);
                } else {
                    onLoginFail();
                }
            }).start();
        }
    }

    private void onLoginSuccess(Account account) {
//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(accountFileSharePreferenceKey, Context.MODE_PRIVATE);
//
//        String topicsString = sharedPreferences.getString(topicSharePreferenceKey, "[]");
//        String topicIdsString = sharedPreferences.getString(topicIdSharePreferenceKey, "[]");
//        Log.d("topicString", topicsString);
//
//        ArrayList<LinkedTreeMap<Object, Object>> topicsMaps = HttpUtils.gson.fromJson(topicsString, ArrayList.class);
//        ArrayList<Integer> topicIdsMaps = HttpUtils.gson.fromJson(topicIdsString, ArrayList.class);
//
//        for (int i = 0; i < topicsMaps.size(); i++) {
//            Topic t = new Topic();
//            t.setCreateTime((String) topicsMaps.get(i).get("createTime"));
//            t.setName((String) topicsMaps.get(i).get("name"));
//            Double aId = (Double) topicsMaps.get(i).get("creatorAccountId");
//            Double id = (Double) topicsMaps.get(i).get("id");
//            t.setCreatorAccountId(aId.intValue());
//            t.setId(id.intValue());
//            TopicHolder.topics.add(t);
//        }
//
//        String typesString = sharedPreferences.getString(typeSharePreferenceKey, "[]");
//        String typeIdsString = sharedPreferences.getString(typeIdSharePreferenceKey, "[]");
//        Log.d("typeString", typeIdsString);
//
//        ArrayList<LinkedTreeMap<Object, Object>> typesMaps = HttpUtils.gson.fromJson(typesString, ArrayList.class);
//        ArrayList<LinkedTreeMap> typeIdsMaps = HttpUtils.gson.fromJson(typeIdsString, ArrayList.class);
//
//        for (int i = 0; i < typesMaps.size(); i++) {
//            ExerciseType et = new ExerciseType();
//            et.setCreateTime((String) typesMaps.get(i).get("createTime"));
//            et.setName((String) typesMaps.get(i).get("name"));
//            Double aId = (Double) topicsMaps.get(i).get("creatorAccountId");
//            Double id = (Double) topicsMaps.get(i).get("id");
//            et.setCreatorAccountId(aId.intValue());
//            et.setId(id.intValue());
//            TypeHolder.types.add(et);
//        }
        runOnUiThread(() -> {
            Intent intent;
            if (account.getAccountType().equalsIgnoreCase("Learner")) {
                intent = new Intent(this, LearnerHomeActivity.class);
            }
            else {
                intent = new Intent(this, EducatorHomeActivity.class);
            }
            startActivity(intent);

        });
    }

    private void onLoginFail() {
        runOnUiThread(() -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }
}


//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//
//    public void login(View view) {
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
//    }
//
//    public void register(View view) {
//        Intent intent = new Intent(this, RegisterActivity.class);
//        startActivity(intent);
//    }
//
//
//}