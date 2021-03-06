package com.rgi.hanumanchalisa.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rgi.hanumanchalisa.R;

import java.util.ArrayList;

public class AdvertiseBannerAdapter extends RecyclerView.Adapter<AdvertiseBannerAdapter.viewHolder> {
    Context context;
    ArrayList<Integer> advertises;

    public AdvertiseBannerAdapter(Context context, ArrayList<Integer> advertises) {
        this.context = context;
        this.advertises = advertises;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false);
        return new viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
      /*  Glide.with(context)
                .load(advertises.get(position))
                .into(holder.adImage);*/
        holder.adImage.setImageResource(advertises.get(position));
    }

    @Override
    public int getItemCount() {
        return advertises.size();

    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView adImage;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            adImage = itemView.findViewById(R.id.advertiseImage);
        }
    }
}
