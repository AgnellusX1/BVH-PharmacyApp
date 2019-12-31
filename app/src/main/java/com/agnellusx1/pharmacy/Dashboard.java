package com.agnellusx1.pharmacy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agnellusx1.pharmacy.Adapters.NormalAdapter;
import com.agnellusx1.pharmacy.Adapters.OrderSample;
import com.agnellusx1.pharmacy.Adapters.TabAdapter;
import com.agnellusx1.pharmacy.Tabs.FastTrackTab;
import com.agnellusx1.pharmacy.Tabs.NormalTab;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Dashboard extends AppCompatActivity {
   String BillNo;
   String value="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        FloatingActionButton AddBtn = findViewById(R.id.AddBtn);
        ImageButton searchButton=findViewById(R.id.searchButton);
        final EditText search=findViewById(R.id.search);


        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addItems=new Intent(Dashboard.this,AddItems.class);
                startActivity(addItems);
                finish();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(Dashboard.this, "Searching", Toast.LENGTH_SHORT).show();
                value=search.getText().toString();
                DBconnect DB=new DBconnect();
                Connection connection= DB.connectionclass();
                if(connection==null){
                    Toast.makeText(Dashboard.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }else{
                    String query="Select * from Vw_PharmacyDeliveries where PatientCode='"+value+"'";
                    try {
                        Statement stmt = connection.createStatement();
                        ResultSet resultSet=stmt.executeQuery(query);
                        if(resultSet.next()){
                            Toast.makeText(Dashboard.this, "The !st query is good", Toast.LENGTH_SHORT).show();
                            String query2 = "INSERT INTO Order_List (MIN,scanDate,PatientCode,PatientName,Status,Location)values('"+value+"',getDate(),'"+resultSet.getString("PatientCode")+"','"+resultSet.getString("PatientName")+"',1,'aggubaggu')";
                            Statement stmt2=connection.createStatement();
                            int z;
                            z = stmt2.executeUpdate(query2);
                            if (z>0){
                                Toast.makeText(Dashboard.this, "...wuba luba dub dub", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(Dashboard.this, "puftttt", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}
