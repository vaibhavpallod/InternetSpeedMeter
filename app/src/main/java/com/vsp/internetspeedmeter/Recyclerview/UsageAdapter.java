package com.vsp.internetspeedmeter.Recyclerview;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vsp.internetspeedmeter.Model.DisplayModel;
import com.vsp.internetspeedmeter.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UsageAdapter extends RecyclerView.Adapter<UsageAdapter.MyViewHolder> {
   Context context;
    DisplayModel display;

    public UsageAdapter(Context context, DisplayModel display) {
        this.context = context;
        this.display = display;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_recyclerview,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.date.setText(display.getDate().get(position));
        holder.mobile.setText(display.getMobile().get(position));
        holder.wifi.setText(display.getWifi().get(position));
        holder.total.setText(display.getTotal().get(position));


    }

    @Override
    public int getItemCount() {
        return display.getDate().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView date,mobile,wifi,total;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date=itemView.findViewById(R.id.dateTv);
            mobile=itemView.findViewById(R.id.mobileTv);
            wifi=itemView.findViewById(R.id.wifiTv);
            total=itemView.findViewById(R.id.totalTv);

        }
    }
}
