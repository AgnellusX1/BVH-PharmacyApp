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
            ConnectionURL = "jdbc:jtds:sqlserver://192.168.5.19:49429;database=DietApp;user=DietApp;password=BvhApp@123";
//            jdbc:jtds:sqlserver://sql5050.site4now.net;database=DB_A511EF_TestApp;user=DB_A511EF_TestApp_admin;password=MyPassword123
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

    public boolean insTable (String MIN1,String PC,String PN,String loc,String MatIndentNo)  {


        try {
            Connection conn = connectionclass();
            String query="";
            Statement stmt;
            // int flag =1;
            stmt = conn.createStatement();
            // if(DietRqstNumber!=null) {
            query = "INSERT INTO Pharmacy_status (MatlIssueNumber,scanDate,PatientCode,PatientName,Status,WardName,MatlIndentNumber,scanUser) values('"+MIN1+"',getDate(),'"+PC+"','"+PN+"',1,'"+loc+"','"+MatIndentNo+"','"+MainActivity.scanUserName+"')";
//            INSERT INTO Order_List (MIN,scanDate,PatientCode,PatientName,Status,Location) values('"+MIN1+"',getDate(),'"+PC+"','"+PN+"',1,'"+loc+"')
            int success;
            success = stmt.executeUpdate(query);
            if(success > 0)
                return true;
            else
                return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            Log.e("error",e.toString());
        }

        return true;
    }

    public boolean delvrySuccess(String MIN1){
        try {
            Connection conn = connectionclass();
            String query="";
            Statement stmt;
            // int flag =1;
            stmt = conn.createStatement();
            // if(DietRqstNumber!=null) {
            query = "UPDATE Pharmacy_status SET Status = '0' WHERE MatlIssueNumber ='"+MIN1+"'";
            int success;
            success = stmt.executeUpdate(query);
            if(success > 0)
                return true;
            else
                return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            Log.e("error",e.toString());
        }
        return true;
    }


}
