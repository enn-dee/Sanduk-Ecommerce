package com.example.e_commerce.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;
import com.example.e_commerce.models.MyCartModel;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
 Context context;
List<MyCartModel> modelList;
int totalAmount = 0;

    public MyCartAdapter(Context context, List<MyCartModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, int position) {

holder.name.setText(modelList.get(position).getProductName());
holder.price.setText(modelList.get(position).getTotalPrice()+"$");
holder.time.setText(modelList.get(position).getCurrentTime());
holder.date.setText(modelList.get(position).getCurrentDate());
holder.totalQuantity.setText(modelList.get(position).getTotalQuantity());
holder.totalPrice.setText(String.valueOf(modelList.get(position).getTotalPrice()));

// total amount pass to cart activity
        totalAmount = totalAmount+modelList.get(position).getTotalPrice();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalAmount);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
TextView name , price, date, time, totalQuantity, totalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
name = itemView.findViewById(R.id.product_name);
price = itemView.findViewById(R.id.product_price);
time = itemView.findViewById(R.id.current_time);
date = itemView.findViewById(R.id.current_date);
totalQuantity = itemView.findViewById(R.id.total_quantity);
totalPrice = itemView.findViewById(R.id.product_price);

        }


    }
}
