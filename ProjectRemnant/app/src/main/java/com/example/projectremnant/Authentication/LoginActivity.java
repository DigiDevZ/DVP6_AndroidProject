package com.example.projectremnant.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectremnant.Character.CharacterActivity;
import com.example.projectremnant.Checklist.ChecklistActivity;
import com.example.projectremnant.DataModels.User;
import com.example.projectremnant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity.TAG";
    
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

    private EditText mEt_userName;
    private EditText mEt_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        linkEditTexts();
        setOnClickListeners();
    }

    private void linkEditTexts() {
        mEt_userName = findViewById(R.id.et_username);
        mEt_password = findViewById(R.id.et_password);
    }

    private void setOnClickListeners() {
        Button btn_create = findViewById(R.id.btn_login);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginTapped();
            }
        });
    }


    //TODO: Have to reupdate the keys for the login and sign in process ASAP
    //Check for the user with the entered username and then validate that, that user has the same password.
    private void loginTapped() {

        final String userName = mEt_userName.getText().toString();
        final String password = mEt_password.getText().toString();

        ValueEventListener loginListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    //User exists
                    String userPass = dataSnapshot.child("userPass").getValue(String.class);
                    Log.i(TAG, "onDataChange: password: " + userPass);

                    if(password.equals(userPass)) {
                        //Launch the intent to the checklist screen.
                        Log.i(TAG, "onDataChange: login succesful.");

                        //Remember to fix this after testing.

                        long id = dataSnapshot.child("userId").getValue(Long.class);
                        String name = dataSnapshot.child("userName").getValue(String.class);
                        String characters = dataSnapshot.child("userCharacters").getValue(String.class);
                        String pass = dataSnapshot.child("userPass").getValue(String.class);
                        String joinedSessionIds = dataSnapshot.child("joinedSessionsIds").getValue(String.class);
                        //TODO: Grab the session ids from database.

                        User user = new User(name, pass, characters, id, joinedSessionIds);

                        Intent i = new Intent(getApplicationContext(), CharacterActivity.class);
                        i.putExtra(CharacterActivity.EXTRA_USER, user);
                        startActivity(i);
                        //Need to remove the listener after intenting to the next activity.
                        mDatabase.child(userName).removeEventListener(this);
                    }else {
                        //Toast that login failed, either username or password is wrong.
                        Log.i(TAG, "onDataChange: login failed, password expected: " + password + " found: " + userPass);
                        Toast.makeText(getApplicationContext(), R.string.login_activity_toast_loginFailed, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    //Toast that account was not found with that username.
                    Toast.makeText(getApplicationContext(), R.string.login_activity_toast_accountNotFound, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Access to the database is down. Try again later.
            }
        };

        //Attach the listener.
        mDatabase.child(userName).addValueEventListener(loginListener);
        //End of loginTapped.
    }



}
