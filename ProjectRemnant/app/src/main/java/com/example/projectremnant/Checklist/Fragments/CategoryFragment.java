package com.example.projectremnant.Checklist.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projectremnant.Checklist.Adapters.CategoryAdapter;
import com.example.projectremnant.DataModels.Items.Item;
import com.example.projectremnant.R;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    private static final String TAG = "CategoryFragment.TAG";

    private static final String ARG_ITEMS = "items";

    private GridView mGridView;
    private ArrayList<Item>[] mItems = (ArrayList<Item>[]) new ArrayList[6];

    public static CategoryFragment newInstance(ArrayList<Item>[] _items) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ITEMS, _items);

        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    private CategoryFragmentListener mListener;
    public interface CategoryFragmentListener {
        void armorsTapped(ArrayList<Item> _armorSets);
        void amuletsTapped(ArrayList<Item> _amulets);
        void weaponsTapped(ArrayList<Item> _weapons);
        void modsTapped(ArrayList<Item> _mods);
        void traitsTapped(ArrayList<Item> _traits);
        void ringsTapped(ArrayList<Item> _rings);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof CategoryFragmentListener) {
            mListener = (CategoryFragmentListener)context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_categories_layout, container, false);
        mGridView = view.findViewById(R.id.gv_categories);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Item>[] itemsArray = (ArrayList<Item>[]) (getArguments() != null ? getArguments().getSerializable(ARG_ITEMS) : null);
        if(itemsArray != null) {
            //Assign the items arrays to the mItems array of array lists.
            mItems[0] = itemsArray[0];
            mItems[1] = itemsArray[1];
            mItems[2] = itemsArray[2];
            mItems[3] = itemsArray[3];
            mItems[4] = itemsArray[4];
            mItems[5] = itemsArray[5];
        }

        //Assign the grid view its adapter.
        mGridView.setAdapter(new CategoryAdapter(getActivity(), mItems));

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Run the appropriate interface method depending on what section was tapped.
                switch (position) {
                    case 0:
                        mListener.amuletsTapped(mItems[position]);
                        break;
                    case 1:
                        mListener.modsTapped(mItems[position]);
                        break;
                    case 2:
                        mListener.ringsTapped(mItems[position]);
                        break;
                    case 3:
                        mListener.traitsTapped(mItems[position]);
                        break;
                    case 4:
                        mListener.weaponsTapped(mItems[position]);
                        break;
                    case 5:
                        mListener.armorsTapped(mItems[position]);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
