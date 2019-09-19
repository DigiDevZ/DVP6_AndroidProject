package com.example.projectremnant.Sessions;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectremnant.Checklist.ChecklistActivity;
import com.example.projectremnant.R;

public class SessionActivity extends AppCompatActivity {



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
                //TODO: Launch the session form fragment.
            }
        }
    }
}
