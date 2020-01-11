package com.agnellusx1.pharmacy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agnellusx1.pharmacy.R;

import java.util.ArrayList;
import java.util.List;

public class WardAdapter extends RecyclerView.Adapter<WardAdapter.WardViewHolder>{
    List<OrderSample> Samples;
    private Context Context;

    public WardAdapter(Context mContext, ArrayList<OrderSample> mSamples) {
        Samples = mSamples;
        Context = mContext;
    }



    @NonNull
    @Override
    public WardAdapter.WardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WardAdapter.WardViewHolder(LayoutInflater.from(Context).inflate(R.layout.ward_card,parent,false)) ;
    }

    @Override
    public void onBindViewHolder(@NonNull WardAdapter.WardViewHolder holder, int position) {
        final OrderSample orderSample = Samples.get(position);
        holder.Location.setText(orderSample.getLocation());
        holder.Name.setText(orderSample.getName());
        holder.BillNumber.setText(orderSample.getBillNumber());

    }

    @Override
    public int getItemCount() {
        return Samples.size();
    }

    public class WardViewHolder extends RecyclerView.ViewHolder{
        TextView Location,Name,BillNumber;
        View layout;
        public WardViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView;
            Location=itemView.findViewById(R.id.location);
            Name=itemView.findViewById(R.id.pName);
            BillNumber=itemView.findViewById(R.id.billNo);
        }
    }
}
