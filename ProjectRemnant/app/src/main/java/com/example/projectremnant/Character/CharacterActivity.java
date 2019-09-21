package com.example.projectremnant.Character;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.projectremnant.Character.Fragments.CharacterFormFragment;
import com.example.projectremnant.Checklist.ChecklistActivity;
import com.example.projectremnant.DataModels.Character;
import com.example.projectremnant.DataModels.User;
import com.example.projectremnant.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CharacterActivity extends AppCompatActivity implements CharacterFormFragment.OnSaveTapped{

    private static final String TAG = "CharacterActivity.TAG";
    
    public static final String EXTRA_USER = "EXTRA_USER";

    private static final String TAG_FORM_FRAGMENT = "FormFragment";

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

    private User mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_activity);

        Intent starter = getIntent();
        if(starter != null) {
            //Get the user account passed in from the signup activity or the login activity.
            mUser = (User) starter.getSerializableExtra(EXTRA_USER);
            Log.i(TAG, "onCreate: intent not null");
        }

        //TODO: Create gridview fragment and functionality.
        //TODO: If the user contains characters, then load the grid view.
//        String characters = mUser.getUserCharacters();
//        if(characters.equals("Empty") || characters.isEmpty()){
//            //Load the text view stating there are no characters and to make one.
//        }else {
//            //Turn the characters String into a JSON Object and decode it.
//        }
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
            //Launch the fragment to add a character.
            loadCharacterFormFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //If the Form Fragment is up, back out of that first.
        if(getSupportFragmentManager().findFragmentByTag(TAG_FORM_FRAGMENT) != null) {
            getSupportFragmentManager().popBackStack("CharacterFormFragment",
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
            setTitle("Characters");
        }else {
            super.onBackPressed();
        }

    }

    private void loadCharacterFormFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, CharacterFormFragment.newInstance(), TAG_FORM_FRAGMENT)
                .addToBackStack("CharacterFormFragment")
                .commit();
        setTitle("Character Creation Form");
    }

    private void intentToChecklist() {
        Intent starter = new Intent(this, ChecklistActivity.class);
        starter.putExtra(EXTRA_USER, mUser);
        startActivity(starter);
    }

    /**
     * Interface methods for the Character Form Fragment.
     */

    @Override
    public void saveTapped(Character _character) {
        //Turn the character info into a JSON Object string and then add it to the user and update the user account.
        mUser.updateUserCharacters( _character.toJSONString());
        Log.i(TAG, "saveTapped: character json: " + _character.toJSONString());
        //TODO: Update the users account.
        mDatabase.child(mUser.mUserName).child("mUserCharacters").setValue(_character.toJSONString()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //For this build just intent to the checklist activity.
                Toast.makeText(getApplicationContext(), "Character Created, sending you to the checklist", Toast.LENGTH_LONG).show();
                intentToChecklist();
            }
        });
    }
}
