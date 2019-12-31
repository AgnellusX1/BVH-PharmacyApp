package com.agnellusx1.pharmacy.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agnellusx1.pharmacy.DBconnect;
import com.agnellusx1.pharmacy.R;

import java.util.ArrayList;
import java.util.List;

public class DeliveredAdapter extends RecyclerView.Adapter<DeliveredAdapter.DeliveredViewHolder> {

    List<OrderSample> Samples;
    private Context Context;

    public DeliveredAdapter(Context mContext, ArrayList<OrderSample> mSamples) {
        Samples = mSamples;
        Context = mContext;
    }

    @NonNull
    @Override
    public DeliveredAdapter.DeliveredViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        //View v = inflater.inflate(R.layout.patient_card,parent,false);
        return new DeliveredAdapter.DeliveredViewHolder(LayoutInflater.from(Context).inflate(R.layout.delivered_card,parent,false)) ;
        //ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveredAdapter.DeliveredViewHolder holder, int position) {
        final OrderSample orderSample = Samples.get(position);
        holder.Location.setText(orderSample.getLocation());
        holder.Name.setText(orderSample.getName());
        holder.BillNumber.setText(orderSample.getBillNumber());

    }

    @Override
    public int getItemCount() {
        return Samples.size();
    }

    public class DeliveredViewHolder extends RecyclerView.ViewHolder{
        TextView Location,Name,BillNumber;
        android.content.Context mContext;
        View layout;
        public DeliveredViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView;
            Location=itemView.findViewById(R.id.location);
            Name=itemView.findViewById(R.id.pName);
            BillNumber=itemView.findViewById(R.id.billNo);
        }
    }
}

