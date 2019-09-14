package com.example.projectremnant;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

    private void loginTapped() {

        //TODO: Check for the user with the users name and then validate that that user has the same password.
        String userName = mEt_userName.getText().toString();
        final String password = mEt_password.getText().toString();

        mDatabase.child(userName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    //User exists

                    String userPass = dataSnapshot.child("mUserPass").getValue(String.class);
                    Log.i(TAG, "onDataChange: password: " + userPass);

                    if(password.equals(userPass)) {
                        //TODO: Launch the intent to the home screen.
                        Log.i(TAG, "onDataChange: login succesful.");
                    }
                }
                else {
                    //Toast that account was not found with that username.
                    Toast.makeText(getApplicationContext(), "No account associated with that username.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //End of loginTapped.
    }



}
