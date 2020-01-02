package com.agnellusx1.pharmacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Dashboard extends AppCompatActivity {
   String value="";
   public static final String MSG ="flag value";
   private DBconnect DB;
   private String nurse_id,nurse_pass;
   public static final String ClrData = "Data clear info";
   public static String cacheCheck = "DD";


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.Logout:
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                cacheCheck = "C";
                startActivity(intent);
                finish();

                return (true);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        TextView AgentName=findViewById(R.id.AgentId);
        FloatingActionButton AddBtn = findViewById(R.id.AddBtn);
        FloatingActionButton VfyBtn = findViewById(R.id.VfyBtn);
        Toolbar toolbar=findViewById(R.id.actionbar);
        setSupportActionBar(toolbar);

        AgentName.setText(MainActivity.scanUserName);

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
                                        else{
                                            Toast.makeText(Dashboard.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                                        }

                                    }catch(Exception ex){
                                        Toast.makeText(Dashboard.this,ex.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }

                            }
                        });
                nursePop.show();
            }
        });

    }
}
