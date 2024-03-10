package com.example.newgalleryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements itemClickListener{

    RecyclerView folderRecycler;
    TextView empty;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

        empty = findViewById(R.id.empty);

        folderRecycler = findViewById(R.id.folderRecycler);
        folderRecycler.addItemDecoration(new MarginDecoration(this));
        folderRecycler.hasFixedSize();
        ArrayList<imageFolder> folds = getPicturePath();

        if(folds.isEmpty()){
            empty.setVisibility(View.VISIBLE);
        }else {
            RecyclerView.Adapter folderAdapter = new pictureFolderAdapter(folds, MainActivity.this,this);
            folderRecycler.setAdapter(folderAdapter);
        }

        private ArrayList<imageFolder> getPicturePaths(){
            ArrayList<imageFolder> picFolders = new ArrayList<imageFolder>();
            ArrayList<String> picPaths = new ArrayList<>();
            Uri allImagesuri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String[] projection = {MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME,MediaStore.Images.Media.BUCKET_ID};
            Cursor cursor = this.getContentResolver().query(allImagesuri,projection,null,null,null);
            try {
                if (cursor != null) {
                    cursor.moveToFirst();
                }
                do{
                    imageFolder folds = new imageFolder();
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                    String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                    String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                    String folderpaths = datapath.substring(0,datapath.lastIndexOf(folder+"/"));
                    folderpaths = folderpaths+folder+"/";
                    if (!picPaths.contains(folderpaths)){
                        picPaths.add(folderpaths);

                        folds.setPath(folderpaths);
                        folds.setFolderName(folder);
                        folds.seTFirstPic(datapath);
                        folds.addpics();
                        picFolders.add(folds);
                    } else {
                        for (int i = 0; i<picFolders.size();i++){
                            if(picFolders.get(i).getPath().equals(folderpaths)){
                                picFolders.get(i).setFirstPic(datapath);
                                picFolders.get(i).addpics();
                            }
                        }
                    }
                }while (cursor.moveToNext());
                cursor.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
            for (int i = 0; i < picFolders.size();i++){
                Log.d("picture folders", picFolders.get(i).getFolderName()+"and path ="+picFolders.get(i).getNumberOfPics());
            }
            return picFolders;
        }
    }

    @Override
    public void onPicClicked(PicHolder holder, int position, ArrayList<pictureFacer> pics) {

    }

    @Override
    public void onPicClicked(String pictureFolderPath, String folderName) {
        Intent move = new Intent(MainActivity.this,ImageDisplay.class);
        move.putExtra("folderPath",pictureFolderPath);
        move.putExtra("folderName", folderName);

        startActivity(move);
    }
}