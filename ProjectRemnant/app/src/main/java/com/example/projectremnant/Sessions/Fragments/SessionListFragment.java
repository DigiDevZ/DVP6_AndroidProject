package com.example.projectremnant.Sessions.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.projectremnant.DataModels.Session;
import com.example.projectremnant.DataModels.User;
import com.example.projectremnant.R;
import com.example.projectremnant.Sessions.Adapter.SessionAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SessionListFragment extends ListFragment {

    private static final String TAG = "SessionListFragment.TAG";
    
    private static final String ARG_JOINED = "joined";
    private static final String ARG_USER = "user";

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("sessions");

    private User mUser;

    private ArrayList<Session> mSessions = new ArrayList<>();

    private SessionListFragmentListener mListener;
    public interface SessionListFragmentListener {
        void sessionClicked(Session _session);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof SessionListFragmentListener) {
            mListener = (SessionListFragmentListener)context;
        }
    }

    public static SessionListFragment newInstance(boolean _joined, User _user) {
        
        Bundle args = new Bundle();
        args.putBoolean(ARG_JOINED, _joined);
        args.putSerializable(ARG_USER, _user);

        SessionListFragment fragment = new SessionListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sessionlist_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mUser = (User) (getArguments() != null ? getArguments().getSerializable(ARG_USER) : null);

        boolean joined = (getArguments() != null ? getArguments().getBoolean(ARG_JOINED) : null);
        if(joined) {
            Log.i(TAG, "onActivityCreated: looking for joined groups: " + joined);
            ArrayList<String> joinedSessions = mUser.getJoinedSessions();
            getSessionsFromJoinedSessionsIds(joinedSessions);
        }else {
            Log.i(TAG, "onActivityCreated: looking for joined groups: " + joined);
            //Todo get all the sessions in the sessions part of the database.
            getSessionsFromAvailableSessions();
        }
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mListener.sessionClicked(mSessions.get(position));
    }

    private void updateList() {
        SessionAdapter sa = new SessionAdapter(getContext(), mSessions);
        setListAdapter(sa);
    }

    private void getSessionsFromJoinedSessionsIds(ArrayList<String> _sessionIds) {

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String sessionDetails = dataSnapshot.getValue(String.class);
                //Create the session based off of the details and then remove the listener, by using the id.
                Session session = Session.fromJSONString(sessionDetails);
                if(session != null) {
                    Log.i(TAG, "onDataChange: adding session to list.");
                    mSessions.add(session);
                    updateList();
                    mDatabase.child(session.getSessionId()).removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        //Getting whatever session is joined and then making it for the list view.
        for (int i = 0; i < _sessionIds.size(); i++) {
            mDatabase.child(_sessionIds.get(i)).addValueEventListener(listener);
        }
    }

    private void getSessionsFromAvailableSessions() {

        ValueEventListener sessionListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    //The child now holds the session details.
                    String sessionDetails = child.getValue(String.class);
                    //Create the session from the details and then
                    Session session = Session.fromJSONString(sessionDetails);
                    if(session != null) {
                        mSessions.add(session);
                    }
                }
                updateList();
                mDatabase.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mDatabase.addValueEventListener(sessionListener);
    }
}
