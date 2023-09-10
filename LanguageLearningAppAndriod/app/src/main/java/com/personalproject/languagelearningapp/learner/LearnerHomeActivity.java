package com.personalproject.languagelearningapp.learner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.personalproject.languagelearningapp.R;
import com.personalproject.languagelearningapp.authentication.Account;
import com.personalproject.languagelearningapp.authentication.AccountHolder;
import com.personalproject.languagelearningapp.authentication.AccountHttpUtils;
import com.personalproject.languagelearningapp.authenticationActivities.RegisterActivity;
import com.personalproject.languagelearningapp.common.HttpUtils;
import com.personalproject.languagelearningapp.common.ResultResponse;
import com.personalproject.languagelearningapp.create.*;
import com.personalproject.languagelearningapp.learnGrammar.CustomGrammarPracticeTopicsActivity;

import java.lang.reflect.Type;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import static com.personalproject.languagelearningapp.authentication.AccountHolder.accountFileSharePreferenceKey;
import static com.personalproject.languagelearningapp.authentication.AccountHolder.accountSharePreferenceKey;
import static com.personalproject.languagelearningapp.create.ExerciseHolder.exerciseIdSharePreferenceKey;
import static com.personalproject.languagelearningapp.create.ExerciseHolder.exerciseSharePreferenceKey;
import static com.personalproject.languagelearningapp.create.TopicHolder.topicIdSharePreferenceKey;
import static com.personalproject.languagelearningapp.create.TopicHolder.topicSharePreferenceKey;
import static com.personalproject.languagelearningapp.create.TypeHolder.typeIdSharePreferenceKey;
import static com.personalproject.languagelearningapp.create.TypeHolder.typeSharePreferenceKey;

public class LearnerHomeActivity extends AppCompatActivity {
    Account account = AccountHolder.getAccount();
    ArrayList<String> languagesArray = new ArrayList<String>() {
        {
            add("Spanish");
            add("German");
            add("Greek");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learner_home);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(accountFileSharePreferenceKey, Context.MODE_PRIVATE);
        String topicsString = sharedPreferences.getString(topicSharePreferenceKey, "[]");
        String topicIdsString = sharedPreferences.getString(topicIdSharePreferenceKey, "[]");

        Log.d("topicString", topicsString);
        Log.d("topicIdsString", topicIdsString);

        ArrayList<LinkedTreeMap<Object, Object>> topicsMaps = HttpUtils.gson.fromJson(topicsString, ArrayList.class);
        ArrayList<Integer> topicIdsMaps = HttpUtils.gson.fromJson(topicIdsString, ArrayList.class);

        for (int i = 0; i < topicsMaps.size(); i++) {
            Topic t = new Topic();
            t.setCreateTime((String) topicsMaps.get(i).get("createTime"));
            t.setName((String) topicsMaps.get(i).get("name"));
            Double aId = (Double) topicsMaps.get(i).get("creatorAccountId");
            Double id = (Double) topicsMaps.get(i).get("id");
            t.setCreatorAccountId(aId.intValue());
            t.setId(id.intValue());
            TopicHolder.topics.add(t);
        }
        TopicHolder.topicIds = topicIdsMaps;

        String typesString = sharedPreferences.getString(typeSharePreferenceKey, "[]");
        String typeIdsString = sharedPreferences.getString(typeIdSharePreferenceKey, "[]");
        Log.d("typeString", typesString);
        Log.d("typeIdsString", typeIdsString);


        ArrayList<LinkedTreeMap<Object, Object>> typesMaps = HttpUtils.gson.fromJson(typesString, ArrayList.class);
        ArrayList<Integer> typeIdsMaps = HttpUtils.gson.fromJson(typeIdsString, ArrayList.class);

        for (int i = 0; i < typesMaps.size(); i++) {
            ExerciseType et = new ExerciseType();
            et.setCreateTime((String) typesMaps.get(i).get("createTime"));
            et.setName((String) typesMaps.get(i).get("name"));
            Double aId = (Double) typesMaps.get(i).get("creatorAccountId");
            Double id = (Double) typesMaps.get(i).get("id");
            et.setCreatorAccountId(aId.intValue());
            et.setId(id.intValue());
            TypeHolder.types.add(et);
        }
        TypeHolder.typeIds = typeIdsMaps;

        String exercisesString = sharedPreferences.getString(exerciseSharePreferenceKey, "[]");
        String exerciseIdsString = sharedPreferences.getString(exerciseIdSharePreferenceKey, "[]");
        Log.d("exerciseString", exercisesString);

        ArrayList<LinkedTreeMap<Object, Object>> exerciseMaps = HttpUtils.gson.fromJson(exercisesString, ArrayList.class);
        ArrayList<Integer> exerciseIdsMaps = HttpUtils.gson.fromJson(exerciseIdsString, ArrayList.class);

        for (int i = 0; i < exerciseMaps.size(); i++) {
            Exercise e = new Exercise();
            Double aId = (Double) exerciseMaps.get(i).get("creatorAccountId");
            Double id = (Double) exerciseMaps.get(i).get("id");
            e.setCreatorAccountId(aId.intValue());
            e.setId(id.intValue());
            e.setCreateTime((String) exerciseMaps.get(i).get("createTime"));
            e.setQuestion((String) exerciseMaps.get(i).get("question"));
            e.setAnswer((String) exerciseMaps.get(i).get("answer"));
            e.setMultipleChoice((Boolean) exerciseMaps.get(i).get("multipleChoice"));
            e.setChoiceA((String) exerciseMaps.get(i).get("choiceA"));
            e.setChoiceB((String) exerciseMaps.get(i).get("choiceB"));
            e.setChoiceC((String) exerciseMaps.get(i).get("choiceC"));
            e.setChoiceD((String) exerciseMaps.get(i).get("choiceD"));
            ExerciseHolder.exercises.add(e);
        }
        ExerciseHolder.exerciseIds = exerciseIdsMaps;

        TextView homeTitle = findViewById(R.id.learnerHomeTitle);
        TextView dateTimeDisplay = findViewById(R.id.learnerCurrentDateDisplay);
        TextView selectLanguage = findViewById(R.id.selectLanguage);
        Spinner spinner = findViewById(R.id.languageSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.languages_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        String currentDateTimeString = java.text.DateFormat.getDateInstance().format(new Date());
        dateTimeDisplay.setText(currentDateTimeString);
        homeTitle.setText("Hello, " + account.getUsername() +"!");


        if (account.getLanguage() == null) {
            selectLanguage.setText("Choose a language: ");
        }
        else {
            int i = languagesArray.indexOf(account.getLanguage());
            spinner.setSelection(i);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String selected = spinner.getSelectedItem().toString();
                account.setLanguage(selected);
                updateLanguage(account);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        Button createExerciseButton = findViewById(R.id.createExerciseButton);
        createExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToCreateExercise();
            }
        });

        Button grammarPracticeButton = findViewById(R.id.grammarPracticeButton);
        grammarPracticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                practiceGrammar();
            }
        });



    }

    private void updateLanguage(Account account) {
        new Thread(() -> {
            try{
                Boolean updateLanguage = AccountHttpUtils.updateLanguage(account);
                if (updateLanguage == true) {
                    AccountHolder.onChange(account, getApplicationContext().getSharedPreferences(accountFileSharePreferenceKey, Context.MODE_PRIVATE));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }).start();

    }

    private void jumpToCreateExercise() {
        Intent intent = new Intent(this, MainCreateExercise.class);
        startActivity(intent);
    }

    private void practiceGrammar() {
        Intent intent = new Intent(this, CustomGrammarPracticeTopicsActivity.class);
        startActivity(intent);
    }


}