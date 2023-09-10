package com.personalproject.languagelearningapp.learnGrammar;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.personalproject.languagelearningapp.R;
import com.personalproject.languagelearningapp.authentication.Account;
import com.personalproject.languagelearningapp.authentication.AccountHolder;
import com.personalproject.languagelearningapp.create.ExerciseType;
import com.personalproject.languagelearningapp.create.TopicHolder;
import com.personalproject.languagelearningapp.create.TypeHolder;
import com.personalproject.languagelearningapp.learner.LearnerHomeActivity;

import java.util.ArrayList;

public class CustomGrammarPracticeTypesActivity extends AppCompatActivity {
    ArrayList<ExerciseType> exerciseTypes = new ArrayList<>();
    ArrayList<String> exerciseTypeNames = new ArrayList<>();
    String selectedTopicName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_grammar_practice_types);
        selectedTopicName = getIntent().getStringExtra("ExerciseTopic");
        exerciseTypes = TypeHolder.getTypes();
        for (int i = 0; i < exerciseTypes.size(); i++) {
            if (!exerciseTypeNames.contains(exerciseTypes.get(i).getName())) {
                exerciseTypeNames.add(exerciseTypes.get(i).getName());
            }
        }

        ListView typesList = findViewById(R.id.typesList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                exerciseTypeNames);
        typesList.setAdapter(arrayAdapter);

        // register onClickListener to handle click events on each item
        typesList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // argument position gives the index of item which is clicked
                String selectedType = exerciseTypeNames.get(position);
                jumpToCustomExerciseMode(selectedType);
            }
        });

        Button chooseTypeReturnHomeButton = findViewById(R.id.chooseTypeReturnHomeButton);
        chooseTypeReturnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnHome();
            }
        });

    }

    private void jumpToCustomExerciseMode(String selectedType) {
        Intent intent = new Intent(this, GrammarPracticeModeActivity.class);
        intent.putExtra("ExerciseTopic",selectedTopicName);
        intent.putExtra("ExerciseType",selectedType);
        startActivity(intent);
    }

    private void returnHome() {
        Intent intent = new Intent(this, LearnerHomeActivity.class);
        startActivity(intent);
    }
}