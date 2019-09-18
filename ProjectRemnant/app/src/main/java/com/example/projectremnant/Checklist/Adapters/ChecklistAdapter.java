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

import java.util.ArrayList;

public class ChecklistAdapter extends BaseAdapter {

    //TODO: This adapter will showcase each item from the selected category.
    private static final long BASE_ID = 0x0102;

    private final Context mContext;
    private final ArrayList<Item> mItems;

    public ChecklistAdapter(Context _context, ArrayList<Item> _items) {
        mContext = _context;
        mItems = _items;
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
    public View getView(int position, View convertView, ViewGroup parent) {

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
