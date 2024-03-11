package com.example.newgalleryapp;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerViewPagerImageIndicator extends RecyclerView.Adapter<indicatorHolder> {

    ArrayList<pictureFacer> pictureList;
    Context pictureContx;
    private final imageIndicatorListener imageListerner;

    public recyclerViewPagerImageIndicator(ArrayList<pictureFacer> pictureList, Context pictureContx, imageIndicatorListener imageListerner) {
        this.pictureList = pictureList;
        this.pictureContx = pictureContx;
        this.imageListerner = imageListerner;
    }

    @NonNull
    @Override
    public indicatorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View cell = inflater.inflate(R.layout.indicator_holder,parent,false);
        return new indicatorHolder(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull indicatorHolder holder, int position) {
        final pictureFacer pic = pictureList.get(position);
        holder.positionController.setBackgroundColor(pic.getSelected()? Color.parseColor("#0000000");

        Glide.with(pictureContx)
                .load(pic.getPicturePath())
                .apply(new RequestOption().centerCrop())
                .into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pic.setSelected(true);
                notifyDataSetChanged();
                imageListerner.onImageIndicatorCicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }
}
