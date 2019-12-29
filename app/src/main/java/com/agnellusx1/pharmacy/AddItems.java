package com.agnellusx1.pharmacy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.agnellusx1.pharmacy.Tabs.NormalTab;
import com.google.zxing.Result;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class AddItems extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA=1;
    private ZXingScannerView mScannerView;

    public String theAns,two,three,four,five,six;
    String BillNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);

        int currentApiVersion = Build.VERSION.SDK_INT;

        if ( currentApiVersion >= Build.VERSION_CODES.M)
        {
            if (checkPermission()) {
                Toast.makeText(AddItems.this, "Permission is Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                requestPermission();
            }
        }
    }
    private boolean checkPermission(){
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA)== PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{CAMERA},REQUEST_CAMERA);
    }

    @Override
    public void onResume(){
        super.onResume();

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if(currentapiVersion>=android.os.Build.VERSION_CODES.M){
            if(checkPermission()){
                if(mScannerView==null){
                    mScannerView=new ZXingScannerView(this);
                    setContentView(mScannerView);
                }
                mScannerView.setResultHandler(this);
                mScannerView.startCamera();
            }
            else{
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mScannerView.stopCamera();
    }

    public void onRequestPermissionsResult(final int requestCode, String[] permission, int [] grantResults){
        switch (requestCode){
            case REQUEST_CAMERA:
            if (grantResults.length>0){

                boolean cameraAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                if(cameraAccepted){
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                    {
                        if(shouldShowRequestPermissionRationale(CAMERA)){
                            displayAlertMessage("You need to allow access for both permissions",
                                    new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(new String[]{CAMERA},
                                            REQUEST_CAMERA);
                                }
                                }
                            });
                            return;
                        }
                    }
                }
            }
            break;
        }
    }




    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener){
        new androidx.appcompat.app.AlertDialog.Builder(AddItems.this)
                .setMessage(message)
                .setPositiveButton("Okay",listener)
                .setNegativeButton("Cancel",null)
                .create()
                .show();
    }


    @Override
    public void handleResult(final Result rawResult) {
        final String scanResult=rawResult.getText();



        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Add Order to Deliver");


        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                BillNo = rawResult.getText();
                CheckDB checkDB = new CheckDB();// this is the Asynctask, which is used to process in background to reduce load on app process
                checkDB.execute("");



                Intent intent = new Intent(AddItems.this, Dashboard.class);
                //intent.putExtra(MSG,theAns);
                startActivity(intent);
                finish();

            }
        });
//        builder.setNeutralButton("YES", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                //Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(scanResult));    agggus view
//                //startActivity(intent);
//                finish();
//            }
//        });
        builder.setNegativeButton("NO",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mScannerView.resumeCameraPreview(AddItems.this);
                finish();
            }
        });
        builder.setMessage(rawResult.getText());
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    public class CheckDB extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected void onPostExecute(String r)
        {
            Toast.makeText(AddItems.this, r, Toast.LENGTH_SHORT).show();
            if(isSuccess)
            {

                Toast.makeText(AddItems.this ,"data retrieved" , Toast.LENGTH_LONG).show();
                Log.d("tag1", String.valueOf(theAns));


            }
        }

        private  DBconnect DB;

        @Override
        protected String doInBackground(String... params)
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
                    String query = "SELECT top 1 * from Vw_PharmacyDeliveries where PatientCode ='"+BillNo+"'";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next())
                    {
                        z = "Login successful";
                        isSuccess=true;
//                        theAns = rs.getString("PatientName");
//                        two = rs.getString("MatlIndentNumber");
//                        three = rs.getString("PatientCode");
                        DB.insTable(rs.getString("MatlIssueNumber"),
                                rs.getString("PatientCode"),
                                rs.getString("PatientName"),
                                rs.getString("WardName"));
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
            return z;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddItems.this,Dashboard.class);
        startActivity(intent);
    }

}