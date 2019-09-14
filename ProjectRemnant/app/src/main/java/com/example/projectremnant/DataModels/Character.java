package com.example.projectremnant.DataModels;

public class Character {

    private String mTraitRank;
    private String mGamertag;
    private String mNickname;

    //This string of items will be a JSON String of all items ids that are owned by this character.
    //Or it can be an array of item extended class objects.
    //Testing for now.
    private String mItems;

    //Constructor for receiving data from database.
    public Character() {

    }

    //Construct character without items.
    public Character(String _traitRank, String _gamertag, String _nickname) {
        mTraitRank = _traitRank;
        mGamertag = _gamertag;
        mNickname = _nickname;
    }

    //Construct character with items.
    public Character(String _traitRank, String _gamertag, String _nickname, String _items) {
        mTraitRank = _traitRank;
        mGamertag = _gamertag;
        mNickname = _nickname;
        mItems = _items;
    }

    //TODO: Need to fill out this method.
    public void toJSON() {

    }


}
