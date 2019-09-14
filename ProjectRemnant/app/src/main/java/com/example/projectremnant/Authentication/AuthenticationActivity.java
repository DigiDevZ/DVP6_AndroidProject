package com.example.projectremnant.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projectremnant.Authentication.LoginActivity;
import com.example.projectremnant.Authentication.SignupActivity;
import com.example.projectremnant.R;

public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_activity);

        setOnClickListeners();
    }

    private void setOnClickListeners() {
        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch the login process.
                loginTapped();
            }
        });

        Button btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch the sign up process.
                signupTapped();
            }
        });
    }

    private void loginTapped() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    private void signupTapped() {
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
    }


}
