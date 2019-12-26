package com.agnellusx1.pharmacy;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import net.sourceforge.jtds.jdbc.*;

public class MainActivity extends AppCompatActivity
{
    // Declaring layout button, edit texts
    Button login;
    EditText username,password;
    ProgressBar progressBar;
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

        // Getting values from button, texts and progress bar
        login = (Button) findViewById(R.id.dLogin);
        username = (EditText) findViewById(R.id.userid);
        password = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
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

                SharedPreferences Cache = getSharedPreferences("memory",MODE_PRIVATE);
                SharedPreferences.Editor editor = Cache.edit();

                editor.putString("uname",usernam);
                editor.putString("pass",passwordd);
                editor.apply();
            }
        });
        //End Setting up the function when button login is clicked


        //now to get values of SharedPreferences
        SharedPreferences getShared = getSharedPreferences("memory",MODE_PRIVATE);
        String saved1 = getShared.getString("uname","");
        String saved2 = getShared.getString("pass","");

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
                        String query = "select * from Vw_PharmacyDeliveries where PatientCode = '" + usernam.toString() + "' and PatientName = '"+ passwordd.toString() +"' ";
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


//    @SuppressLint("NewApi")
//    public Connection connectionclass(String user, String password, String database, String server)
//    {
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//        Connection connection = null;
//        String ConnectionURL = null;
//        try
//        {
//            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
//            ConnectionURL = "jdbc:jtds:sqlserver://192.168.5.19:49429;database=DietApp;user=DietApp;password=BvhApp@123";
////            ConnectionURL = "jdbc:jtds:sqlserver://192.168.1.9;database=msss;instance=SQLEXPRESS;Network Protocol=NamedPipes" ;
//
//
//            connection = DriverManager.getConnection(ConnectionURL);
//        }
//        catch (SQLException se)
//        {
//            Log.e("error here 1 : ", se.getMessage());
//        }
//        catch (ClassNotFoundException e)
//        {
//            Log.e("error here 2 : ", e.getMessage());
//        }
//        catch (Exception e)
//        {
//            Log.e("error here 3 : ", e.getMessage());
//        }
//        return connection;
//    }
}