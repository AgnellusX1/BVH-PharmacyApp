package com.agnellusx1.pharmacy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class AddItems extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA=1;
    private ZXingScannerView mScannerView;


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
    public void handleResult(Result rawResult) {
        final String scanResult=rawResult.getText();



        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Add Order to Deliver");
        builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mScannerView.resumeCameraPreview(AddItems.this);
            }
        });
        builder.setNeutralButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(scanResult));
                startActivity(intent);
            }
        });
        builder.setMessage(rawResult.getText());
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}