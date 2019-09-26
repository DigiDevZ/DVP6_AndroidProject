package com.example.projectremnant.Sessions.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projectremnant.R;

public class SessionFormFragment extends Fragment {

    private static final String TAG = "SessionFormFragment";

    private EditText mSessionName;
    private EditText mSessionDescription;
    private String mPlayerLimitSelected;

    private OnCreateTapped mListener;
    public interface OnCreateTapped{
        void createTapped(String _sessionName, String _sessionDescription, String _playerLimit);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof OnCreateTapped) {
            mListener = (OnCreateTapped) context;
        }
    }

    public static SessionFormFragment newInstance() {

        Bundle args = new Bundle();

        SessionFormFragment fragment = new SessionFormFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_session_form_layout, container,false);

        mSessionName = view.findViewById(R.id.et_sessionName);
        mSessionDescription = view.findViewById(R.id.et_sessionDescription);
        Spinner mSessionLimit = view.findViewById(R.id.spinner_playerLimit);
        mSessionLimit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = "";
                if(position == 0){
                    value = "2";
                    mPlayerLimitSelected = "2";
                }else if(position == 1) {
                    value = "3";
                    mPlayerLimitSelected = "3";
                }
                Log.i(TAG, "onItemSelected: value of spinner: " + value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do nothing.
            }
        });

        Button btn_create = view.findViewById(R.id.btn_create);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSessionInfoFromViews();
            }
        });

        return view;
    }

    private void getSessionInfoFromViews() {

        String name = mSessionName.getText().toString();
        String description = mSessionDescription.getText().toString();

        mListener.createTapped(name, description, mPlayerLimitSelected);
    }

}
