package com.agnellusx1.pharmacy;
//import com.agnellusx1.pharmacy.AddItems.Example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    Button Add;
//    private AddItems.Example;
//    String x = Example.ans;
//    String BillNum;
//    TextView Bitems;
//    public String flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
//        Log.d("Tag6",String.valueOf(flag));


//        Bitems = (TextView)findViewById(R.id.textView);
//        Log.d("Tag4","onCreate invoked");
//        if (flag != ""){
//            Log.d("Tag5","IF invoked");
//            //Intent getAns = getIntent();
//            //Bitems.setText((CharSequence) getAns);
//
//            Log.d("Tag3", String.valueOf(Example.ans));
//            Bitems.setText(Example.ans);
//            flag = "";
//
//        }



        Button add=findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                flag = "Dallu";
                Intent addItems=new Intent(Dashboard.this,AddItems.class);
                startActivity(addItems);
            }
        });
    }



}
