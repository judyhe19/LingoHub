package com.personalproject.languagelearningapp.create;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.personalproject.languagelearningapp.R;
import com.personalproject.languagelearningapp.learner.LearnerHomeActivity;

public class MainCreateExercise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_create_exercise);

        Button grammarCreateButton = findViewById(R.id.grammarCreateButton);
        grammarCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToCreateGrammarExercise();
            }
        });

        Button returnHomeButton = findViewById(R.id.returnHomeButton);
        returnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnHome();
            }
        });


    }

    private void returnHome() {
        Intent intent = new Intent(this, LearnerHomeActivity.class);
        startActivity(intent);
    }

    private void jumpToCreateGrammarExercise() {
        Intent intent = new Intent(this, CreateGrammarExercise.class);
        startActivity(intent);
    }


}