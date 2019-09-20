package com.example.projectremnant.DataModels;

import java.util.UUID;

public class Session {

    private String mSessionId;
    private String mSessionName;
    private int mSessionLimit;
    private int mSessionPlayersJoined;
    private String mSessionCreated;
    private String mSessionCharacters;
    private String mSessionDescription;

    //TODO: Need to implement this soon.
    private String mSessionJoinedPlayersIds;

    public Session() {

    }

    public Session(String _sessionName, int _sessionLimit, String _sessionCreated, String _sessionCharacters, String _sessionDescription) {

        //Assign the regular properties.
        mSessionName = _sessionName;
        mSessionLimit = _sessionLimit;
        mSessionCreated = _sessionCreated;
        mSessionCharacters = _sessionCharacters;
        mSessionDescription = _sessionDescription;

        //Create the session id when a new session is instantiated.
        mSessionId = UUID.randomUUID().toString();

        //Get the date of creation for the session.
        mSessionCreated = _sessionCreated;
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

    public String toJSON() {
        return null;
    }

}
