package com.example.idpfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

int totalprice;

    Context context;
    ArrayList<products> list;

    public MyAdapter(Context context, ArrayList<products> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            products product=list.get(position);
            holder.productname.setText(product.getPname());
            holder.Price.setText(product.getPrice());
            holder.Quantity.setText(product.getQuantity());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView productname,Price,Quantity;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            productname=itemView.findViewById(R.id.Productname);
            Price=itemView.findViewById(R.id.price);
            Quantity=itemView.findViewById(R.id.quantity);

        }
    }

}
