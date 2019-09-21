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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item = menu.findItem(R.id.character_menu_add);
        if(item != null) {
            item.setVisible(false);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.character_form_save_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.character_form_menu_save) {
            saveCharacter();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveCharacter() {
        String gamertag = mGamertag.getText().toString();
        String platform = mPlatform.getText().toString();
        String nickname = mNickname.getText().toString();
        String traitrank = mTraitrank.getText().toString();

        //Create the character and send it back to the activity.
        Character character = new Character(traitrank, gamertag, nickname, platform);
        mListener.saveTapped(character);
    }
}
