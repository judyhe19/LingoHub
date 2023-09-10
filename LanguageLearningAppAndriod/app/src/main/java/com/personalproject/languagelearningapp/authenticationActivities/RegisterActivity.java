package com.personalproject.languagelearningapp.authenticationActivities;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.personalproject.languagelearningapp.R;
import com.personalproject.languagelearningapp.authentication.Account;
import com.personalproject.languagelearningapp.authentication.AccountHolder;
import com.personalproject.languagelearningapp.authentication.AccountHttpUtils;
import org.apache.commons.lang3.StringUtils;

import static com.personalproject.languagelearningapp.authentication.AccountHolder.accountFileSharePreferenceKey;

public class RegisterActivity extends AppCompatActivity {
    String userType = "Learner";
    Boolean accountNull = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMain();
            }
        });

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(v);
            }
        });

        Button registerLoginButton = findViewById(R.id.registerLoginButton);
        registerLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afterRegister();
            }
        });

        ToggleButton userTypeToggleButton = findViewById(R.id.userTypeToggleButton);
        userTypeToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled --> Educator
                    userType = "Educator";
                } else {
                    // The toggle is disabled --> Learner
                    userType = "Learner";
                }
            }
        });
    }
    /** Called when the user taps the Send button */
    public void register(View view) {
        EditText usernameEditText = findViewById(R.id.registerUsername);
        EditText emailEditText = findViewById(R.id.registerEmail);
        EditText passwordEditText = findViewById(R.id.registerPassword);
        EditText confirmPasswordEditText = findViewById(R.id.confirmPassword);
        TextView errorTextView = findViewById(R.id.registerErrorTextView);

        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (StringUtils.isBlank(username) || StringUtils.isBlank(email) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
            errorTextView.setText("Please fill all fields.");
            return;
        }
        if (!password.equalsIgnoreCase(confirmPassword)) {
            errorTextView.setText("PASSWORDS DO NOT MATCH: Please try again.");
            passwordEditText.getText().clear();
            confirmPasswordEditText.getText().clear();
            return;
        }

        registerCall(username, password, email);
        if (accountNull == true) {
            errorTextView.setText("Error or Email Already Exists: Please try again or log in.");
            passwordEditText.getText().clear();
            confirmPasswordEditText.getText().clear();
            return;
        }
    }

    private void registerCall(String username, String password, String email) {
        new Thread(() -> {
            Account account = new Account();
            account.setUsername(username);
            account.setPassword(password);
            account.setEmail(email);
            account.setAccountType(userType);
            Account registerAccount = AccountHttpUtils.register(account);
            if (registerAccount != null) {
                AccountHolder.onChange(registerAccount, getApplicationContext().getSharedPreferences(accountFileSharePreferenceKey, Context.MODE_PRIVATE));
                afterRegister();
            }
            else {
                accountNull = true;
            }
        }).start();
    }

    private void afterRegister() {
        runOnUiThread(() -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }

    public void returnToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

//public class RegisterActivity extends AppCompatActivity {
//
//    String userType = "";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//        Intent intent = getIntent();
//    }
//
//    public void registerUser(View view) {
//        Intent intent = new Intent(this, MainActivity.class);
//        EditText username = findViewById(R.id.enterUsername);
//        String usernameString = username.getText().toString();
//        intent.putExtra("username", usernameString);
//
//        EditText email = findViewById(R.id.enterEmail);
//        String emailString = email.getText().toString();
//        intent.putExtra("email", emailString);
//
//        ToggleButton toggleButton = findViewById(R.id.userTypeToggleButton);
//        String userTypeString = toggleButton.getText().toString();
//        intent.putExtra("userType", userTypeString);
//
//        EditText password = findViewById(R.id.enterPassword);
//        String passwordString = password.getText().toString();
//        EditText confirmPassword = findViewById(R.id.confirmPassword);
//        String confirmPasswordString = password.getText().toString();
//
//        if (passwordString.equals(confirmPasswordString)) {
//            intent.putExtra("password", passwordString);
//            startActivity(intent);
//        }
//        else {
//            TextView textView = findViewById(R.id.errorTextView);
//            textView.setText("Passwords do not match!");
//        }
//
//    }
//
//}
