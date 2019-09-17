package com.example.projectremnant.DataModels.Items;

import android.util.Log;

import com.example.projectremnant.Authentication.LoginActivity;

import java.io.Serializable;

public class Armor extends Item implements Serializable {

    private static final String TAG = "Armor.TAG";

    private String mArmorPieces;
    private String mArmorSetBonus;
    private String mArmorBonusStages;

    public Armor() {
        super();
    }

    public Armor(String _armorName, long _armorSetId, String _armorPieces, String _armorSetBonus, String _armorBonusStages, String _unlockCriteria, String _wikiPage) {
        super(_armorName, _armorSetId, _unlockCriteria, _wikiPage);
        mArmorPieces = _armorPieces;
        mArmorSetBonus = _armorSetBonus;
        mArmorBonusStages = _armorBonusStages;
    }

    public String[] getArmorPieces() {

        //Split the string based on "/" and return that array.
        return mArmorPieces.split("/");
    }
    public String[] getArmorBonusStages() {

        return mArmorBonusStages.split("/");
    }

    public String getArmorSetBonus() {
        return mArmorSetBonus;
    }
    public void setmArmorSetBonus(String _armorSetBonus) {
        mArmorSetBonus = _armorSetBonus;
    }

}
