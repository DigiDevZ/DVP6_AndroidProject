package com.example.projectremnant.DataModels.Items;

public class Item {

    private String mItemName;
    private String mItemBonus;
    private String mItemUnlockCriteria;
    private String mItemWikiPage;

    private long mItemId;

    //

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

}
