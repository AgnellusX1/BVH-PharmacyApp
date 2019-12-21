package com.agnellusx1.pharmacy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    Connection con;
    String userId;
    String pass;

    String dbUser,dbPass,dbMain,ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Layout Elements Reference
        Button login = findViewById(R.id.dLogin);
        final EditText mUserId = findViewById(R.id.userid);
        final EditText mPass = findViewById(R.id.password);

        //Database Connection Variables
         dbUser = "DietApp";
         dbPass = "BvhApp@123";
         dbMain = "DietApp";
         ip = "192.168.5.19:49429;";


        //On Button Click
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userId = mUserId.getText().toString();
                pass = mPass.getText().toString();
//                CheckLogin checkLogin=new CheckLogin();
//                checkLogin.execute("");
                Intent dashboard=new Intent(MainActivity.this,Dashboard.class);
                startActivity(dashboard);
            }
        });
    }


    public class CheckLogin extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            String z = "";
            Boolean isSuccess = false;

            if (userId.trim().equals("") || pass.trim().equals(""))
                z = "Please enter Username and Password";
            else {
                try {
                    con = connectionclass(userId,pass,dbMain,ip);        // Connect to database
                    if (con == null) {
                        z = "Check Your Internet Access!";
                    } else {
                        String query = "select * from login where username= '" + userId + "' and password = '" + pass + "' ";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs.next()) {
                            z = "Login successful";
                            isSuccess = true;
                            con.close();
                        } else {
                            z = "Invalid Credentials!";
                            isSuccess = false;
                        }
                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    z = ex.getMessage();
                }
            }
            return z;
        }
    }

    @SuppressLint("NewApi")
    public Connection connectionclass(String user, String password, String database, String server)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            //ConnectionURL = "jdbc:jtds:sqlserver://sql5009.mywindowshosting.com;database=DB_A2C00B_login;user=DB_A2C00B_login_admin;password=login@123";

            connection = DriverManager.getConnection(ConnectionURL);
        }
        catch (SQLException se)
        {
            Log.e("error here 1 : ", se.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error here 2 : ", e.getMessage());
        }
        catch (Exception e)
        {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }
}
