package com.example.projectremnant.Sessions.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projectremnant.R;

public class SessionFormFragment extends Fragment {


    private EditText mSessionName;
    private EditText mSessionDescription;
    private EditText mSessionLimit;

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
        mSessionLimit = view.findViewById(R.id.et_playerLimit);

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


        //mListener.createTapped();
    }

}
