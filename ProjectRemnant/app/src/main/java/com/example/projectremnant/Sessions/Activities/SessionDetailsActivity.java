package com.example.projectremnant.Sessions.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectremnant.Character.CharacterActivity;
import com.example.projectremnant.DataModels.Character;
import com.example.projectremnant.DataModels.Session;
import com.example.projectremnant.DataModels.User;
import com.example.projectremnant.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;

public class SessionDetailsActivity extends AppCompatActivity {

    private static final String TAG = "SessionDetailsAct.TAG";

    public static final String EXTRA_SESSION = "session";

    private Session mSession;
    private Character mCharacter;
    private User mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_details_activity);

        Intent starter = getIntent();
        if(starter != null) {
            mUser = (User) starter.getSerializableExtra(CharacterActivity.EXTRA_USER);
            mCharacter = (Character) starter.getSerializableExtra(CharacterActivity.EXTRA_CHARACTER);
            mSession = (Session) starter.getSerializableExtra(EXTRA_SESSION);
            updateUI();

            Button btn_join = findViewById(R.id.btn_joinSession);
            btn_join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    //Add the session id to the users joined sessions and then add the users character to the session.
                    String characters = mSession.getSessionCharacters();

                    Log.i(TAG, "onClick: before json array");
                    try {
                        JSONArray characterArray = new JSONArray(characters);
                        characterArray.put(mCharacter.getCharacterForSession());
                        Log.i(TAG, "onClick: Character array: " + characterArray.toString());
                        //Need to update database now.
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("sessions").child(mSession.getSessionId());


                        Session session = new Session(mSession.getSessionName(), mSession.getSessionLimit(), characterArray.toString(), mSession.getSessionDescription(), mSession.getSessionId());
                        String sessionDetails = session.getSessionDetails();

                        ref.setValue(sessionDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //Dirty way to stop the user but it is needed for now.
                                v.setClickable(false);
                            }
                        });
                        mSession = session;
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mUser.updateJoinedSessions(mSession.getSessionId());
                    Log.i(TAG, "onClick: " + mUser.getJoinedSessionsIds());
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUserName()).child("joinedSessionsIds");
                    userRef.setValue(mUser.getJoinedSessionsIds()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.i(TAG, "onSuccess: success");
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent finish = new Intent();
        finish.putExtra(CharacterActivity.EXTRA_USER, mUser);
        finish.putExtra(CharacterActivity.EXTRA_CHARACTER, mCharacter);
        setResult(RESULT_OK, finish);
        this.finishActivity(SessionActivity.REQUEST_SESSIONDETAILS);
        super.onBackPressed();
    }

    private void updateUI() {
        TextView tv_name = findViewById(R.id.tv_name);
        TextView tv_limit = findViewById(R.id.tv_limit);
        TextView tv_description = findViewById(R.id.tv_description);

        tv_name.setText(mSession.getSessionName());
        tv_description.setText(mSession.getSessionDescription());
        tv_limit.setText(String.valueOf(mSession.getSessionLimit()));
    }



}
