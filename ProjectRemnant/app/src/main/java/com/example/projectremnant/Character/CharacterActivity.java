package com.example.projectremnant.Character;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.projectremnant.Character.Fragments.CharacterFormFragment;
import com.example.projectremnant.Character.Fragments.CharacterGridFragment;
import com.example.projectremnant.Checklist.ChecklistActivity;
import com.example.projectremnant.DataModels.Character;
import com.example.projectremnant.DataModels.User;
import com.example.projectremnant.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CharacterActivity extends AppCompatActivity implements CharacterFormFragment.OnSaveTapped, CharacterGridFragment.CharacterFragmentListener {

    //TODO: Character package is good to go and signed off for final review.

    private static final String TAG = "CharacterActivity.TAG";
    
    public static final String EXTRA_USER = "EXTRA_USER";
    public static final String EXTRA_CHARACTER = "EXTRA_CHARACTER";
    public static final String EXTRA_KEY = "EXTRA_KEY";

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

        if(mUser != null) {
            //If the user contains characters, then load the grid view.
            String characters = mUser.getUserCharacters();
            if(characters.equals("Empty") || characters.isEmpty()){
                //Load the text view stating there are no characters and to make one.
                TextView tv_empty = findViewById(R.id.tv_empty);
                tv_empty.setVisibility(View.VISIBLE);

                FrameLayout fl = findViewById(R.id.fragment_container);
                fl.setVisibility(View.GONE);
            }else {
                //Load the gridview
                loadCharacterGridFragment(characters);
            }
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

    private void hideEmptyDataWarning() {
        TextView tv_empty = findViewById(R.id.tv_empty);
        tv_empty.setVisibility(View.GONE);

        FrameLayout fl = findViewById(R.id.fragment_container);
        fl.setVisibility(View.VISIBLE);

    }
    private void loadCharacterFormFragment() {
        hideEmptyDataWarning();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, CharacterFormFragment.newInstance(), TAG_FORM_FRAGMENT)
                .addToBackStack("CharacterFormFragment")
                .commit();
        setTitle("Character Creation Form");
    }
    private void loadCharacterGridFragment(String _characters) {
        hideEmptyDataWarning();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, CharacterGridFragment.newInstance(_characters))
                .commit();
        setTitle("Characters");
    }

    private void intentToChecklist(Character _character, int _key) {
        Intent starter = new Intent(this, ChecklistActivity.class);
        starter.putExtra(EXTRA_USER, mUser);
        starter.putExtra(EXTRA_CHARACTER, _character);
        starter.putExtra(EXTRA_KEY, _key);
        startActivity(starter);
    }

    /**
     * Interface methods for the Character Form Fragment.
     */

    @Override
    public void saveTapped(Character _character) {
        //Turn the character info into a JSON Object string and then add it to the user and update the user account.
        //When saving, I need to know the count of characters so I can save to the appropriate key for that character.
        int characterCount = User.getUserCharacterCount(mUser.getUserCharacters());
        mUser.updateUserCharacters( _character.toJSONString(), characterCount + 1);
        //Update the users account.
        mDatabase.child(mUser.getUserName()).child("userCharacters").setValue(mUser.getUserCharacters()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "New Character Created and saved", Toast.LENGTH_SHORT).show();
                loadCharacterGridFragment(mUser.getUserCharacters());
            }
        });
    }

    /**
     * Interface methods for the Character Grid Fragment.
     */

    @Override
    public void characterTapped(Character _character, int _key) {
        //Launch the checklist activity with the character selected.
        intentToChecklist(_character, _key);
    }

}
