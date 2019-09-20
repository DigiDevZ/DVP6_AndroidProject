package com.example.projectremnant.Character.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projectremnant.DataModels.Character;
import com.example.projectremnant.R;

public class CharacterFormFragment extends Fragment {

    private EditText mGamertag;
    private EditText mPlatform;
    private EditText mTraitrank;
    private EditText mNickname;

    private OnSaveTapped mListener;
    public interface OnSaveTapped {
        void saveTapped(Character _character);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof OnSaveTapped) {
            mListener = (OnSaveTapped) context;
        }
    }

    public static CharacterFormFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CharacterFormFragment fragment = new CharacterFormFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character_form_layout, container, false);

        mGamertag = view.findViewById(R.id.et_gamertag);
        mPlatform = view.findViewById(R.id.et_platform);
        mNickname = view.findViewById(R.id.et_nickname);
        mTraitrank = view.findViewById(R.id.et_traitrank);

        return view;
    }

    //TODO: Create the menu for this view and make sure the menu from the host activity is hidden.

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void saveCharacter() {
        String gamertag = mGamertag.getText().toString();
        String platform = mPlatform.getText().toString();
        String nickname = mNickname.getText().toString();
        String traitrank = mTraitrank.getText().toString();

        //TODO: Check the character data model.
        Character character = new Character();
        mListener.saveTapped(character);
    }
}
