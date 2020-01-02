package com.example.photosapp;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class CustomPagerAdapter extends PagerAdapter {

    private Context mContext;
    private Album album;
    private ArrayList<Photo> photos;
    private Photo photo;

    public CustomPagerAdapter(Context context, Album a, Photo p) {
        mContext = context;
        album=a;
        photos=a.getPhotos();
        photo=p;

    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.activity_open_photo, collection, false);
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return album.getNumPhotos();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {


        photo=photos.get(position);
        if(photo.getTagsString().isEmpty())
            return "No Tag";
        return photo.getTagsString().get(0);
    }

}







