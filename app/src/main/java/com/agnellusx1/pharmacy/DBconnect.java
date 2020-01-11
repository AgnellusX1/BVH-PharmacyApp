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

    public boolean insTable (String MIN1,String PC,String PN,String loc,String MatIndentNo,String Mdate)  {


        try {
            Connection conn = connectionclass();
            String query="";
            Statement stmt;
            stmt = conn.createStatement();
            query = "INSERT INTO Pharmacy_status (MatlIssueNumber,scanDate,PatientCode,PatientName,Status,WardName,MatlIndentNumber,scanUser,MatlIssueDate) values('"+MIN1+"',GETDATE(),'"+PC+"','"+PN+"',1,'"+loc+"','"+MatIndentNo+"','"+MainActivity.scanUserName+"','"+Mdate+"')";
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
            String query,TATquery,TATentryQ,startTime,endTime="";
            Statement stmt;
            stmt = conn.createStatement();

            query = "UPDATE Pharmacy_status SET DeliveryEnd = GETDATE(),RecievedUser = '"+Dashboard.NurseEntry+"' WHERE MatlIssueNumber ='"+MIN1+"'";
            TATquery="SELECT * from Pharmacy_status where MatlIssueNumber = '"+MIN1+"'";
            int success;
            success = stmt.executeUpdate(query);
            if(success > 0){
                ResultSet rs = stmt.executeQuery(TATquery);
                if (rs.next()){
                    startTime = rs.getString("scanDate");
                    endTime = rs.getString("DeliveryEnd");
                    TATentryQ = "UPDATE Pharmacy_status SET Status = '0', Tat = DATEDIFF(mi, '"+startTime+"', '"+endTime+"') WHERE MatlIssueNumber ='"+MIN1+"'";
                    int successTAT = stmt.executeUpdate(TATentryQ);
                }
                return true;
            }

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
