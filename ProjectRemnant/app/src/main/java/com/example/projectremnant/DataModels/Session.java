package com.example.projectremnant.DataModels;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class Session {

    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_ID = "KEY_ID";
    public static final String KEY_LIMIT = "KEY_LIMIT";
    public static final String KEY_DESCRIPTION = "KEY_DESCRIPTION";
    public static final String KEY_CHARACTERSJOINED = "KEY_CHARACTERSJOINED";

    private String mSessionId;
    private String mSessionName;
    private int mSessionLimit;
    private int mSessionPlayersJoined;
    private String mSessionCreated;
    private String mSessionCharacters;
    private String mSessionDescription;

    public Session() {

    }

    public Session(String _sessionName, int _sessionLimit, String _sessionCharacters, String _sessionDescription) {

        //Assign the regular properties.
        mSessionName = _sessionName;
        mSessionLimit = _sessionLimit;
        mSessionCharacters = _sessionCharacters;
        mSessionDescription = _sessionDescription;

        //Create the session id when a new session is instantiated.
        String id = UUID.randomUUID().toString();
        String[] split = id.split("-");
        mSessionId = split[0];

        //Get the date of creation for the session.
        //mSessionCreated = _sessionCreated;
    }


    public String getSessionId() {
        return mSessionId;
    }

    public int getSessionLimit() {
        return mSessionLimit;
    }

    public String getSessionName() {
        return mSessionName;
    }

    public String getSessionDescription() {
        return mSessionDescription;
    }

    public int getSessionPlayersJoined() {
        return mSessionPlayersJoined;
    }
    public void setSessionPlayersJoined(int _value) {
        mSessionPlayersJoined = _value;
    }

    public String getSessionCharacters() {
        return mSessionCharacters;
    }
    public void setSessionCharacters(String _value) {
        mSessionCharacters = _value;
    }

    public String toJSONString() {
        return null;
    }
    public static void fromJSONString() {

    }

    public String getSessionDetails() {
        String returnString = "";

        try{
            JSONObject obj = new JSONObject();
            obj.put(KEY_NAME, mSessionName);
            obj.put(KEY_ID, mSessionId);
            obj.put(KEY_LIMIT, mSessionLimit);
            obj.put(KEY_DESCRIPTION, mSessionDescription);
            obj.put(KEY_CHARACTERSJOINED, mSessionCharacters);

            returnString = obj.toString();
        }catch (JSONException e) {
            e.printStackTrace();
        }

        return returnString;
    }

}
