package com.example.projectremnant.DataModels.Items;

import java.io.Serializable;

public class Item implements Serializable {



    //Test difference of development vs master branch.

    private String mItemName;
    private String mItemBonus;
    private String mItemUnlockCriteria;
    private String mItemWikiPage;

    private long mItemId;

    //Constructors
    public Item() {

    }

    //Item with all properties EXCEPT itemBonus, for use with the armor and weapons items.
    public Item(String _itemName, long _itemId, String _unlockCriteria, String _wikiPage) {
        mItemName = _itemName;
        mItemId = _itemId;
        mItemUnlockCriteria = _unlockCriteria;
        mItemWikiPage = _wikiPage;
    }

    //Item with all properties.
    public Item(String _itemName, long _itemId, String _itemBonus, String _unlockCriteria, String _wikiPage) {
        mItemName = _itemName;
        mItemId = _itemId;
        mItemBonus = _itemBonus;
        mItemUnlockCriteria = _unlockCriteria;
        mItemWikiPage = _wikiPage;
    }

    public String getItemName() {
        return mItemName;
    }
    public void setItemName(String _itemName) {
        mItemName = _itemName;
    }

    public String getItemBonus() {
        return mItemBonus;
    }
    public void setItemBonus(String _itemBonus) {
        mItemBonus = _itemBonus;
    }

    public String getItemUnlockCriteria() {
        return mItemUnlockCriteria;
    }
    public void setItemUnlockCriteria(String _unlockCriteria) {
        mItemUnlockCriteria = _unlockCriteria;
    }

    public String getItemWikiPage() {
        return mItemWikiPage;
    }
    public void setItemWikiPage(String _wikiPage) {
        mItemWikiPage = _wikiPage;
    }

    public long getItemId() {
        return mItemId;
    }
    public void setItemId(long _itemId) {
        mItemId = _itemId;
    }

    public static String itemIdListToJSON() {

        return "";
    }

}
