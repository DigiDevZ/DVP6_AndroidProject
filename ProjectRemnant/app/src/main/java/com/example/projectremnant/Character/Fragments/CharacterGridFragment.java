package com.example.projectremnant.Character.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projectremnant.Character.Adapter.CharacterAdapter;
import com.example.projectremnant.DataModels.Character;
import com.example.projectremnant.DataModels.User;
import com.example.projectremnant.R;

import java.util.ArrayList;

public class CharacterGridFragment extends Fragment {

    private static final String TAG = "CharacterGFragment.TAG";

    private static final String ARG_CHARACTERS = "characters";

    private GridView mGridView;
    private ArrayList<Character> mCharacters = new ArrayList<>();

    public static CharacterGridFragment newInstance(String _characters) {
        
        Bundle args = new Bundle();
        args.putString(ARG_CHARACTERS, _characters);
        
        CharacterGridFragment fragment = new CharacterGridFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private CharacterFragmentListener mListener;
    public interface CharacterFragmentListener {
        void characterTapped(Character _character, int _key);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof CharacterFragmentListener) {
            mListener = (CharacterFragmentListener)context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_character_grid_layout, container, false);
        mGridView = view.findViewById(R.id.gv_characters);
        return view;
    }

    //TODO: Needs to update the grid from the character activity and close on save tapped.

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Get the characters string and turn it into an array list of characters.
        String characters = (getArguments() != null ? getArguments().getString(ARG_CHARACTERS) : null);
        if(characters != null) {
            mCharacters = User.getUserCharactersAsArrayList(characters);
            Log.i(TAG, "onActivityCreated: size: " + mCharacters.size());

            CharacterAdapter ca = new CharacterAdapter(getActivity(), mCharacters);
            mGridView.setAdapter(ca);
            //Need click listener and to attach interface to activity.
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.i(TAG, "onItemClick: Character selected: " + mCharacters.get(position).getNickname());
                    mListener.characterTapped(mCharacters.get(position), position + 1);
                }
            });
        }
    }


}

