package com.example.projectremnant.Sessions;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectremnant.Checklist.ChecklistActivity;
import com.example.projectremnant.R;
import com.example.projectremnant.Sessions.Fragments.SessionFormFragment;

public class SessionActivity extends AppCompatActivity implements SessionFormFragment.OnCreateTapped {

    private static final String TAG = "SessionActivity.TAG";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_activity);

        Intent starter = getIntent();
        if(starter != null) {
            String searchOption = starter.getStringExtra(ChecklistActivity.EXTRA_OPTION);
            if(searchOption.equals("search")){
                //TODO: Launch the available sessions lists fragments.
            }else if(searchOption.equals("create")){
                //Launch the session form fragment.
                launchFormFragment();
            }
        }
    }

    private void launchFormFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, SessionFormFragment.newInstance())
                .commit();
    }

    /**
     * Interface method for the session form fragment.
     */

    @Override
    public void createTapped(String _sessionName, String _sessionDescription, String _playerLimit) {
        Log.i(TAG, "createTapped: session being created.");
    }
}
