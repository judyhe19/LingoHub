package com.personalproject.languagelearningapp.learnGrammar;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.personalproject.languagelearningapp.R;
import com.personalproject.languagelearningapp.learner.LearnerHomeActivity;

public class GrammarPracticeMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar_practice_main);

        Button customPracticeButton = findViewById(R.id.customPracticeButton);
        customPracticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToCustomExercise();
            }
        });

        Button practiceMainReturnHomeButton = findViewById(R.id.practiceMainReturnHomeButton);
        practiceMainReturnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnHome();
            }
        });
    }

    private void jumpToCustomExercise() {
        Intent intent = new Intent(this, CustomGrammarPracticeTopicsActivity.class);
        startActivity(intent);
    }

    private void returnHome() {
        Intent intent = new Intent(this, LearnerHomeActivity.class);
        startActivity(intent);
    }
}