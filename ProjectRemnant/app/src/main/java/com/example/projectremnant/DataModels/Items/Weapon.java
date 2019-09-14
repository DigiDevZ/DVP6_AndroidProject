package com.example.projectremnant.DataModels.Items;

public class Weapon extends Item {

    private long mWeaponCategory;

    public Weapon() {
        super();
    }

    //Custom constructor.
    public Weapon(String _weaponName, String _unlockCriteria, long _weaponId, String _wikiPage, long _weaponCategory) {
        super(_weaponName, _weaponId, _unlockCriteria, _wikiPage);
        mWeaponCategory = _weaponCategory;
    }

    public String getWeaponCategory() {
        String weaponCategory = "";
        if(mWeaponCategory == 1) {
            weaponCategory = "Long Gun";
        }else if(mWeaponCategory == 2) {
            weaponCategory = "Sidearm";
        }else if(mWeaponCategory == 3) {
            weaponCategory = "Melee";
        }
        return weaponCategory;
    }
    public void setWeaponCategory(long _weaponCategory) {
        mWeaponCategory = _weaponCategory;
    }

}
