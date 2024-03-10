package com.example.newgalleryapp;

import static androidx.core.view.ViewCompat.setTransitionName;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class picture_Adapter extends RecyclerView.Adapter<PicHolder> {

    private ArrayList<pictureFacer> pictureList;
    private Context pictureContx;
    private final itemClickListener picListerner;

    public picture_Adapter(ArrayList<pictureFacer> pictureList, Context pictureContx, itemClickListener picListerner) {
        this.pictureList = pictureList;
        this.pictureContx = pictureContx;
        this.picListerner = picListerner;
    }

    @NonNull
    @Override
    public PicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View cell = inflater.inflate(R.layout.pic_holder_item, container,false);
        return new PicHolder(cell);
    }


    @Override
    public void onBindViewHolder(@NonNull PicHolder holder, int position) {
        final pictureFacer image = pictureList.get(position);

        Glide.with(pictureContx)
                .load(image.getPicturePath())
                .apply(new RequestOption().centerCrop())
                .into(holder.picture);

        setTransitionName(holder.picture,String.valueOf(position) + "_image");

        holder.picture.setOnClickListner(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                picListerner.onPicClicked(holder,position,pictureList);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }
}
