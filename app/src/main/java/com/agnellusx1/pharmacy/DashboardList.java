package com.agnellusx1.pharmacy;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.agnellusx1.pharmacy.Adapters.OrderSample;
import com.agnellusx1.pharmacy.Adapters.TabAdapter;
import com.agnellusx1.pharmacy.Tabs.DeliveredTab;
import com.agnellusx1.pharmacy.Tabs.WardTab;
import com.agnellusx1.pharmacy.Tabs.PatientTab;
import com.google.android.material.tabs.TabLayout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


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

        DBconnect dBconnect=new DBconnect();
        Connection connection=dBconnect.connectionclass();

        ArrayList<OrderSample> itemList = new ArrayList<OrderSample>();
        Toast.makeText(getContext(), "Checking fo Orders", Toast.LENGTH_SHORT).show();
        if(connection==null){
            Toast.makeText(getContext(),"Check Internet", Toast.LENGTH_SHORT).show();
        }
        else {
            String query="Select PatientCode,Status,PatientName FROM Order_List where Status = '1'";

            try {
                Statement stmt=connection.createStatement();
                ResultSet resultSet=stmt.executeQuery(query);
                if(resultSet!=null){
                    while(resultSet.next()){
                        {
                            try{
                                OrderSample orderSample=new OrderSample(
                                        resultSet.getString("PatientCode"),
                                        resultSet.getString("Status"),
                                        resultSet.getString("PatientName")
                                );

                                itemList.add(orderSample);
//                                mNormalAdapter=new PatientAdapter(getContext(),itemList);
//                                mRecyclerView.setAdapter(mNormalAdapter);
                            }catch(Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    }

                }
                else{
                    Toast.makeText(getContext(), "Value is Null", Toast.LENGTH_SHORT).show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


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
        adapter.AddTab(new WardTab(),"Ward");
        adapter.AddTab(new PatientTab(),"Patient");
        adapter.AddTab(new DeliveredTab(),"Delivered");
        viewPager.setAdapter(adapter);

    }

}
