package com.example.photosapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class PhotoListAdapter extends ArrayAdapter<Photo> {
    private ArrayList<Photo> photoList;
    private Context context;

    public PhotoListAdapter(ArrayList<Photo> photoList, Context ctx) {
        super(ctx, R.layout.row_layout, photoList);
        this.photoList = photoList;
        this.context = ctx;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_layout, parent, false);
        }
        // Now we can fill the layout with the right values
        ImageView photo = (ImageView) convertView.findViewById(R.id.photo);

        Photo p = photoList.get(position);
        String uri=p.getImageUri();
        Uri imageUri = Uri.parse(uri);
        photo.setImageURI(imageUri);



        return convertView;
    }
}