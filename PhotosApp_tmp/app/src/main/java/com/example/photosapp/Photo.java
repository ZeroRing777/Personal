package com.example.photosapp;

import androidx.viewpager.widget.PagerAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Photo implements Serializable {
    private static final long serialVersionUID = 1L;


    private String imageUri;

    private ArrayList<String> tags;


    public Photo(String imageUri){

        this.imageUri=imageUri;

    }

    public ArrayList<String> getTags(){


        if(tags==null){
            return new ArrayList<String>();
        }
        return this.tags;

    }

    public void AddTag(String tag){
        if(tags==null){
            tags=new ArrayList<String>();
        }

      tags.add(tag);
    }

    public void DeleteTag(String tag){
        if(tags==null){
            tags=new ArrayList<String>();
        }

        for(int i=0;i<tags.size();i++){

            if(tags.get(i).equalsIgnoreCase(tag)){

                tags.remove(i);
                break;
            }
        }
    }

    public String getImageUri(){

        return this.imageUri;
    }



}