package com.personalproject.languagelearningapp.learnGrammar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.personalproject.languagelearningapp.R;
import com.personalproject.languagelearningapp.authentication.Account;
import com.personalproject.languagelearningapp.authentication.AccountHolder;
import com.personalproject.languagelearningapp.create.*;
import com.personalproject.languagelearningapp.learner.LearnerHomeActivity;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Timer;

import static com.personalproject.languagelearningapp.authentication.AccountHolder.accountFileSharePreferenceKey;

public class PracticeSessionActivity extends AppCompatActivity {
    String selectedTopicName = "";
    String selectedTypeName = "";
    String modeValue = "";
    Boolean practiceByTime = false;
    Boolean practiceByQuestionN = false;
    Boolean practiceByCorrectN = false;
    Boolean exercisesNull = false;
    ArrayList<Exercise> exerciseList = new ArrayList<>();
    int questionCount = 1;
    int score = 0; // number correct
    long startTime = System.currentTimeMillis();
    Exercise currentExercise = new Exercise();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_session);
        selectedTopicName = getIntent().getStringExtra("ExerciseTopic");
        selectedTypeName = getIntent().getStringExtra("ExerciseType");
        practiceByTime = getIntent().getBooleanExtra("practiceByTime", false);
        practiceByQuestionN = getIntent().getBooleanExtra("practiceByQuestionN", false);
        practiceByCorrectN = getIntent().getBooleanExtra("practiceByCorrectN",false);
        modeValue = getIntent().getStringExtra("valueEntered");
        TextView timeLeftTextView = findViewById(R.id.timeLeftTextView);
        TextView topicTextView = findViewById(R.id.topicTextView);
        topicTextView.setText(selectedTopicName);


        ArrayList<Topic> topics = TopicHolder.getTopics();
        int topicId = -1;
        for (Topic topic : topics) {
            if (topic.getName().equalsIgnoreCase(selectedTopicName)) {
                topicId = topic.getId();
            }
        }
        ArrayList<ExerciseType> types = TypeHolder.getTypes();
        int typeId = -1;
        for (ExerciseType type : types) {
            if (type.getName().equalsIgnoreCase(selectedTypeName)) {
                typeId = type.getId();
            }
        }

        int finalTopicId = topicId;
        int finalTypeId = typeId;

//        Thread T1 = new Thread(new Runnable() {
//            public void run()
//            {
//                ArrayList<Exercise> exercises = GrammarExerciseHttpUtils.findExercises(finalTopicId, finalTypeId);
//                Log.d("Queried Exercises", String.valueOf(exercises.size()));
//                ArrayList<Exercise> savedExercises = ExerciseHolder.getExercises();
//                if (exercises != null) {
//                    for (Exercise exercise : exercises) {
//                        if (!exerciseList.contains(exercise) && savedExercises.contains(exercise)) {
//                            exerciseList.add(exercise);
//                        }
//                    }
//                }
//                else {
//                    exercisesNull = true;
//                }
//            }
//        });
//
//        T1.start();
//
//        try
//        {
//            T1.join();
//        }
//        catch (InterruptedException e)
//        {
//            System.out.println("Interrupt Occurred");
//            e.printStackTrace();
//        }

//        new Thread(() -> {
//            ArrayList<Exercise> exercises = GrammarExerciseHttpUtils.findExercises(finalTopicId, finalTypeId);
//            Log.d("Queried Exercises", String.valueOf(exercises.size()));
//            ArrayList<Exercise> savedExercises = ExerciseHolder.getExercises();
//            if (exercises != null) {
//                for (Exercise exercise : exercises) {
//                    if (!exerciseList.contains(exercise) && savedExercises.contains(exercise)) {
//                        exerciseList.add(exercise);
//                    }
//                }
//            }
//            else {
//                exercisesNull = true;
//            }
//        }).start();

        Button practiceSessionReturnHomeButton = findViewById(R.id.practiceSessionReturnHomeButton);
        practiceSessionReturnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnHome();
            }
        });

        Button checkAnswerORfinishButton = findViewById(R.id.checkAnswerORfinishButton);
        checkAnswerORfinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

        if (practiceByTime) {
            long timeLimit = Integer.valueOf(modeValue) * 60 * 1000;
            new CountDownTimer(timeLimit, 1000) {

                public void onTick(long millisUntilFinished) {
                    timeLeftTextView.setText(millisUntilFinished / 1000 / 60 + ":" + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    sessionFinished();
                    return;
                }
            }.start();
        }
        else {
            timeLeftTextView.setVisibility(View.GONE);
        }


        getExercisesList(finalTopicId, finalTypeId);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        while (exerciseList.size() == 0) {
            progressBar.setVisibility(View.VISIBLE);
        }
        progressBar.setVisibility(View.GONE);
        beginSession();

    }

    private void getExercisesList(Integer finalTopicId, Integer finalTypeId) {
        new Thread(() -> {
            ArrayList<Exercise> exercises = GrammarExerciseHttpUtils.findExercises(finalTopicId, finalTypeId);
            Log.d("Queried Exercises", String.valueOf(exercises.size()));
            ArrayList<Integer> exerciseIds = new ArrayList<>();
            ArrayList<Integer> savedIds = new ArrayList<>();
            if (exercises != null) {
                for (int i = 0; i < exercises.size(); i++) {
                    exerciseIds.add(exercises.get(i).getId());
                }
                ArrayList<Integer> duplicatedIds = findDuplicates(exerciseIds);
                for (Exercise exercise : exercises) {
                    if (duplicatedIds.contains(exercise.getId()) && !savedIds.contains(exercise.getId())) {
                        savedIds.add(exercise.getId());
                        exerciseList.add(exercise);
                    }
                }
            }
            else {
                exercisesNull = true;
            }
        }).start();
    }

    public ArrayList<Integer> findDuplicates(ArrayList<Integer> listContainingDuplicates) {
        final ArrayList<Integer> returnList = new ArrayList<>();
        final ArrayList<Integer> list1 = new ArrayList<>();

        for (Integer n : listContainingDuplicates) {
            if (list1.contains(n)) {
                returnList.add(n);
                Log.d("DUPLICATE FOUND", String.valueOf(n));
            }
            list1.add(n);
        }
        return returnList;
    }

    private void beginSession() {
        EditText answerEditTextTextMultiLine = findViewById(R.id.answerEditTextTextMultiLine);
        answerEditTextTextMultiLine.getText().clear();
        TextView questionCountTextView = findViewById(R.id.questionCountTextView);
        TextView questionORResultTextView = findViewById(R.id.questionORResultTextView);
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        TextView sessionChoiceAText = findViewById(R.id.sessionChoiceAText);
        TextView aTextView = findViewById(R.id.aTextView);
        TextView sessionChoiceBText = findViewById(R.id.sessionChoiceBText);
        TextView bTextView = findViewById(R.id.bTextView);
        TextView sessionChoiceCText = findViewById(R.id.sessionChoiceCText);
        TextView cTextView = findViewById(R.id.cTextView);
        TextView sessionChoiceDText = findViewById(R.id.sessionChoiceDText);
        TextView dTextView = findViewById(R.id.dTextView);

        questionCountTextView.setText("Question " + questionCount);
        scoreTextView.setText(String.valueOf(score));

        currentExercise = exerciseList.get(questionCount-1);
        questionORResultTextView.setText(currentExercise.getQuestion());

        if (!currentExercise.getMultipleChoice()) {
            sessionChoiceAText.setVisibility(View.GONE);
            aTextView.setVisibility(View.GONE);
            sessionChoiceBText.setVisibility(View.GONE);
            bTextView.setVisibility(View.GONE);
            sessionChoiceCText.setVisibility(View.GONE);
            cTextView.setVisibility(View.GONE);
            sessionChoiceDText.setVisibility(View.GONE);
            dTextView.setVisibility(View.GONE);
        }
        else {
            sessionChoiceAText.setVisibility(View.VISIBLE);
            aTextView.setVisibility(View.VISIBLE);
            aTextView.setText(currentExercise.getChoiceA());

            sessionChoiceBText.setVisibility(View.VISIBLE);
            bTextView.setVisibility(View.VISIBLE);
            bTextView.setText(currentExercise.getChoiceB());

            sessionChoiceCText.setVisibility(View.VISIBLE);
            cTextView.setVisibility(View.VISIBLE);
            cTextView.setText(currentExercise.getChoiceC());

            sessionChoiceDText.setVisibility(View.VISIBLE);
            dTextView.setVisibility(View.VISIBLE);
            dTextView.setText(currentExercise.getChoiceD());
        }



    }
    private void checkAnswer() {
        TextView answerFeedbackTextView = findViewById(R.id.answerFeedbackTextView);
        EditText answerEditTextTextMultiLine = findViewById(R.id.answerEditTextTextMultiLine);
        String answer = answerEditTextTextMultiLine.getText().toString();
        if(StringUtils.isBlank(answer)) {
            answerEditTextTextMultiLine.setText("No Answer Received!");
        }
        else {
            questionCount++;
            if (answer.equalsIgnoreCase(currentExercise.getAnswer())) {
                score++;
                answerFeedbackTextView.setText("LAST ANSWER: CORRECT");
                answerFeedbackTextView.setTextColor(Color.GREEN);
                if (practiceByCorrectN && score >= Integer.valueOf(modeValue)) {
                    sessionFinished();
                    return;
                }
                if (questionCount > exerciseList.size()) {
                    sessionFinished();
                    return;
                }
                if (practiceByQuestionN && questionCount > Integer.valueOf(modeValue)){
                    sessionFinished();
                    return;
                }
                beginSession();
            }
            else {
                answerFeedbackTextView.setText("LAST ANSWER: WRONG");
                answerFeedbackTextView.setTextColor(Color.RED);
                answerEditTextTextMultiLine.setText("WRONG");
                if (questionCount > exerciseList.size()) {
                    sessionFinished();
                    return;
                }
                if (practiceByQuestionN && questionCount > Integer.valueOf(modeValue)){
                    sessionFinished();
                    return;
                }
                beginSession();
            }
        }

    }
    private void sessionFinished() {
        Button checkAnswerORfinishButton = findViewById(R.id.checkAnswerORfinishButton);
        checkAnswerORfinishButton.setVisibility(View.GONE);

        EditText answerEditTextTextMultiLine = findViewById(R.id.answerEditTextTextMultiLine);
        answerEditTextTextMultiLine.setVisibility(View.GONE);
        TextView scoreTitle = findViewById(R.id.scoreTitle);
        scoreTitle.setVisibility(View.GONE);

        TextView answerFeedbackTextView = findViewById(R.id.answerFeedbackTextView);
        answerFeedbackTextView.setVisibility(View.GONE);
        TextView sessionChoiceAText = findViewById(R.id.sessionChoiceAText);
        TextView aTextView = findViewById(R.id.aTextView);
        TextView sessionChoiceBText = findViewById(R.id.sessionChoiceBText);
        TextView bTextView = findViewById(R.id.bTextView);
        TextView sessionChoiceCText = findViewById(R.id.sessionChoiceCText);
        TextView cTextView = findViewById(R.id.cTextView);
        TextView sessionChoiceDText = findViewById(R.id.sessionChoiceDText);
        TextView dTextView = findViewById(R.id.dTextView);
        sessionChoiceAText.setVisibility(View.GONE);
        aTextView.setVisibility(View.GONE);
        sessionChoiceBText.setVisibility(View.GONE);
        bTextView.setVisibility(View.GONE);
        sessionChoiceCText.setVisibility(View.GONE);
        cTextView.setVisibility(View.GONE);
        sessionChoiceDText.setVisibility(View.GONE);
        dTextView.setVisibility(View.GONE);

        TextView timeLeftTextView = findViewById(R.id.timeLeftTextView);
        timeLeftTextView.setVisibility(View.GONE);
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setVisibility(View.GONE);

        long estimatedTimeTaken = (System.currentTimeMillis() - startTime)/1000;
        TextView questionORResultTextView = findViewById(R.id.questionORResultTextView);
        TextView questionCountTextView = findViewById(R.id.questionCountTextView);
        questionCountTextView.setText("Practice Session Finished");
        questionORResultTextView.setText("TIME TAKEN: " + estimatedTimeTaken + "s"+ " | TOTAL # CORRECT: " + score);
    }

    private void returnHome() {
        Intent intent = new Intent(this, LearnerHomeActivity.class);
        startActivity(intent);
    }
}