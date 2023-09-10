package com.personalproject.languagelearningapp.authenticationActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.personalproject.languagelearningapp.R;
import com.personalproject.languagelearningapp.authentication.Account;
import com.personalproject.languagelearningapp.authentication.AccountHolder;
import com.personalproject.languagelearningapp.authentication.AccountHttpUtils;

import static com.personalproject.languagelearningapp.authentication.AccountHolder.accountFileSharePreferenceKey;

public class resetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button resetReturnButton = findViewById(R.id.resetReturnButton);
        resetReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMain();
            }
        });

    }

    public void resetPassword(View view) {
        EditText resetPasswordEmailEditText = findViewById(R.id.resetPasswordEmail);
        EditText resetPasswordEditText = findViewById(R.id.resetPassword);
        EditText confirmResetPasswordEditText = findViewById(R.id.confirmResetPassword);
        TextView errorTextView = (TextView)findViewById(R.id.resetPasswordError);
        String resetPasswordEmail = resetPasswordEmailEditText.getText().toString();
        String resetPassword = resetPasswordEditText.getText().toString();
        String confirmResetPassword = confirmResetPasswordEditText.getText().toString();

        Account account = AccountHolder.getAccount();
        if (account == null || !account.getEmail().equalsIgnoreCase(resetPasswordEmail)) {
            errorTextView.setText("Email not found: Please try again or register first.");
            resetPasswordEditText.getText().clear();
            confirmResetPasswordEditText.getText().clear();
            return;

        }
        if (!resetPassword.equalsIgnoreCase(confirmResetPassword)) {
            errorTextView.setText("NEW PASSWORDS DO NOT MATCH: Please try again.");
            resetPasswordEditText.getText().clear();
            confirmResetPasswordEditText.getText().clear();
            return;
        }

        Account finalAccount;
        if (account == null) {
            finalAccount = AccountHttpUtils.getByEmail(resetPasswordEmail);
        }
        else {
            finalAccount = account;
        }
        new Thread(() -> {
            try{
                finalAccount.setPassword(resetPassword);
                Boolean resetResult = AccountHttpUtils.resetPassword(finalAccount);
                if (resetResult == true) {
                    Account updatedAccount = AccountHttpUtils.getByEmail(finalAccount.getEmail());
                    AccountHolder.onChange(updatedAccount, getApplicationContext().getSharedPreferences(accountFileSharePreferenceKey, Context.MODE_PRIVATE));
                    resetSuccess();
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    private void resetSuccess() {
        runOnUiThread(() -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }

    public void returnToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


//        Intent intent = new Intent(this, LoginActivity.class);
//        EditText email = findViewById(R.id.enterEmail);
//        String emailString = email.getText().toString();
//        intent.putExtra("email", emailString);
//
//
//        EditText password = findViewById(R.id.enterPassword);
//        String passwordString = password.getText().toString();
//
//        EditText confirmPassword = findViewById(R.id.confirmPassword);
//        String confirmPasswordString = password.getText().toString();
//
//        if (passwordString.equals(confirmPasswordString)) {
//            intent.putExtra("newPassword", passwordString);
//            startActivity(intent);
//
//        }
//        else {
//            TextView textView = findViewById(R.id.errorTextView);
//            textView.setText("Passwords do not match!");
//        }

}

