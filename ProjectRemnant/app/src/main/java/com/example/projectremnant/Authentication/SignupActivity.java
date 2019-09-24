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

import com.example.projectremnant.Checklist.ChecklistActivity;
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

    //NOTE: Need to use actual testing device when working with fire base database.

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
                checkUserNameTaken();
            }
        });
    }

    private void createAccount() {

        EditText et_pass = findViewById(R.id.et_password);
        String password = et_pass.getText().toString();

        final EditText et_confirmPass = findViewById(R.id.et_confirmPassword);

        EditText et_userName = findViewById(R.id.et_username);
        String userName = et_userName.getText().toString();

        //Grab the confirm password and validate it.
        String confirmPassword = et_confirmPass.getText().toString();
        boolean validPassword = password.equals(confirmPassword);

        //If the user tries to make a username with the name admin, refuse them.
        if(userName.equals("admin")) {
            Toast.makeText(this, "Try again buddy. This name is taken.", Toast.LENGTH_SHORT).show();
            validPassword = false;
        }

        if(validPassword) {
            updateUserCount();
            User newUser = new User(userName, password, "Empty", mUserCount);
            createNewUserAccount(newUser);
            Log.i(TAG, "createAccount: users: " + mUserCount);
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

    private void createNewUserAccount(User _newUser) {
        //On successful user creation, update the user count and then intent to the home screen.
        mDatabase.child("users").child(_newUser.getUserName()).setValue(_newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabase.child("userCount").setValue(mUserCount+1);
                //Intent to the checklist screen.
                Intent i = new Intent(getApplicationContext(), ChecklistActivity.class);
                startActivity(i);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                //Failed to make account try again.
                //TODO: Toast, description one line above.
            }
        });
    }

    private void checkUserNameTaken() {

        final EditText et_userName = findViewById(R.id.et_username);
        final String userName = et_userName.getText().toString();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    //Username does not exist, system can create new account for user.
                    createAccount();
                    //Remove the event listener
                    mDatabase.child("users").child(userName).removeEventListener(this);
                }
                else{
                    //NOTE: Username taken bug should be fixed.
                    Log.i(TAG, "onDataChange: user name taken");
                    //Username already exists
                    Toast.makeText(getApplicationContext(), "Username taken", Toast.LENGTH_SHORT).show();
                    et_userName.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mDatabase.child("users").child(userName).addValueEventListener(listener);
    }



}
