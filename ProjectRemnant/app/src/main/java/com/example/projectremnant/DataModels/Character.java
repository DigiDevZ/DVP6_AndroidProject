package com.example.projectremnant.DataModels;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Character implements Serializable {

    //Keys/Contracts
    public static final String KEY_TRAITRANK = "KEY_TRAITRANK";
    public static final String KEY_GAMERTAG = "KEY_GAMERTAG";
    public static final String KEY_NICKNAME = "KEY_NICKNAME";
    public static final String KEY_PLATFORM = "KEY_PLATFORM";
    public static final String KEY_ITEMS = "KEY_ITEMS";

    private String mTraitRank;
    private String mGamertag;
    private String mNickname;
    private String mPlatform;

    //This string of items will be a JSON String of all items ids that are owned by this character.
    //Or it can be an array of item extended class objects.
    //Testing for now.
    private String mItems;

    //Construct character without items.
    public Character(String _traitRank, String _gamertag, String _nickname, String _platform) {
        mTraitRank = _traitRank;
        mGamertag = _gamertag;
        mNickname = _nickname;
        mPlatform = _platform;
    }

    //Construct character with items.
    public Character(String _traitRank, String _gamertag, String _nickname,String _platform, String _items) {
        mTraitRank = _traitRank;
        mGamertag = _gamertag;
        mNickname = _nickname;
        mPlatform = _platform;
        mItems = _items;
    }

    public String getTraitRank() {
        return mTraitRank;
    }
    public void setTraitRank(String _value) {
        mTraitRank = _value;
    }

    public String getGamerTag() {
        return mGamertag;
    }

    public String getNickname() {
        return mNickname;
    }

    public String getPlatform() {
        return mPlatform;
    }

    public String getItems() {
        return mItems;
    }
    public void updateItems(String _updatedItems) {
        mItems = _updatedItems;
    }

    //TODO: Need to fill out this method.
    public String toJSONString() {

        JSONObject object = new JSONObject();
        //Set the values into the JSON.
        try {
            object.put(KEY_TRAITRANK, mTraitRank);
            object.put(KEY_GAMERTAG, mGamertag);
            object.put(KEY_NICKNAME, mNickname);
            object.put(KEY_PLATFORM, mPlatform);
            if(mItems != null) {
                object.put(KEY_ITEMS, mItems);
            }else {
                mItems = "Empty";
                object.put(KEY_ITEMS, mItems);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return object.toString();
    }

    public static Character fromJSONString(String _characterJSON) {

        try {
            //Decode the Character from the JSON and return it.
            JSONObject object = new JSONObject(_characterJSON);
            String platform = object.getString(KEY_PLATFORM);
            String nickname = object.getString(KEY_NICKNAME);
            String gamertag = object.getString(KEY_GAMERTAG);
            String traitrank = object.getString(KEY_TRAITRANK);
            String items = object.getString(KEY_ITEMS);

            return new Character(traitrank, gamertag, nickname, platform, items);

        }catch (JSONException e){
            e.printStackTrace();
        }

        return null;
    }


}
