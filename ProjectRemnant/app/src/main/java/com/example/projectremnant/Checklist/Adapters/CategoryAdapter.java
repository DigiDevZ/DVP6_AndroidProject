package com.example.projectremnant.Checklist.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectremnant.R;

import java.util.ArrayList;

import javax.crypto.Cipher;

public class CategoryAdapter extends BaseAdapter {

    private static final long BASE_ID = 0x0101;

    private final Context mContext;
    private final ArrayList<String> mCategories;

    public CategoryAdapter(Context _context, ArrayList<String> _categories) {
        mContext = _context;
        mCategories = _categories;
    }

    @Override
    public int getCount() {
        if(mCategories != null) {
            return mCategories.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(mCategories != null && position >= 0) {
            return mCategories.get(position);
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
        String category = (String)getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.category_adapter_layout, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder)convertView.getTag();
        }

        if(category != null && !category.isEmpty()) {
            vh.tv_category.setText(category);
        }

        return convertView;
    }

    static class ViewHolder{
        final ImageView iv_categoryImage;
        final TextView tv_category;
        private ViewHolder(View _layout) {
            iv_categoryImage = _layout.findViewById(R.id.iv_categoryImage);
            tv_category = _layout.findViewById(R.id.tv_category);
        }
    }

}
