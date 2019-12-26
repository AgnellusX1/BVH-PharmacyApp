package com.agnellusx1.pharmacy;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBconnect {

    @SuppressLint("NewApi")
    public Connection connectionclass()// parameters (String user, String password, String database, String server)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            ConnectionURL = "jdbc:jtds:sqlserver://sql5050.site4now.net;database=DB_A511EF_TestApp;user=DB_A511EF_TestApp_admin;password=MyPassword123";
                    //192.168.5.19:49429;database=DietApp;user=DietApp;password=BvhApp@123";
//            ConnectionURL = "jdbc:jtds:sqlserver://192.168.1.9;database=msss;instance=SQLEXPRESS;Network Protocol=NamedPipes" ;


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

    public static String pickQuerry (String inputQ) throws Exception {

        String z;
        String ans = null;
        DBconnect DB = new DBconnect();
        Connection con = DB.connectionclass();
        if (con == null)
        {
            z = "Check Your Internet Access!";
        }
        else
        {
            String query = "select * from Vw_PharmacyDeliveries where PatientCode = '" + inputQ;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next())
            {
                ans = rs.getString("PatientName ");
                con.close();
            }
            else
            {
                z = "Invalid Credentials!";
                con.close();
            }

        }
        return ans;
    }



}
