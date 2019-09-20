package com.example.projectremnant.Character;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectremnant.DataModels.User;
import com.example.projectremnant.R;

public class CharacterActivity extends AppCompatActivity {

    public static final String EXTRA_USER = "EXTRA_USER";

    private User mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_activity);

        Intent starter = getIntent();
        if(starter != null) {
            //Get the user account passed in from the signup activity or the login activity.
            mUser = (User) starter.getSerializableExtra(EXTRA_USER);
        }

        //TODO: If the user contains characters, then load the grid view.
        String characters = mUser.getUserCharacters();
        if(characters.equals("Empty") || characters.isEmpty()){
            //Load the text view stating there are no characters and to make one.
        }else {
            //Turn the characters String into a JSON Object and decode it.
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.character_add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.character_menu_add) {
            //TODO: Launch the fragment to add a character.
        }

        return super.onOptionsItemSelected(item);
    }


}
