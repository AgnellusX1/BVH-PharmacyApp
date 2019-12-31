package com.agnellusx1.pharmacy.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.agnellusx1.pharmacy.DBconnect;
import com.agnellusx1.pharmacy.Dashboard;
import com.agnellusx1.pharmacy.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.NormalViewHolder> {

    private DBconnect DB;
    DialogInterface.OnClickListener listener;
    List<OrderSample> Samples;
    private Context Context;
    private String nurse_pass,nurse_id;

    public PatientAdapter(Context mContext, ArrayList<OrderSample>mSamples) {
        Samples = mSamples;
        Context = mContext;
    }



    @NonNull
    @Override
    public PatientAdapter.NormalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        //View v = inflater.inflate(R.layout.patient_card,parent,false);
        return new NormalViewHolder(LayoutInflater.from(Context).inflate(R.layout.patient_card,parent,false)) ;
        //ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientAdapter.NormalViewHolder holder, int position) {
        final OrderSample orderSample = Samples.get(position);
        holder.Location.setText(orderSample.getLocation());
        holder.Name.setText(orderSample.getName());
        holder.BillNumber.setText(orderSample.getBillNumber());

    }

    @Override
    public int getItemCount() {
        return Samples.size();
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder/* implements View.OnClickListener */{
        TextView Location,Name,BillNumber;
        Context mContext;
        View layout;
        public NormalViewHolder(@NonNull View itemView) {
            super(itemView);
            //itemView.setOnClickListener(this);

            layout=itemView;
            Location=itemView.findViewById(R.id.location);
            Name=itemView.findViewById(R.id.pName);
            BillNumber=itemView.findViewById(R.id.billNo);
        }
    }
}

