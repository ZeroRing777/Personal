package com.example.photosapp;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SaveAndLoad {


    private Context context;
    private ArrayList<Album> albums;
    private  ArrayList<Photo> photos;
    public void Save( ArrayList<Album> albums, Context context){
        this.context=context;
        SharedPreferences sharedPreferences =  context.getSharedPreferences("shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(albums);
        editor.putString("task list", json);
        editor.apply();

    }
    public ArrayList<Album> load(Context context){


        SharedPreferences sharedPreferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Album>>() {
        }.getType();
        albums = gson.fromJson(json, type);
        if (albums == null) {

            albums = new ArrayList<Album>();
        }

        return albums;

    }
}
