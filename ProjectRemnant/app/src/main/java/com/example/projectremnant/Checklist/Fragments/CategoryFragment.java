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
import com.example.projectremnant.R;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    private static final String TAG = "CategoryFragment.TAG";

    private GridView mGridView;

    public static CategoryFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    private CategoryFragmentListener mListener;
    public interface CategoryFragmentListener {
        void armorsTapped();
        void amuletsTapped();
        void weaponsTapped();
        void modsTapped();
        void traitsTapped();
        void ringsTapped();
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
        View v = getLayoutInflater().inflate(R.layout.fragment_categories_layout, container, false);
        mGridView = v.findViewById(R.id.gv_categories);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ArrayList<String> categories = new ArrayList<>();
        categories.add("Amulets");
        categories.add("Armor");
        categories.add("Mods");
        categories.add("Traits");
        categories.add("Rings");
        categories.add("Weapons");

        //Assign the grid view its adapter.
        mGridView.setAdapter(new CategoryAdapter(getActivity(), categories));

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick: category: " + categories.get(position));
            }
        });
    }
}
