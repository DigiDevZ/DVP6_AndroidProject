package com.example.projectremnant.DataModels;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private static final String TAG = "User.TAG";

    public static final String KEY_CHARACTER1 = "KEY_CHARACTER1";
    public static final String KEY_CHARACTER2 = "KEY_CHARACTER2";
    public static final String KEY_CHARACTER3 = "KEY_CHARACTER3";
    public static final String KEY_CHARACTER4 = "KEY_CHARACTER4";

    private String mUserName;
    private String mUserPass;
    private long mUserId;
    private String mUserCharacters;
    private String mJoinedSessionsIds;


    private ArrayList<String> mJoinedSessions;

    public User() {

    }

    public User(String _userName, String _userPass, String _userCharacters, long _userId, String _joinedSessionIds) {
        mUserName = _userName;

        //TODO: Need to encode password on creation and decode whenever authenticating.
        mUserPass = _userPass;
        mUserId = _userId;
        mUserCharacters = _userCharacters;
        mJoinedSessionsIds = _joinedSessionIds;
        mJoinedSessions = new ArrayList<>();
    }


    public static void encodePassword() {

    }

    public ArrayList<String> getJoinedSessions() {
        return mJoinedSessions;
    }
    public void setJoinedSessions(ArrayList<String> _sessionIds) {
        mJoinedSessions = _sessionIds;
    }
    public void updateJoinedSessions(String _sessionId) {
        mJoinedSessions.add(_sessionId);
    }

    public String getUserName() {
        return mUserName;
    }

    //TODO: This is only saving the one session, so i need to figure out why there is only 1 being saved in this list.
    public String getJoinedSessionsIds() {
        JSONArray array = new JSONArray();
        for (int i = 0; i < mJoinedSessions.size(); i++) {
            array.put(mJoinedSessions.get(i));
        }
        return array.toString();
    }


    public String getUserPass() {
        return  mUserPass;
    }

    public Long getUserId() {
        return mUserId;
    }

    public String getUserCharacters() {
        return mUserCharacters;
    }
    public void updateUserCharacters(String _updatedCharacter, int _key) {

        if(mUserCharacters.equals("Empty") || _key == 0){
            try {
                JSONObject charactersObj = new JSONObject();
                charactersObj.put(KEY_CHARACTER1, _updatedCharacter);
                Log.i(TAG, "updateUserCharacters: new characters obj created: " + charactersObj.toString());

                //Save the new JSON to characters as a string.
                mUserCharacters = charactersObj.toString();
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            try {
                //Save the updated character to the right place in the JSON.
                JSONObject charactersObj = new JSONObject(mUserCharacters);
                String keyForUpdatedCharacter = getCharacterKey(_key);
                Log.i(TAG, "updateUserCharacters: key: " + keyForUpdatedCharacter);
                charactersObj.put(keyForUpdatedCharacter, _updatedCharacter);

                //Save the updated JSON to characters as a string.
                mUserCharacters = charactersObj.toString();
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Character> getUserCharactersAsArrayList(String _characters) {

        ArrayList<Character> charactersList = new ArrayList<>();
        try{
            JSONObject charactersObj = new JSONObject(_characters);
            for (int i = 0; i < charactersObj.length(); i++) {
                String characterAsString = charactersObj.getString(getCharacterKey(i + 1));
                Character character = Character.fromJSONString(characterAsString);
                charactersList.add(character);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return charactersList;
    }
    public static int getUserCharacterCount(String _characters) {
        int characterCount = 0;
        try{
            JSONObject charactersObj = new JSONObject(_characters);
            characterCount = charactersObj.length();
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return characterCount;
    }

    //This method will get the JSON Key for the character that is being updated.
    private static String getCharacterKey(int _key) {
        String characterKey = "";
        switch (_key) {
            case 1:
                characterKey = KEY_CHARACTER1;
                break;
            case 2:
                characterKey = KEY_CHARACTER2;
                break;
            case 3:
                characterKey = KEY_CHARACTER3;
                break;
            case 4:
                characterKey = KEY_CHARACTER4;
                break;
            default:
                break;
        }
        return characterKey;
    }


}
