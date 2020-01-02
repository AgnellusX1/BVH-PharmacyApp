package com.agnellusx1.pharmacy;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.Cache;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity
{
    public static String scanUserName;
    // Declaring layout button, edit texts
    //public String scanUserName;
    Button login;
    EditText username,password;
    ProgressBar progressBar;
    String flag = "";
    // End Declaring layout button, edit texts

    // Declaring connection variables
    Connection con;
    String un,pass,db,ip;
    String usernam,passwordd;
    //End Declaring connection variables
    private DBconnect DB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Dashboard.cacheCheck.equals("C")){

            Dashboard.cacheCheck = "DD";
            SharedPreferences Cache = getSharedPreferences("memory",MODE_PRIVATE);
            SharedPreferences.Editor editor = Cache.edit();
            editor.putString("uname","");
            editor.putString("pass","");
            editor.apply();
        }

        // Getting values from button, texts and progress bar
        login = findViewById(R.id.dLogin);
        username =findViewById(R.id.userid);
        password =  findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        // End Getting values from button, texts and progress bar

        // Declaring Server ip, username, database name and password
        ip = "192.168.1.9";
        db = "msss";
        un = "DESKTOP-O439JCP/user";
        pass = "";
        // Declaring Server ip, username, database name and password


        // Setting up the function when button login is clicked
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                usernam = username.getText().toString();
                passwordd = password.getText().toString();
                CheckLogin checkLogin = new CheckLogin();// this is the Asynctask, which is used to process in background to reduce load on app process
                checkLogin.execute("");

                scanUserName = usernam;

                SharedPreferences Cache = getSharedPreferences("memory",MODE_PRIVATE);
                SharedPreferences.Editor editor = Cache.edit();

                if (Dashboard.cacheCheck.equals("DD")){
                    editor.putString("uname",usernam);
                    editor.putString("pass",passwordd);
                    editor.apply();
                }
            }
        });
        //End Setting up the function when button login is clicked


        //now to get values of SharedPreferences

        SharedPreferences getShared = getSharedPreferences("memory",MODE_PRIVATE);
        String saved1 = getShared.getString("uname","itdept");
        String saved2 = getShared.getString("pass","ITDEPT");

        username.setText(saved1);
        password.setText(saved2);

    }

    public class CheckLogin extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute()

        {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r)
        {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            if(isSuccess)
            {
                Intent intent = new Intent(MainActivity.this,Dashboard.class);
                startActivity(intent);
                finish();
                //Toast.makeText(MainActivity.this , "Login Successfull" , Toast.LENGTH_LONG).show();
                //finish();
            }
        }
        @Override
        protected String doInBackground(String... params)
        {

            if(usernam.trim().equals("")|| passwordd.trim().equals(""))
                z = "Please enter Username and Password";
            else
            {
                try
                {
                    DB = new DBconnect();
                    Connection con = DB.connectionclass();        // Connect to database
                    if (con == null)
                    {
                        z = "Check Your Internet Access!";
                    }
                    else
                    {
                        String query = "select * from LoginTable where UserCode = '" + usernam+ "' and Passwrd = '"+ passwordd +"' ";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if(rs.next())
                        {
                            z = "Login successful";
                            isSuccess=true;
                            con.close();
                        }
                        else
                        {
                            z = "Invalid Credentials!";
                            isSuccess = false;
                            con.close();
                        }
                    }
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    z = ex.getMessage();
                }
            }
            return z;
        }
    }
}