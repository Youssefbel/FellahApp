package com.example.ifarm.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ifarm.R;
import com.example.ifarm.model.ProductModel;
import com.example.ifarm.ui.ProductDetails;

import java.util.ArrayList;

public class RecyclerViewAdapterProduct extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<ProductModel> data;

    public RecyclerViewAdapterProduct(Context context, ArrayList<ProductModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_fields, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        final ProductModel model = data.get(position);

        holder.tv_name.setText(model.getName());
        holder.tv_superficie.setText(model.getSuperficie());
        holder.tv_production.setText(model.getProduction());
        holder.tv_percentage.setText(model.getPercentage());

        holder.iv_icon.setImageResource(model.getIcon());
        holder.iv_trending_icon.setImageResource(model.getTrendingIcon());
        holder.ll_container.setBackgroundResource(model.getColor());

        holder.ll_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra("model", model);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_superficie, tv_production, tv_percentage;
        ImageView iv_icon, iv_trending_icon;
        LinearLayout ll_container;

        public ViewHolder(View view) {
            super(view);

            tv_name = view.findViewById(R.id.tv_name);
            tv_superficie = view.findViewById(R.id.tv_superficie);
            tv_production = view.findViewById(R.id.tv_production);
            tv_percentage = view.findViewById(R.id.tv_percentage);
            iv_icon = view.findViewById(R.id.iv_icon);
            iv_trending_icon = view.findViewById(R.id.iv_trending_icon);
            ll_container = view.findViewById(R.id.ll_container);
        }
    }
}
