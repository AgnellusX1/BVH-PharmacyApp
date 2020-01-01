package com.agnellusx1.pharmacy.Tabs;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.agnellusx1.pharmacy.Adapters.OrderSample;
import com.agnellusx1.pharmacy.Adapters.PatientAdapter;
import com.agnellusx1.pharmacy.Adapters.WardAdapter;
import com.agnellusx1.pharmacy.DBconnect;
import com.agnellusx1.pharmacy.MainActivity;
import com.agnellusx1.pharmacy.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class WardTab extends Fragment {

    ArrayList<OrderSample>itemList;
    private RecyclerView mRecyclerView;
    WardAdapter mWardAdapter;


    public WardTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ward_tab, container, false);

        DBconnect dBconnect=new DBconnect();
        Connection connection=dBconnect.connectionclass();


        mRecyclerView=view.findViewById(R.id.wardlist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        itemList=new ArrayList<OrderSample>();
        itemList.clear();

        if(connection==null){
            Toast.makeText(getContext(),"Check Internet", Toast.LENGTH_SHORT).show();
        }
        else {
            String query="Select * FROM Pharmacy_status where Status = '1',PatientName='NULL' and scanUser = '"+ MainActivity.scanUserName +"'";
            try {
                Statement stmt=connection.createStatement();
                ResultSet resultSet=stmt.executeQuery(query);
                if(resultSet!=null){
                    while(resultSet.next()){
                        {
                            try{
                                OrderSample orderSample=new OrderSample(
                                        resultSet.getString("WardName"),
                                        resultSet.getString("Status"),
                                        resultSet.getString("MatlIssueNumber")
                                );

                                itemList.add(orderSample);
                                mWardAdapter =new WardAdapter(getContext(),itemList);
                                mRecyclerView.setAdapter(mWardAdapter);

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
        return view;

    }

}
