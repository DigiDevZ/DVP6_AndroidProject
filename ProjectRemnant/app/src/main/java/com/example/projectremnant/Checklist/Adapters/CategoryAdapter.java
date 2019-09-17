package com.example.projectremnant.Checklist.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectremnant.DataModels.Items.Item;
import com.example.projectremnant.R;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {

    private static final long BASE_ID = 0x0101;

    private final Context mContext;
    private final ArrayList<Item>[] mItems;

    public CategoryAdapter(Context _context, ArrayList<Item>[] _items) {
        mContext = _context;
        mItems = _items;
    }

    @Override
    public int getCount() {
        if(mItems != null) {
            return mItems.length;
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(mItems != null && position >= 0) {
            return mItems[position];
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return BASE_ID + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;
        String category = getCategory(position);
        ArrayList<Item> categoryItems = (ArrayList<Item>) getItem(position);
        int itemCount = categoryItems.size();

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.category_adapter_layout, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder)convertView.getTag();
        }

        if(category != null && !category.isEmpty()) {
            vh.tv_category.setText(category);

            String itemCountDisplay = "Items: " + itemCount;
            vh.tv_items.setText(itemCountDisplay);
        }

        return convertView;
    }

    static class ViewHolder{
        final ImageView iv_categoryImage;
        final TextView tv_category;
        final TextView tv_items;
        private ViewHolder(View _layout) {
            iv_categoryImage = _layout.findViewById(R.id.iv_categoryImage);
            tv_category = _layout.findViewById(R.id.tv_category);
            tv_items = _layout.findViewById(R.id.tv_items);
        }
    }



    private String getCategory(int _position){

        String category = "";
        if(_position == 0) {
            category = "Amulets";
        }else if(_position == 1) {
            category = "Mods";
        }else if(_position == 2){
            category = "Rings";
        }else if(_position == 3) {
            category = "Traits";
        }else if(_position == 4) {
            category = "Weapons";
        }else if(_position == 5) {
            category = "Armor Sets";
        }
        return category;
    }
}
