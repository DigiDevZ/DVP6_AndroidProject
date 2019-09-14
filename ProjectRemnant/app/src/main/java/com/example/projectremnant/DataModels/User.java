package com.example.projectremnant.DataModels;

public class User {

    public String mUserName;
    public String mUserPass;
    public long mUserId;
    public String mUserCharacters;

    public User() {

    }

    public User(String _userName, String _userPass, String _userCharacters, long _userId) {
        mUserName = _userName;

        //TODO: Need to encode password on creation and decode whenever authenticating.
        mUserPass = _userPass;
        mUserId = _userId;
        mUserCharacters = _userCharacters;
    }


    public static void encodePassword() {

    }

    public void updateUserCharacters(String _updatedCharacters) {
        mUserCharacters = _updatedCharacters;
    }


}
