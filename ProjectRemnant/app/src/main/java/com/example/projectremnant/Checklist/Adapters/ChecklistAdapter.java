package com.example.projectremnant.Checklist.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.projectremnant.DataModels.Items.Item;
import com.example.projectremnant.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ChecklistAdapter extends BaseAdapter {



    //This adapter will showcase each item from the selected category.
    private static final long BASE_ID = 0x0102;

    private final Context mContext;
    private final ArrayList<Item> mItems;
    private final ArrayList<Boolean> mItemUnlockedState = new ArrayList<>();

    private CheckBoxChecked mListener;
    public interface CheckBoxChecked {
        public void checkboxTapped(int _position, boolean _state);
    }

    public ChecklistAdapter(Context _context, ArrayList<Item> _items, CheckBoxChecked _listener, String _itemsOwnedArray) {
        mContext = _context;
        mItems = _items;
        mListener = _listener;

        //Establish the default boolean array state.
        for (int i = 0; i < mItems.size(); i++) {
            mItemUnlockedState.add(false);
        }

        try {
            JSONArray array = new JSONArray(_itemsOwnedArray);

            //TODO: When I have implemented the tracking of items on a user/character, i can check for the items that are owned.
            for (int i = 0; i < mItems.size(); i++) {
                String itemId = String.valueOf(mItems.get(i).getItemId());
                for (int j = 0; j < array.length(); j++) {
                    if(itemId.equals(array.getString(j))) {
                        mItemUnlockedState.add(i,true);
                    }
                }
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    public int getCount() {
        if(mItems != null) {
            return mItems.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(mItems != null && position >= 0) {
            return mItems.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return BASE_ID + position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //TODO: Later will need to add in functionality to check off items owned.
        ViewHolder vh;
        Item item = (Item) getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.checklist_adapter_layout, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder)convertView.getTag();
        }

        if(item != null) {
            vh.tv_itemName.setText(item.getItemName());

            //To make sure that checkboxes stay checked/not checked correctly.
            vh.cb_itemChecked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((CheckBox) v).isChecked()) {
                        mItemUnlockedState.set(position, true);
                    }else {
                        mItemUnlockedState.set(position, false);
                    }
                }
            });
            //Set the checkbox of the row to the state of the item owned array index.
            vh.cb_itemChecked.setChecked(mItemUnlockedState.get(position));

            vh.cb_itemChecked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox)v;
                    mListener.checkboxTapped(position, cb.isChecked());
                }
            });
        }

        return convertView;
    }

    static class ViewHolder{
        final TextView tv_itemName;
        final CheckBox cb_itemChecked;
        private ViewHolder(View _layout) {
            cb_itemChecked = _layout.findViewById(R.id.cb_itemChecked);
            tv_itemName = _layout.findViewById(R.id.tv_itemName);
        }
    }

}
