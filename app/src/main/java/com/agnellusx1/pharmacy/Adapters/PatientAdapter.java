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

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.NormalViewHolder> {

    List<OrderSample> Samples;
    private Context Context;

    public PatientAdapter(Context mContext, ArrayList<OrderSample> mSamples) {
        Samples = mSamples;
        Context = mContext;
    }


    @NonNull
    @Override
    public PatientAdapter.NormalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NormalViewHolder(LayoutInflater.from(Context).inflate(R.layout.patient_card, parent, false));
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
        TextView Location, Name, BillNumber;
        View layout;

        public NormalViewHolder(@NonNull View itemView) {
            super(itemView);

            layout=itemView;
            Location=itemView.findViewById(R.id.location);
            Name=itemView.findViewById(R.id.pName);
            BillNumber=itemView.findViewById(R.id.billNo);
        }
    }
}