package com.example.projectremnant.Sessions.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projectremnant.DataModels.Session;
import com.example.projectremnant.R;

import java.util.ArrayList;

public class SessionAdapter extends BaseAdapter {

    private static final long BASE_ID = 0x0111;

    private final Context mContext;
    private final ArrayList<Session> mSessions;

    public SessionAdapter(Context _context, ArrayList<Session> _sessions) {
        mContext = _context;
        mSessions = _sessions;
    }


    @Override
    public int getCount() {
        if(mSessions != null) {
            return mSessions.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(mSessions != null && position >= 0) {
            return mSessions.get(position);
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
        Session session = (Session) getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.session_adapter_layout, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder)convertView.getTag();
        }

        if(session != null) {
            vh.tv_name.setText(session.getSessionName());
            int limit = session.getSessionLimit();
            //TODO: Make it so that the characters in the session characters, come from a JSONArray.
            String limitString = "Need: " + limit;
            vh.tv_limit.setText(limitString);
        }

        return convertView;
    }

    static class ViewHolder {
        final TextView tv_name;
        final TextView tv_limit;
        private ViewHolder(View _layout) {
            tv_name = _layout.findViewById(R.id.tv_name);
            tv_limit = _layout.findViewById(R.id.tv_limit);
        }

    }
}
