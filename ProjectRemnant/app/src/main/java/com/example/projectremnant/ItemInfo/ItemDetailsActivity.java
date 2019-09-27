package com.example.projectremnant.ItemInfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectremnant.Character.CharacterActivity;
import com.example.projectremnant.Checklist.ChecklistActivity;
import com.example.projectremnant.Contracts.ItemContracts;
import com.example.projectremnant.DataModels.Character;
import com.example.projectremnant.DataModels.Items.Armor;
import com.example.projectremnant.DataModels.Items.Item;
import com.example.projectremnant.DataModels.Items.Weapon;
import com.example.projectremnant.DataModels.User;
import com.example.projectremnant.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ItemDetailsActivity extends AppCompatActivity {

    //TODO: ItemInfo package is good to go and signed off for final review.

    private static final String TAG = "ItemDetailsActivity";

    public static final String EXTRA_ITEM = "EXTRA_ITEM";
    public static final String EXTRA_CATEGORY = "EXTRA_CATEGORY";

    public static final String EXTRA_UPDATE = "EXTRA_UPDATE";

    //Variables for keeping track/updating user and character.
    private User mUser;
    private Character mCharacter;
    private int mCharacterKey;

    private int mCategory;

    //Views
    private TextView tv_itemName;
    private TextView tv_itemBonus;
    private TextView tv_itemUnlockCriteria;
    private TextView tv_itemCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details_activitiy);

        tv_itemName = findViewById(R.id.tv_itemName);
        tv_itemBonus = findViewById(R.id.tv_itemBonus);
        tv_itemUnlockCriteria = findViewById(R.id.tv_itemUnlockCriteria);
        tv_itemCategory = findViewById(R.id.tv_itemCategory);

        CheckBox cb_itemOwned = findViewById(R.id.cb_itemOwned);
        Button btn_viewWiki = findViewById(R.id.btn_viewWiki);

        //Need to get the starter intent that will have the item information and the category.
        Intent i = getIntent();
        if(i != null) {
            mUser = (User) i.getSerializableExtra(CharacterActivity.EXTRA_USER);
            mCharacter = (Character) i.getSerializableExtra(CharacterActivity.EXTRA_CHARACTER);
            mCharacterKey = i.getIntExtra(CharacterActivity.EXTRA_KEY, 0);

            final Item item = (Item) i.getSerializableExtra(EXTRA_ITEM);
            mCategory = i.getIntExtra(EXTRA_CATEGORY, -1);

            String itemsOwned = mCharacter.getItems();
            if(!itemsOwned.equals("Empty")){
                try {
                    //TODO: I could possibly make a method of this as well.
                    //Get the list of items from the character JSON, the owned items, and then send them into the adapter.
                    JSONObject obj = new JSONObject(mCharacter.getItems());
                    JSONArray array = obj.getJSONArray(getItemCategoryKey(mCategory));

                    for (int j = 0; j < array.length(); j++) {
                        String itemId = array.getString(j);
                        if(itemId.equals(String.valueOf(item.getItemId()))){
                            //Set the checkbox to checked.
                            cb_itemOwned.setChecked(true);
                        }
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //Set the click listener for the button.
            btn_viewWiki.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: Starting intent to view. wiki page: " + item.getItemWikiPage());
                    //Intent to the wikiPage of an item.
                    String uriString = item.getItemWikiPage();
                    Uri uri = Uri.parse(uriString);

                    Intent viewIntent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(viewIntent);
                }
            });


            String itemCategory = "";
            switch (mCategory) {
                case 0:
                    itemCategory = getString(R.string.item_details_activity_category_amulet);
                    displayBasicItemInfo(item, itemCategory);
                    break;
                case 1:
                    itemCategory = getString(R.string.item_details_activity_category_mod);
                    displayBasicItemInfo(item, itemCategory);
                    break;
                case 2:
                    itemCategory = getString(R.string.item_details_activity_category_ring);
                    displayBasicItemInfo(item, itemCategory);
                    break;
                case 3:
                    itemCategory = getString(R.string.item_details_activity_category_trait);
                    displayBasicItemInfo(item, itemCategory);
                    break;
                case 4:
                    itemCategory = getString(R.string.item_details_activity_category_weapon);
                    displayWeaponItemInfo((Weapon) item, itemCategory);
                    break;
                case 5:
                    itemCategory = getString(R.string.item_details_activity_category_armor);
                    displayArmorItemInfo((Armor) item, itemCategory);
                    break;
                default:
                    break;
            }

            //Set the listener for the checkbox so it updates the database as well.
            //TODO: Check out why this doesnt update the previous activity
            // it has to do with not updating the adapter when returning.
            // But when the adapter is updated it still doesnt work, possibly because i am using the user and character
            // 1. Update the character and user of the checklist activity in the activity result manually. **
            // 2. Update the character and user by calling a new copy down from the reference.
            cb_itemOwned.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox)v;
                    String updatedItems = addItemIdToOwnedObject(String.valueOf(item.getItemId()), mCharacter.getItems(), cb.isChecked());
                    mCharacter.updateItems(updatedItems);

                    mUser.updateUserCharacters(mCharacter.toJSONString(), mCharacterKey);

                    //Update database
                    DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUserName());
                    userReference.child("userCharacters").setValue(mUser.getUserCharacters()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.i(TAG, "onSuccess: update successful from item activity.");
                        }
                    });
                }
            });

            //End of intent null check.
        }
        //End of on create.
    }

    @Override
    public void onBackPressed() {

        Intent finish = new Intent();
        finish.putExtra(CharacterActivity.EXTRA_USER, mUser);
        finish.putExtra(CharacterActivity.EXTRA_CHARACTER, mCharacter);
        finish.putExtra(EXTRA_CATEGORY, mCategory);

        Log.i(TAG, "onBackPressed: finishing item details activity");
        
        setResult(RESULT_OK, finish);
        this.finishActivity(ChecklistActivity.REQUEST_ITEMDETAILS);
        super.onBackPressed();
    }

    //TODO: May make two more methods that handle the armor and weapons since those display more.
    private void displayBasicItemInfo(Item _item, String _category) {
        //Display the info from the item.
        tv_itemName.setText(_item.getItemName());
        tv_itemUnlockCriteria.setText(_item.getItemUnlockCriteria());
        tv_itemBonus.setText(_item.getItemBonus());
        tv_itemCategory.setText(_category);
    }

    private void displayWeaponItemInfo(Weapon _weapon, String _category) {
        //Display the info from the item.
        tv_itemName.setText(_weapon.getItemName());
        tv_itemUnlockCriteria.setText(_weapon.getItemUnlockCriteria());
        tv_itemCategory.setText(_category);
    }

    private void displayArmorItemInfo(Armor _armor, String _category) {
        //Display the info from the item.
        tv_itemName.setText(_armor.getItemName());
        tv_itemUnlockCriteria.setText(_armor.getItemUnlockCriteria());
        tv_itemCategory.setText(_category);
    }

    //TODO: these two methods are in the checklist fragment, I will put these in a static class soon.
    private String addItemIdToOwnedObject(String _id, String _items, boolean _state) {

        if(_items.equals("Empty")) {
            _items = null;
        }
        try {
            JSONObject itemsObj;
            if(_items == null) {
                itemsObj = new JSONObject();
                //Give this item obj the the json arrays it needs.
                itemsObj.put(ItemContracts.KEY_AMULETJSON, new JSONArray());
                itemsObj.put(ItemContracts.KEY_ARMORJSON, new JSONArray());
                itemsObj.put(ItemContracts.KEY_WEAPONJSON, new JSONArray());
                itemsObj.put(ItemContracts.KEY_TRAITJSON, new JSONArray());
                itemsObj.put(ItemContracts.KEY_RINGJSON, new JSONArray());
                itemsObj.put(ItemContracts.KEY_MODJSON, new JSONArray());
            }else {
                itemsObj = new JSONObject(_items);
            }

            //Add the item id to the right category of the items JSON
            JSONArray itemsArray = itemsObj.getJSONArray(getItemCategoryKey(mCategory));

            //Before update the item array, if the state is false we take the item out, if it is true then we place it in.
            if(_state) {
                itemsArray.put(_id);
            }else {
                int indexToRemove = 0;
                for (int i = 0; i < itemsArray.length(); i++) {
                    String indexItemId = itemsArray.getString(i);
                    if(indexItemId.equals(_id)) {
                        indexToRemove = i;
                    }
                }
                itemsArray.remove(indexToRemove);
            }

            //Update the itemsObj and then send it out as a string to update the characters items.
            itemsObj.put(getItemCategoryKey(mCategory), itemsArray);
            return itemsObj.toString();
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String getItemCategoryKey(int _category) {

        String itemCategory = "";
        switch (_category) {
            case 0:
                itemCategory = ItemContracts.KEY_AMULETJSON;
                break;
            case 1:
                itemCategory = ItemContracts.KEY_MODJSON;
                break;
            case 2:
                itemCategory = ItemContracts.KEY_RINGJSON;
                break;
            case 3:
                itemCategory = ItemContracts.KEY_TRAITJSON;
                break;
            case 4:
                itemCategory = ItemContracts.KEY_WEAPONJSON;
                break;
            case 5:
                itemCategory = ItemContracts.KEY_ARMORJSON;
                break;
            default:
                break;
        }
        return itemCategory;
    }

}
