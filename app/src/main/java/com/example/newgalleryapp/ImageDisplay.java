package com.example.newgalleryapp;

import android.os.Build;
import android.transition.Fade;

import java.util.ArrayList;

public class ImageDisplay implements itemClickListener{

    @Override
    public void onPicClicked(PicHolder holder, int position, ArrayList<pictureFacer> pics) {
        pictureBrowserFragment brower = pictureBrowserFragment.newInstance(pics,position,ImageDisplay.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            brower.setEnterTransition(new Fade());
            brower.setExitTransition(new Fade());
        }

        getSupportFragmentManager()
                .beingTranscation()
                .addSharedElement(holder.picture,position+"picture")
                .add(R.id.displayContainer, brower)
                .addToBackStack(null)
                .commit();
    }
}
