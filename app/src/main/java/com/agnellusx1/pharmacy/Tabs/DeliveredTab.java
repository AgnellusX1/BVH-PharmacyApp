package com.agnellusx1.pharmacy.Tabs;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agnellusx1.pharmacy.Adapters.DeliveredAdapter;
import com.agnellusx1.pharmacy.Adapters.OrderSample;
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
public class DeliveredTab extends Fragment {
    ArrayList<OrderSample>itemList;
    private RecyclerView mRecyclerView;
    DeliveredAdapter mDeliveredAdapter;


    public DeliveredTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dilevered_tab, container, false);

        DBconnect dBconnect=new DBconnect();
        Connection connection=dBconnect.connectionclass();


        mRecyclerView=view.findViewById(R.id.deliveredlist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        itemList=new ArrayList<OrderSample>();
        itemList.clear();

        if(connection==null){
            Toast.makeText(getContext(),"Check Internet", Toast.LENGTH_SHORT).show();
        }
        else {
            // TODO Verify If Date Query works Properly
            String query="Select * FROM Pharmacy_status where Status = '0' and scanUser = '"+ MainActivity.scanUserName+"' and Convert(varchar(10),MatlIssueDate,120) >= Convert(varchar(10),getdate()-1,120)";
            try {
                Statement stmt=connection.createStatement();
                ResultSet resultSet=stmt.executeQuery(query);
                if(resultSet!=null){
                    while(resultSet.next()){
                        {
                            try{
                                OrderSample orderSample=new OrderSample(
                                        resultSet.getString("DeliveryEnd"),
                                        resultSet.getString("WardName"),
                                        resultSet.getString("MatlIssueNumber")
                                );

                                itemList.add(orderSample);
                                mDeliveredAdapter =new DeliveredAdapter(getContext(),itemList);
                                mRecyclerView.setAdapter(mDeliveredAdapter);

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