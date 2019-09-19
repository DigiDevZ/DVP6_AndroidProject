package com.example.projectremnant.Checklist.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.projectremnant.Checklist.Adapters.ChecklistAdapter;
import com.example.projectremnant.DataModels.Items.Item;
import com.example.projectremnant.R;

import java.util.ArrayList;

public class ChecklistFragment extends ListFragment {

    //TODO: Will need to add an interface method for checking the state of the checkbox when it is checked.

    private static final String ARG_ITEMS = "items";
    private static final String ARG_CATEGORY = "category";

    private OnItemClicked mListener;
    public interface OnItemClicked {
        void itemClicked(int _position, int _category);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof OnItemClicked) {
            mListener = (OnItemClicked) context;
        }
    }

    public static ChecklistFragment newInstance(ArrayList<Item> _items, int _category) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_ITEMS, _items);
        args.putInt(ARG_CATEGORY, _category);

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

        ArrayList<Item> items = (ArrayList<Item>) (getArguments() != null ? getArguments().getSerializable(ARG_ITEMS) : null);
        if(items != null) {

            ChecklistAdapter ca = new ChecklistAdapter(getActivity(), items);
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
}
