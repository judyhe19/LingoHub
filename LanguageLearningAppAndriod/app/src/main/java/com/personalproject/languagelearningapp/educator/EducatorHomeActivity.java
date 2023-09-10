package com.personalproject.languagelearningapp.educator;

import android.content.Intent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.personalproject.languagelearningapp.R;

import java.util.Date;

public class EducatorHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educator_home);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        TextView homeTitle = findViewById(R.id.educatorHomeTitle);
        TextView dateTimeDisplay = findViewById(R.id.educatorCurrentDateDisplay);
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        dateTimeDisplay.setText(currentDateTimeString);
        homeTitle.setText("Hello, " + username +"!");

    }
}