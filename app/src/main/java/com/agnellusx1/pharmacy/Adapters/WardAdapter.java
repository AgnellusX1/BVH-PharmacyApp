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

public class WardAdapter extends RecyclerView.Adapter<WardAdapter.WardViewHolder>{
    private DBconnect DB;
    DialogInterface.OnClickListener listener;
    List<OrderSample> Samples;
    private Context Context;
    private String nurse_pass,nurse_id;

    public WardAdapter(Context mContext, ArrayList<OrderSample> mSamples) {
        Samples = mSamples;
        Context = mContext;
    }



    @NonNull
    @Override
    public WardAdapter.WardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        //View v = inflater.inflate(R.layout.Ward_card,parent,false);
        return new WardAdapter.WardViewHolder(LayoutInflater.from(Context).inflate(R.layout.ward_card,parent,false)) ;
        //ViewHolder(v);
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
        android.content.Context mContext;
        View layout;
        public WardViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView;
            Location=itemView.findViewById(R.id.location);
            Name=itemView.findViewById(R.id.pName);
            BillNumber=itemView.findViewById(R.id.billNo);
        }

//        @Override
//        public void onClick(View v) {
//
//            LayoutInflater pop= LayoutInflater.from(Context);
//
//            final View nurseUI=pop.inflate(R.layout.nurse_login,null);
//            final EditText nurseID=nurseUI.findViewById(R.id.NurseId);
//            final EditText nursePASS=nurseUI.findViewById(R.id.NursePass);
//
//            AlertDialog.Builder nursePop=new AlertDialog.Builder(Context);
//            nursePop.setTitle("Login To Validate Order").setView(nurseUI)
//                    .setPositiveButton("Login", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            nurse_id = nurseID.getText().toString();
//                            nurse_pass = nursePASS.getText().toString();
//                    if(nurse_pass.trim().equals("")&&nurse_pass.trim().equals(""))
//                        Toast.makeText(Context ,"Please Enter Details" , Toast.LENGTH_LONG).show();
//
//                    else
//                    {
//                        try
//                        {
//                            DB = new DBconnect();
//                            Connection con = DB.connectionclass();        // Connect to database
//                            if (con == null)
//                            {
//                                Toast.makeText(Context ,"Check Internet Access" , Toast.LENGTH_LONG).show();                            }
//                            else
//                            {
//                                String query = "select * from Nurse_Auth where pass = '" + nurse_pass + "'and userName='"+ nurse_id +"' ";
//                                Statement stmt = con.createStatement();
//                                ResultSet rs = stmt.executeQuery(query);
//                                if(rs.next())
//                                {
//                                    Toast.makeText(Context ,"Delivery Complete" , Toast.LENGTH_LONG).show();
//                                    OrderSample orderSample = Samples.get(getAdapterPosition());
//                                    String query1 = "UPDATE Order_List SET Status = '0' WHERE WardCode ='"+orderSample.LOCATION +"'";
//                                    Statement stmt1 = con.createStatement();
//                                    stmt1.executeUpdate(query1);
//                                    con.close();
//                                    Intent intent = new Intent(Context, Dashboard.class);
//                                    Context.startActivity(intent);
//                                    ((Activity) Context).finish();
//
//
//
//
//                                }
//                                else
//                                {
//                                    Toast.makeText(Context ,"Invalid Credentials!" , Toast.LENGTH_LONG).show();
//                                    con.close();
//                                }
//                            }
//                        }
//                        catch (Exception ex)
//                        {
//                            Toast.makeText(Context ,ex.getMessage() , Toast.LENGTH_LONG).show();
//                        }
//                    }
//                        }
//                    })
//                    .setNegativeButton("Cancel",
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dialogInterface.cancel();
//                                }
//                            });
//            nursePop.show();
//
//
////            new androidx.appcompat.app.AlertDialog.Builder(Context)
////                    .setMessage("Validate Delivery"+ getPosition());
////
////            final EditText input = new EditText(Context);
////// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
////            final AlertDialog.Builder builder=new AlertDialog.Builder(Context);
////            input.setInputType(InputType.TYPE_CLASS_TEXT);
////            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
////            builder.setView(input);
////            builder.setTitle("Validate Delivery"+ getAdapterPosition());
////
////// Set up the buttons
////            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
////                @Override
////                public void onClick(DialogInterface dialog, int which) {
////                    nurse_pass = input.getText().toString();
////
////                    if(nurse_pass.trim().equals(""))
////                        Toast.makeText(Context ,"Enter Nurse Password" , Toast.LENGTH_LONG).show();
////                    else
////                    {
////                        try
////                        {
////                            DB = new DBconnect();
////                            Connection con = DB.connectionclass();        // Connect to database
////                            if (con == null)
////                            {
////                                Toast.makeText(Context ,"Check Internet Access" , Toast.LENGTH_LONG).show();                            }
////                            else
////                            {
////                                String query = "select * from Nurse_Auth where pass = '" + nurse_pass+ "'";
////                                Statement stmt = con.createStatement();
////                                ResultSet rs = stmt.executeQuery(query);
////                                if(rs.next())
////                                {
////                                    Toast.makeText(Context ,"Delivery Complete" , Toast.LENGTH_LONG).show();
////                                    OrderSample orderSample = Samples.get(getAdapterPosition());
////                                    String query1 = "UPDATE Order_List SET Status = '0' WHERE WardCode ='"+orderSample.LOCATION +"'";
////                                    Statement stmt1 = con.createStatement();
////                                    stmt1.executeUpdate(query1);
////                                    con.close();
////                                }
////                                else
////                                {
////                                    Toast.makeText(Context ,"Invalid Credentials!" , Toast.LENGTH_LONG).show();
////                                    con.close();
////                                }
////                            }
////                        }
////                        catch (Exception ex)
////                        {
////                            Toast.makeText(Context ,ex.getMessage() , Toast.LENGTH_LONG).show();
////                        }
////                    }
////                }
////            });
////            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
////                @Override
////                public void onClick(DialogInterface dialog, int which) {
////                    dialog.cancel();
////                }
////            });
////            builder.show();
//        }
    }
}
