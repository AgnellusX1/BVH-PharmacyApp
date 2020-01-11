package com.agnellusx1.pharmacy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class Developers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);

        TextView dev1=findViewById(R.id.Dev1);
        dev1.setClickable(true);
        dev1.setMovementMethod(LinkMovementMethod.getInstance());
        String link1="<a href='https://www.linkedin.com/in/agnellus-fernandes-81232b192'>Agnellus Fernandes</a>";
        dev1.setText(Html.fromHtml(link1));

        TextView dev2=findViewById(R.id.Dev2);
        dev2.setClickable(true);
        dev2.setMovementMethod(LinkMovementMethod.getInstance());
        String link2="<a href='http://linkedin.com/in/amrut-savadatti-277069183'>Amrut Savadatti</a>";
        dev2.setText(Html.fromHtml(link2));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(Developers.this,Dashboard.class);
        startActivity(intent);
        finish();
    }
}
