package com.rgi.hanumanchalisa.adapter;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.rgi.hanumanchalisa.R;

import java.io.IOException;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{
    private Context context;
    ArrayList<Integer> advertiseList;
    public ImageAdapter(Context context, ArrayList<Integer> advertiseList) {
        this.context = context;
        this.advertiseList = advertiseList;
    }

    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder holder, int position) {
        holder.img_android.setImageResource(advertiseList.get(position));
    }

    @Override
    public int getItemCount() {
        return advertiseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img_android;
        public ViewHolder(View view) {
            super(view);
            img_android = (ImageView) view.findViewById(R.id.img_android);
            img_android.setOnClickListener(v->{
                 WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
                try {
                    wallpaperManager.setResource(advertiseList.get(getAdapterPosition()));
                    Toast.makeText(context, "Image set to background", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

    }
}