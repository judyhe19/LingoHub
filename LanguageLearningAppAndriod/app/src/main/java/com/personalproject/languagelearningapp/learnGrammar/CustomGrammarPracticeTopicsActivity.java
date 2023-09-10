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
import com.personalproject.languagelearningapp.create.MainCreateExercise;
import com.personalproject.languagelearningapp.create.Topic;
import com.personalproject.languagelearningapp.create.TopicHolder;
import com.personalproject.languagelearningapp.learner.LearnerHomeActivity;

import java.util.ArrayList;

public class CustomGrammarPracticeTopicsActivity extends AppCompatActivity {
    ArrayList<Topic> topics = new ArrayList<>();
    ArrayList<String> topicsNames = new ArrayList<>();
    String chosenTopic = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_grammar_practice_topics);

        topics = TopicHolder.getTopics();
        for (int i = 0; i < topics.size(); i++) {
            if (!topicsNames.contains(topics.get(i).getName())) {
                topicsNames.add(topics.get(i).getName());
            }

        }

        ListView topicsList = findViewById(R.id.topicsList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                topicsNames);
        topicsList.setAdapter(arrayAdapter);

        // register onClickListener to handle click events on each item
        topicsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // argument position gives the index of item which is clicked
                String selectedTopic = topicsNames.get(position);
                jumpToChooseTypes(selectedTopic);
            }
        });

        Button chooseTopicReturnHomeButton = findViewById(R.id.chooseTopicReturnHomeButton);
        chooseTopicReturnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnHome();
            }
        });
    }

    private void jumpToChooseTypes(String chosenTopic) {
        Intent intent = new Intent(this, CustomGrammarPracticeTypesActivity.class);
        intent.putExtra("ExerciseTopic",chosenTopic);
        startActivity(intent);
    }

    private void returnHome() {
        Intent intent = new Intent(this, LearnerHomeActivity.class);
        startActivity(intent);
    }
}