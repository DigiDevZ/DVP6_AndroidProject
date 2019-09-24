package com.example.projectremnant.Character.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectremnant.DataModels.Character;
import com.example.projectremnant.R;

import java.util.ArrayList;

public class CharacterAdapter extends BaseAdapter {

    private static final long BASE_ID = 0x0121;

    private final Context mContext;
    private final ArrayList<Character> mCharacters;

    public CharacterAdapter(Context _context, ArrayList<Character> _characters) {
        mContext = _context;
        mCharacters = _characters;
    }


    @Override
    public int getCount() {
        if(mCharacters != null) {
            return mCharacters.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(mCharacters != null && position >= 0) {
            return mCharacters.get(position);
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
        Character character = (Character) getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.character_adapter_layout, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder)convertView.getTag();
        }

        if(character != null) {
            vh.tv_platform.setText(character.getPlatform());
            vh.tv_nickname.setText(character.getNickname());
            vh.tv_traitrank.setText(character.getTraitRank());
        }

        return convertView;
    }

    static class ViewHolder{
        final TextView tv_nickname;
        final TextView tv_traitrank;
        final TextView tv_platform;
        private ViewHolder(View _layout) {
            tv_nickname = _layout.findViewById(R.id.tv_nickname);
            tv_traitrank = _layout.findViewById(R.id.tv_traitrank);
            tv_platform = _layout.findViewById(R.id.tv_platform);
        }
    }


}
