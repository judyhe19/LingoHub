package com.personalproject.languagelearningapp.create;

import android.content.Context;
import android.content.Intent;
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
import com.personalproject.languagelearningapp.common.ResultResponse;
import com.personalproject.languagelearningapp.learner.LearnerHomeActivity;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.personalproject.languagelearningapp.authentication.AccountHolder.accountFileSharePreferenceKey;

public class CreateGrammarExercise extends AppCompatActivity {
    ArrayList<Topic> topics = new ArrayList<>();
    ArrayList<String> topicsNames = new ArrayList<>();
    ArrayList<ExerciseType> exerciseTypes = new ArrayList<>();
    ArrayList<String> exerciseTypeNames = new ArrayList<>();
    Account account = AccountHolder.getAccount();
    String selectedTopic;
    String selectedType;
    Boolean MC = true;
    Boolean exerciseNull = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_grammar_exercise);
        topicsNames.add(0, "Choose");
        exerciseTypeNames.add(0, "Choose");

        View answerEditText = findViewById(R.id.answerTextField);
        answerEditText.setVisibility(View.GONE);


        Button returnHomeButton = findViewById(R.id.grammarReturnHomeButton);
        returnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnHome();
            }
        });
        Button createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createExercise(account);
            }
        });
//        if (TopicHolder.getTopics() == null || TypeHolder.getTypes() == null) {
//            new Thread(() -> {
//                try{
//                    topics = GrammarExerciseHttpUtils.getTopics(account);
//                    exerciseTypes = GrammarExerciseHttpUtils.getTypes(account);
//                    for (int i = 0; i < topics.length; i++) {
//                        topicsNames.add(topics[i].getName());
//                    }
//                    for (int i = 0; i < exerciseTypes.length; i++) {
//                        exerciseTypeNames.add(exerciseTypes[i].getName());
//                    }
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//            }).start();
//        }
//        else {
        topics = TopicHolder.getTopics();
        exerciseTypes = TypeHolder.getTypes();
        for (int i = 0; i < topics.size(); i++) {
            if (!topicsNames.contains(topics.get(i).getName())) {
                topicsNames.add(topics.get(i).getName());
            }

        }
        for (int i = 0; i < exerciseTypes.size(); i++) {
            if (!exerciseTypeNames.contains(exerciseTypes.get(i).getName())) {
                exerciseTypeNames.add(exerciseTypes.get(i).getName());
            }
        }
//        }

        Spinner topicSpinner = findViewById(R.id.topicSpinner);
        Spinner typeSpinner = findViewById(R.id.typeSpinner);
//        while (topicsNames == null) {
        ArrayAdapter<String> topicSpinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, topicsNames); //selected item will look like a spinner set from XML
        topicSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        topicSpinner.setAdapter(topicSpinnerArrayAdapter);
//        }

//        while (exerciseTypeNames == null) {
            ArrayAdapter<String> typeSpinnerArrayAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item, exerciseTypeNames); //selected item will look like a spinner set from XML
            typeSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeSpinner.setAdapter(typeSpinnerArrayAdapter);
//        }

        topicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                selectedTopic = topicSpinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                selectedType = typeSpinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        Switch MCSwitch = findViewById(R.id.MCSwitch);
        MCSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    MC = true;
                    View answerEditText = findViewById(R.id.answerTextField);
                    answerEditText.setVisibility(View.GONE);

                    View choiceAEditText = findViewById(R.id.choiceATextField);
                    View choiceAText = findViewById(R.id.choiceAText);
                    choiceAEditText.setVisibility(View.VISIBLE);
                    choiceAText.setVisibility(View.VISIBLE);

                    View choiceBEditText = findViewById(R.id.choiceBTextField);
                    View choiceBText = findViewById(R.id.choiceBText);
                    choiceBEditText.setVisibility(View.VISIBLE);
                    choiceBText.setVisibility(View.VISIBLE);

                    View choiceCEditText = findViewById(R.id.choiceCTextField);
                    View choiceCText = findViewById(R.id.choiceCText);
                    choiceCText.setVisibility(View.VISIBLE);
                    choiceCEditText.setVisibility(View.VISIBLE);

                    View choiceDEditText = findViewById(R.id.choiceDTextField);
                    View choiceDText = findViewById(R.id.choiceDText);
                    choiceDText.setVisibility(View.VISIBLE);
                    choiceDEditText.setVisibility(View.VISIBLE);

                    View checkA = findViewById(R.id.AcheckBox);
                    checkA.setVisibility(View.VISIBLE);
                    View checkB = findViewById(R.id.BcheckBox);
                    checkB.setVisibility(View.VISIBLE);
                    View checkC = findViewById(R.id.CcheckBox);
                    checkC.setVisibility(View.VISIBLE);
                    View checkD = findViewById(R.id.DcheckBox);
                    checkD.setVisibility(View.VISIBLE);

                } else {
                    // The toggle is disabled
                    MC = false;
                    View answerEditText = findViewById(R.id.answerTextField);
                    answerEditText.setVisibility(View.VISIBLE);

                    View choiceAEditText = findViewById(R.id.choiceATextField);
                    View choiceAText = findViewById(R.id.choiceAText);
                    choiceAEditText.setVisibility(View.GONE);
                    choiceAText.setVisibility(View.GONE);

                    View choiceBEditText = findViewById(R.id.choiceBTextField);
                    View choiceBText = findViewById(R.id.choiceBText);
                    choiceBEditText.setVisibility(View.GONE);
                    choiceBText.setVisibility(View.GONE);

                    View choiceCEditText = findViewById(R.id.choiceCTextField);
                    View choiceCText = findViewById(R.id.choiceCText);
                    choiceCText.setVisibility(View.GONE);
                    choiceCEditText.setVisibility(View.GONE);

                    View choiceDEditText = findViewById(R.id.choiceDTextField);
                    View choiceDText = findViewById(R.id.choiceDText);
                    choiceDText.setVisibility(View.GONE);
                    choiceDEditText.setVisibility(View.GONE);

                    View checkA = findViewById(R.id.AcheckBox);
                    checkA.setVisibility(View.GONE);
                    View checkB = findViewById(R.id.BcheckBox);
                    checkB.setVisibility(View.GONE);
                    View checkC = findViewById(R.id.CcheckBox);
                    checkC.setVisibility(View.GONE);
                    View checkD = findViewById(R.id.DcheckBox);
                    checkD.setVisibility(View.GONE);

                }
            }
        });

    }

    public void createExercise(Account account) {
        String topicName = selectedTopic;
        String typeName = selectedType;
        String answer;
        Topic topic = new Topic();
        ExerciseType type = new ExerciseType();
        topic.setName(topicName);
        type.setName(typeName);

        ExerciseContainer exerciseContainer = new ExerciseContainer();
        exerciseContainer.setCreator(account);
        Switch MCSwitch = findViewById(R.id.MCSwitch);
        Spinner topicSpinner = findViewById(R.id.topicSpinner);
        Spinner typeSpinner = findViewById(R.id.typeSpinner);
        EditText newTopicEditText = findViewById(R.id.newTopicTextField);
        EditText newTypeEditText = findViewById(R.id.newTypeTextField);
        EditText questionEditText = findViewById(R.id.questionTextField);
        EditText answerEditText = findViewById(R.id.answerTextField);
        EditText choiceAEditText = findViewById(R.id.choiceATextField);
        EditText choiceBEditText = findViewById(R.id.choiceBTextField);
        EditText choiceCEditText = findViewById(R.id.choiceCTextField);
        EditText choiceDEditText = findViewById(R.id.choiceDTextField);
        CheckBox ACheckBox = findViewById(R.id.AcheckBox);
        CheckBox BCheckBox = findViewById(R.id.BcheckBox);
        CheckBox CCheckBox = findViewById(R.id.CcheckBox);
        CheckBox DCheckBox = findViewById(R.id.DcheckBox);
        TextView errorTextView = findViewById(R.id.createErrorTextView);
        String newTopic = newTopicEditText.getText().toString();
        String newType = newTypeEditText.getText().toString();
        String question = questionEditText.getText().toString();

        Exercise exercise = new Exercise();
        exercise.setQuestion(question);
        exercise.setMultipleChoice(MC);
        if (MC == true) {
            String AText = choiceAEditText.getText().toString();
            String BText = choiceBEditText.getText().toString();
            String CText = choiceCEditText.getText().toString();
            String DText = choiceDEditText.getText().toString();

            exercise.setChoiceA(AText);
            exercise.setChoiceB(BText);
            exercise.setChoiceC(CText);
            exercise.setChoiceD(DText);

            if (ACheckBox.isChecked()) {answer = "A";}
            else if (BCheckBox.isChecked()) {answer = "B";}
            else if (CCheckBox.isChecked()) {answer = "C";}
            else if (DCheckBox.isChecked()) {answer = "D";}
            else {
                errorTextView.setText("Answer not set.");
                return;
            }
        }
        else {
            answer = answerEditText.getText().toString();
            if (StringUtils.isBlank(answer)) {
                errorTextView.setText("Answer not written.");
                return;
            }
        }

        if (!StringUtils.isBlank(newTopic)) {
            topicName = newTopic;
        }
        if (!StringUtils.isBlank(newType)) {
            typeName = newType;
        }

        exercise.setAnswer(answer);
        exerciseContainer.setExercise(exercise);
        createExerciseCall(exerciseContainer, topicName, typeName);
        if (exerciseNull) {
            errorTextView.setText("Exercise Creation: Unsuccessful");
        }
        else {
            questionEditText.getText().clear();
            answerEditText.getText().clear();
            newTopicEditText.getText().clear();
            newTypeEditText.getText().clear();
            choiceAEditText.getText().clear();
            choiceBEditText.getText().clear();
            choiceCEditText.getText().clear();
            choiceDEditText.getText().clear();
            ACheckBox.setChecked(false);
            BCheckBox.setChecked(false);
            CCheckBox.setChecked(false);
            DCheckBox.setChecked(false);
            MCSwitch.setChecked(true);
            topicSpinner.setSelection(0);
            typeSpinner.setSelection(0);
        }

        returnHome();

    }

    private void createExerciseCall(ExerciseContainer exerciseContainer, String topicName, String typeName) {
        new Thread(() -> {
            ExerciseContainer ec = GrammarExerciseHttpUtils.createExercise(exerciseContainer, topicName, typeName);
            if (ec != null) {
                exerciseNull = false;
                Log.d("exercise", ec.getExercise().getQuestion());
                Log.d("type", ec.getExerciseType().getName());
                Log.d("topic", ec.getTopic().getName());

                ExerciseHolder.onEditExercises(ec.getExercise(), getApplicationContext().getSharedPreferences(accountFileSharePreferenceKey, Context.MODE_PRIVATE));
                if (!topicsNames.contains(ec.getTopic().getName())) {
                    TopicHolder.onEditTopics(ec.getTopic(), getApplicationContext().getSharedPreferences(accountFileSharePreferenceKey, Context.MODE_PRIVATE));
                }
                if (!exerciseTypeNames.contains(ec.getExerciseType().getName())) {
                    TypeHolder.onEditTypes(ec.getExerciseType(), getApplicationContext().getSharedPreferences(accountFileSharePreferenceKey, Context.MODE_PRIVATE));
                }
                return;
            }
            else {
                exerciseNull = true;
            }
        }).start();
    }

    private void returnHome() {
        Intent intent = new Intent(this, LearnerHomeActivity.class);
        startActivity(intent);
    }

    private void returnCreateMain() {
        Intent intent = new Intent(this, MainCreateExercise.class);
        startActivity(intent);
    }
}