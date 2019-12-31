package com.agnellusx1.pharmacy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.agnellusx1.pharmacy.Adapters.OrderSample;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Dashboard extends AppCompatActivity {
   String BillNo;
   String value="";
   public static final String MSG ="flag value";
   private DBconnect DB;
   private String nurse_id,nurse_pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        FloatingActionButton AddBtn = findViewById(R.id.AddBtn);
        FloatingActionButton VfyBtn = findViewById(R.id.VfyBtn);
        ImageButton searchButton=findViewById(R.id.searchButton);
        final EditText search=findViewById(R.id.search);

// delivery button
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value = "delivery";
                Intent addItems=new Intent(Dashboard.this,AddItems.class);
                addItems.putExtra(MSG,value);
                startActivity(addItems);
                finish();
            }
        });
//verify button
        VfyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value = "verify";
                LayoutInflater pop = LayoutInflater.from(Dashboard.this);
                final View nurseUI = pop.inflate(R.layout.nurse_login,null);
                final EditText nurseID = nurseUI.findViewById(R.id.NurseId);
                final EditText nursePASS = nurseUI.findViewById(R.id.NursePass);
                AlertDialog.Builder nursePop = new AlertDialog.Builder(Dashboard.this);
                nursePop.setTitle("Nurse Verification").setView(nurseUI)
                    .setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                nurse_id = nurseID.getText().toString();
                                nurse_pass = nursePASS.getText().toString();
                                if(nurse_id.trim().equals("")&&nurse_pass.trim().equals("")) {
                                    Toast.makeText(Dashboard.this, "Please Enter Details", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    try{
                                        DB = new DBconnect();
                                        Connection con = DB.connectionclass();
                                        String query = "select * from LoginTable where passwrd = '" + nurse_pass + "'and UserCode='"+ nurse_id +"' ";
                                        Statement stmt = con.createStatement();
                                        ResultSet rs = stmt.executeQuery(query);
                                        if(rs.next()){
                                            Intent addItems=new Intent(Dashboard.this,AddItems.class);
                                            addItems.putExtra(MSG,value);
                                            startActivity(addItems);
                                            finish();
                                        }

                                    }catch(Exception ex){
                                        Toast.makeText(Dashboard.this,ex.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }

                            }
                        });
                nursePop.show();
//                finish();
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
                    String query="Select * from Vw_PharmacyDeliveries where MatlIssueNumber='"+value+"'";
                    try {
                        Statement stmt = connection.createStatement();
                        ResultSet resultSet=stmt.executeQuery(query);
                        if(resultSet.next()){
                            Toast.makeText(Dashboard.this, "The !st query is good", Toast.LENGTH_SHORT).show();
                            String query2 = "INSERT INTO Pharmacy_status (MatlIssueNumber,scanDate,PatientCode,PatientName,Status,Location)values('"+value+"',getDate(),'"+resultSet.getString("PatientCode")+"','"+resultSet.getString("PatientName")+"',1,'"+resultSet.getString("WardName")+"')";
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
