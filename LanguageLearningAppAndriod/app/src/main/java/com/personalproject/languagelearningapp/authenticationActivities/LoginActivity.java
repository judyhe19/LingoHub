package com.personalproject.languagelearningapp.authenticationActivities;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.personalproject.languagelearningapp.R;

import android.content.Context;
import android.widget.TextView;
import com.personalproject.languagelearningapp.authentication.Account;
import com.personalproject.languagelearningapp.authentication.AccountHolder;
import com.personalproject.languagelearningapp.authentication.AccountHttpUtils;
import com.personalproject.languagelearningapp.common.ResultResponse;
import com.personalproject.languagelearningapp.educator.EducatorHomeActivity;
import com.personalproject.languagelearningapp.learner.LearnerHomeActivity;
import org.apache.commons.lang3.StringUtils;

import static com.personalproject.languagelearningapp.authentication.AccountHolder.accountFileSharePreferenceKey;

public class LoginActivity extends AppCompatActivity {
    TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginRegisterButton = findViewById(R.id.loginRegisterButton);
        loginRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToAccountRegistration();
            }
        });

        Button forgotPasswordButton = findViewById(R.id.forgotPasswordButton);
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });
    }

    /** Called when the user taps the Send button */
    public void login(View view) {
        EditText emailEditText = (EditText) findViewById(R.id.loginEmail);
        EditText passwordEditText = (EditText) findViewById(R.id.loginPassword);
        TextView errorTextView = (TextView) findViewById(R.id.loginError);
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        Account account = AccountHolder.getAccount();
        if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
            errorTextView.setText("Please fill all fields.");
            return;
        }
        if (!account.getEmail().equalsIgnoreCase(email)) {
            account = AccountHttpUtils.getByEmail(email);
            if (account == null || account.getId() == null) {
                errorTextView.setText("Email not registered: Please register first!");
                emailEditText.getText().clear();
                passwordEditText.getText().clear();
                return;
            }
        }

        Account finalAccount = account;
        new Thread(() -> {
            try{
                finalAccount.setPassword(password);
                ResultResponse<String> login = AccountHttpUtils.login(finalAccount);
                if (login.getCode() == 0) {
                    finalAccount.setToken(login.getData());
                    AccountHolder.onChange(finalAccount, getApplicationContext().getSharedPreferences(accountFileSharePreferenceKey, Context.MODE_PRIVATE));
                    loginSuccess(finalAccount);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    private void loginSuccess(Account account) {
        runOnUiThread(() -> {
            Intent intent;
            if (account.getAccountType().equalsIgnoreCase("Learner")) {
                intent = new Intent(this, LearnerHomeActivity.class);
            }
            else {
                intent = new Intent(this, EducatorHomeActivity.class);
            }
            startActivity(intent);
        });
    }

    private void handleAccountNotExist() {
        runOnUiThread(this::jumpToAccountRegistration);
    }

    public void jumpToAccountRegistration() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void resetPassword() {
        Intent intent = new Intent(this, resetPasswordActivity.class);
        startActivity(intent);
    }
}

//public class LoginActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        Intent intent = getIntent();
//
//    }
//
//    public void loginUser(View view) {
//        Intent intent = new Intent(this, LoginActivity.class);
//        EditText email = findViewById(R.id.enterEmail);
//        String emailString = email.getText().toString();
//        intent.putExtra("email", emailString);
//
//        EditText password = findViewById(R.id.enterPassword);
//        String passwordString = password.getText().toString();
//        intent.putExtra("password", passwordString);
//
//        startActivity(intent);
//    }
//
//    public void forgotPassword(View view) {
//        Intent intent = new Intent(this, resetPasswordActivity.class);
//        startActivity(intent);
//    }
//
//    public void onRegister(View view) {
//        Intent intent = new Intent(this, RegisterActivity.class);
//        startActivity(intent);
//    }
//
