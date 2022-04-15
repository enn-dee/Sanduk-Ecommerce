package com.example.e_commerce.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Activities.DetailedActivity;
import com.example.e_commerce.R;
import com.example.e_commerce.models.MobileModel;

import java.util.List;

public class MobileAdapter extends RecyclerView.Adapter<MobileAdapter.ViewHolder> {
    private Context context;
    List<MobileModel> list;

    public MobileAdapter(Context context, List<MobileModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MobileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mobile_list,  parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MobileAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.mobImg);
        holder.mobName.setText(list.get(position).getName());

        holder.mobPrice.setText(String.valueOf(list.get(position).getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detailed",list.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mobImg;
        TextView mobName, mobPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mobImg = itemView.findViewById(R.id.mob_img);
            mobName = itemView.findViewById(R.id.mob_name);
            mobPrice = itemView.findViewById(R.id.mobPrice);


        }
    }
}
