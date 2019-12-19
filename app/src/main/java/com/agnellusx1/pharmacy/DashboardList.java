package com.agnellusx1.pharmacy;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agnellusx1.pharmacy.Adapters.TabAdapter;
import com.agnellusx1.pharmacy.Tabs.FastTrackTab;
import com.agnellusx1.pharmacy.Tabs.NormalTab;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardList extends Fragment {
    private ViewPager viewPager;
    public TabAdapter mTabAdapter;

    public DashboardList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_dashboard_list, container, false);
        mTabAdapter=new TabAdapter(this.getChildFragmentManager());

        viewPager= view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    public void setupViewPager(ViewPager viewPager){
        TabAdapter adapter= new TabAdapter(this.getChildFragmentManager());
        adapter.AddTab(new FastTrackTab(),"Fast Track");
        adapter.AddTab(new NormalTab(),"Normal");
        viewPager.setAdapter(adapter);

    }

}
