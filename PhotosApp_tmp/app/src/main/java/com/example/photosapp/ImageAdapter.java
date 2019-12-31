package com.example.photosapp;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Photo> photoList;


    // Constructor
    public ImageAdapter(Context c, ArrayList<Photo> photoList){
        mContext = c;
        if(photoList==null){
            photoList=new ArrayList<Photo>();
        }
        this.photoList=photoList;
    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public Object getItem(int position) {
        return photoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        Photo p = photoList.get(position);
        String uri=p.getImageUri();
        Uri imageUri = Uri.parse(uri);
        imageView.setImageURI(imageUri);

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
        if(imageView.getDrawable()==null){
            return null;
        }
        return imageView;
    }

}
