package com.agnellusx1.pharmacy.Tabs;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.agnellusx1.pharmacy.Adapters.NormalAdapter;
import com.agnellusx1.pharmacy.Adapters.OrderSample;
import com.agnellusx1.pharmacy.AddItems;
import com.agnellusx1.pharmacy.DBconnect;
import com.agnellusx1.pharmacy.Dashboard;
import com.agnellusx1.pharmacy.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NormalTab extends Fragment {
    ArrayList<OrderSample>itemList;
    private RecyclerView mRecyclerView;
    NormalAdapter mNormalAdapter;


    public NormalTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_normal_tab, container, false);

        DBconnect dBconnect=new DBconnect();
        Connection connection=dBconnect.connectionclass();

        mRecyclerView=view.findViewById(R.id.normalList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemList=new ArrayList<OrderSample>();
        itemList.clear();
        Toast.makeText(getContext(), "Checking for Orders", Toast.LENGTH_SHORT).show();
        if(connection==null){
            Toast.makeText(getContext(),"Check Internet", Toast.LENGTH_SHORT).show();
        }
        else {
            String query="Select MIN,LOCATION,NAME FROM RecyclerTable";
            try {
                Statement stmt=connection.createStatement();
                ResultSet resultSet=stmt.executeQuery(query);
                if(resultSet!=null){
                    while(resultSet.next()){
                        {
                            try{
                                OrderSample orderSample=new OrderSample(
                                        resultSet.getString("NAME"),
                                        resultSet.getString("LOCATION"),
                                        resultSet.getString("MIN")
                                );

                                itemList.add(orderSample);
                                mNormalAdapter=new NormalAdapter(getContext(),itemList);
                                mRecyclerView.setAdapter(mNormalAdapter);
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

//        mNormalAdapter=new NormalAdapter(getContext(),itemList);
//        mRecyclerView.setAdapter(mNormalAdapter);
        return view;
    }

 }


