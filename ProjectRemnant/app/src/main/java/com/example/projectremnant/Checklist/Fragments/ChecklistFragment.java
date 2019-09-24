package com.example.projectremnant.Checklist.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.projectremnant.Checklist.Adapters.ChecklistAdapter;
import com.example.projectremnant.Contracts.ItemContracts;
import com.example.projectremnant.DataModels.Character;
import com.example.projectremnant.DataModels.Items.Item;
import com.example.projectremnant.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChecklistFragment extends ListFragment implements ChecklistAdapter.CheckBoxChecked {

    private static final String TAG = "ChecklistFragment.TAG";
    //TODO: Will need to add an interface method for checking the state of the checkbox when it is checked.

    private static final String ARG_ITEMS = "items";
    private static final String ARG_CATEGORY = "category";
    private static final String ARG_CHARACTER = "character";

    private Character mCharacter;

    private ChecklistFragmentListener mListener;
    public interface ChecklistFragmentListener {
        void itemClicked(int _position, int _category);
        void updateCharacter(Character _character);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof ChecklistFragmentListener) {
            mListener = (ChecklistFragmentListener) context;
        }
    }

    public static ChecklistFragment newInstance(ArrayList<Item> _items, int _category, Character _character) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_ITEMS, _items);
        args.putInt(ARG_CATEGORY, _category);
        args.putSerializable(ARG_CHARACTER, _character);

        ChecklistFragment fragment = new ChecklistFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_checklist_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Character character = (Character) (getArguments() != null ? getArguments().getSerializable(ARG_CHARACTER) : null);
        if(character != null) {
            mCharacter = character;
        }

        ArrayList<Item> items = (ArrayList<Item>) (getArguments() != null ? getArguments().getSerializable(ARG_ITEMS) : null);
        if(items != null && character != null) {

            int category = (getArguments() != null ? getArguments().getInt(ARG_CATEGORY) : 0);

            ArrayList<String> itemsOwnedArray = new ArrayList<>();

            String itemsOwned = character.getItems();
            if(itemsOwned.equals("Empty")){
                //Set the array to null so it doesn't check.
                itemsOwnedArray = null;
            }else {
                try {
                    //TODO: This will work but it needs stuff first. I am always creating a new character in this testing,
                    //  - so i either rough it out and finish the character screens and then update the database from the items screen and backward where needed,
                    // and bum rush the sessions portion of the app.

                    //Get the list of items from the character JSON, the owned items, and then send them into the adapter.
                    JSONObject obj = new JSONObject(character.getItems());
                    JSONArray array = obj.getJSONArray(getItemCategoryKey(category));

                    for (int i = 0; i < array.length(); i++) {
                        String itemId = array.getString(i);
                        itemsOwnedArray.add(itemId);
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            ChecklistAdapter ca = new ChecklistAdapter(getActivity(), items, this, itemsOwnedArray);
            setListAdapter(ca);
        }
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //NOTE: Setting the xml for the adapter layout, the row items are not focusable and their text is not selectable, that was how this item click was fixed.
        int category = (getArguments() != null ? getArguments().getInt(ARG_CATEGORY) : 0);
        mListener.itemClicked(position, category);
    }


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

            int category = (getArguments() != null ? getArguments().getInt(ARG_CATEGORY) : 0);
            //Add the item id to the right category of the items JSON
            JSONArray itemsArray = itemsObj.getJSONArray(getItemCategoryKey(category));

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

//            //TODO: Testing loop.
//            for (int i = 0; i < itemsArray.length(); i++) {
//                Log.i(TAG, "addItemIdToOwnedObject: item id: " + itemsArray.getString(i));
//            }

            //Update the itemsObj and then send it out as a string to update the characters items.
            itemsObj.put(getItemCategoryKey(category), itemsArray);
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

    /**
     * Interface method for the checklist adapter.
     */
    
    @Override
    public void checkboxTapped(int _position, boolean _state) {
        Log.i(TAG, "checkboxTapped: position: " + _position + " state: " + _state);
        //TODO: Get the items string of ids, and then turn it into JSON, and add a item into a new slot or array.
        // THis will happen in this method, and then update the users characters value from the database.
        ArrayList<Item> items = (ArrayList<Item>) (getArguments() != null ? getArguments().getSerializable(ARG_ITEMS) : null);
        if(items != null) {

            Item checkedItem = items.get(_position);
            Log.i(TAG, "checkboxTapped: checked item: " + checkedItem.getItemName());

            //Update the items on the character, and then interface to the activity to have it update the update and user account.
            String updatedItems = addItemIdToOwnedObject(String.valueOf(checkedItem.getItemId()), mCharacter.getItems(), _state);
            mCharacter.updateItems(updatedItems);

            //Update the character on the activity.
            mListener.updateCharacter(mCharacter);
        }
    }

}

