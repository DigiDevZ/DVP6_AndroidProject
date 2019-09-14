package com.example.projectremnant.Authentication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectremnant.DataModels.User;
import com.example.projectremnant.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity.TAG";

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    private long mUserCount = 0;

    //NOTE: Need to use actual testing device when working with firebase database.

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        setOnClickListeners();
    }


    private void setOnClickListeners() {
        Button btn_create = findViewById(R.id.btn_login);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {

        EditText et_pass = findViewById(R.id.et_password);
        String password = et_pass.getText().toString();

        EditText et_confirmPass = findViewById(R.id.et_confirmPassword);
        String confirmPassword = et_confirmPass.getText().toString();

        EditText et_userName = findViewById(R.id.et_username);
        String userName = et_userName.getText().toString();

        boolean validPassword = password.equals(confirmPassword);
        if(validPassword) {
            updateUserCount();

            User newUser = new User(userName, password, "Empty", mUserCount);
            Log.i(TAG, "createAccount: users: " + mUserCount);

            //On successful user creation, update the user count and then intent to the home screen.
            mDatabase.child("users").child(userName).setValue(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    mDatabase.child("userCount").setValue(mUserCount+1);
                    //TODO: Intent to the home screen.

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                    //Failed to make account try again.
                }
            });
        }else {
            //Toast for passwords not matching.
            et_pass.setText("");
            et_confirmPass.setText("");
            Toast.makeText(this, R.string.signup_activity_toast_passwordsNotMatching, Toast.LENGTH_SHORT).show();
        }
    }

    //TODO: Need to add a utility class that encodes and decodes the string password for storing in the database.

    private void updateUserCount() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long userCount = dataSnapshot.child("userCount").getValue(Long.class);
                if(userCount != null) {
                    mUserCount = userCount;
                    Log.i(TAG, "onDataChange: user count: " + mUserCount);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
