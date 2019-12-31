package com.agnellusx1.pharmacy.Tabs;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agnellusx1.pharmacy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeliveredTab extends Fragment {


    public DeliveredTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dilevered_tab, container, false);
    }

}
