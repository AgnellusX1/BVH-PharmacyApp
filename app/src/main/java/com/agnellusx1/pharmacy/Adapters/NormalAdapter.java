package com.agnellusx1.pharmacy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agnellusx1.pharmacy.R;

import org.w3c.dom.Text;

import java.util.List;

public class NormalAdapter extends RecyclerView.Adapter<NormalAdapter.ViewHolder> {
    private List<OrderSample> values;
    public Context mContext;

    @NonNull
    @Override
    public NormalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.fragment_normal_tab,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NormalAdapter.ViewHolder holder, int position) {
        final OrderSample orderSample = values.get(position);
        holder.Location.setText(orderSample.getLocation());
        holder.Name.setText(orderSample.getName());
        holder.BillNumber.setText(orderSample.getBillNumber());
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Location,Name,BillNumber;
        View layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout=itemView;
            Location=itemView.findViewById(R.id.location);
            Name=itemView.findViewById(R.id.pName);
            BillNumber=itemView.findViewById(R.id.billNo);

        }
    }
}
