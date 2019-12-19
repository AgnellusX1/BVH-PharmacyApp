package com.agnellusx1.pharmacy.Tabs;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agnellusx1.pharmacy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NormalTab extends Fragment {
    private RecyclerView mRecyclerView;


    public NormalTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_normal_tab, container, false);

        mRecyclerView=view.findViewById(R.id.normalList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));


        return view;
    }

}
