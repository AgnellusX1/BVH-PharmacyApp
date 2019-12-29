package com.agnellusx1.pharmacy;
//import com.agnellusx1.pharmacy.AddItems.Example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.agnellusx1.pharmacy.Adapters.NormalAdapter;
import com.agnellusx1.pharmacy.Adapters.OrderSample;
import com.agnellusx1.pharmacy.Adapters.TabAdapter;
import com.agnellusx1.pharmacy.Tabs.FastTrackTab;
import com.agnellusx1.pharmacy.Tabs.NormalTab;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class Dashboard extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabAdapter tabAdapter;
    FloatingActionButton AddBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        FloatingActionButton AddBtn = findViewById(R.id.AddBtn);

        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addItems=new Intent(Dashboard.this,AddItems.class);
                startActivity(addItems);
                finish();
            }
        });
    }



}
