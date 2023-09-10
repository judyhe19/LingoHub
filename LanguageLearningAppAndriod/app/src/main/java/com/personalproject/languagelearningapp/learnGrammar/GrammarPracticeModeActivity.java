package com.personalproject.languagelearningapp.learnGrammar;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.personalproject.languagelearningapp.R;
import com.personalproject.languagelearningapp.learner.LearnerHomeActivity;
import org.apache.commons.lang3.StringUtils;

public class GrammarPracticeModeActivity extends AppCompatActivity {
    String selectedTopicName = "";
    String selectedTypeName = "";
    Boolean practiceByTime = false;
    Boolean practiceByQuestionN = false;
    Boolean practiceByCorrectN = false;
    String valueEntered = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar_practice_mode);
        selectedTopicName = getIntent().getStringExtra("ExerciseTopic");
        selectedTypeName = getIntent().getStringExtra("ExerciseType");

        Button modeReturnHomeButton = findViewById(R.id.modeReturnHomeButton);
        modeReturnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnHome();
            }
        });

        Button startPracticeButton = findViewById(R.id.startPracticeButton);
        startPracticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPractice();
            }
        });

    }

    private void startPractice() {
        EditText timeModeEditText = findViewById(R.id.timeTextField);
        EditText questionNModeEditText = findViewById(R.id.questionNumberTextField);
        EditText correctNModeEditText = findViewById(R.id.correctNumberTextField);

        TextView errorTextView = findViewById(R.id.modeErrorTextView);
        String timeMode = timeModeEditText.getText().toString();
        String questionNMode = questionNModeEditText.getText().toString();
        String correctNMode = correctNModeEditText.getText().toString();

        if (!StringUtils.isBlank(timeMode) && !StringUtils.isBlank(questionNMode) ||
                !StringUtils.isBlank(timeMode) && !StringUtils.isBlank(correctNMode) ||
                !StringUtils.isBlank(questionNMode) && !StringUtils.isBlank(correctNMode)) {
            errorTextView.setText("ERROR: Please only choose one practice mode!");
            return;
        }
        else if (StringUtils.isBlank(timeMode) && StringUtils.isBlank(questionNMode) && StringUtils.isBlank(correctNMode)){
            errorTextView.setText("ERROR: Please choose a practice mode!");
            return;
        }
        else if (!StringUtils.isBlank(timeMode) && StringUtils.isBlank(questionNMode) && StringUtils.isBlank(correctNMode)){
            practiceByTime = true;
            valueEntered = timeMode;
        }
        else if (StringUtils.isBlank(timeMode) && !StringUtils.isBlank(questionNMode) && StringUtils.isBlank(correctNMode)){
            practiceByQuestionN = true;
            valueEntered = questionNMode;
        }
        else if (StringUtils.isBlank(timeMode) && StringUtils.isBlank(questionNMode) && !StringUtils.isBlank(correctNMode)){
            practiceByCorrectN = true;
            valueEntered = correctNMode;
        }

        jumpToPracticeSession();
    }

    private void jumpToPracticeSession() {
        Intent intent = new Intent(this, PracticeSessionActivity.class);
        intent.putExtra("ExerciseTopic",selectedTopicName);
        intent.putExtra("ExerciseType",selectedTypeName);
        intent.putExtra("practiceByTime",practiceByTime);
        intent.putExtra("practiceByQuestionN",practiceByQuestionN);
        intent.putExtra("practiceByCorrectN",practiceByCorrectN);
        intent.putExtra("valueEntered", valueEntered);
        startActivity(intent);
    }

    private void returnHome() {
        Intent intent = new Intent(this, LearnerHomeActivity.class);
        startActivity(intent);
    }
}