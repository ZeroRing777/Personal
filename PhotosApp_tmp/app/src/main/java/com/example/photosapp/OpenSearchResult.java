package com.example.photosapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;

public class OpenSearchResult extends AppCompatActivity {

    private ImageView image;
    private String uri;
    private  Uri imageUri;
    private Toolbar toolbar;
    private Photo photo;
    private int pos;
    private ArrayList<Photo> results;
    private String tagString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_search_result);
        image=findViewById(R.id.ResultImage);
        photo=(Photo)getIntent().getSerializableExtra("photo");
        pos= (int) getIntent().getSerializableExtra("pos");
        results=(ArrayList<Photo>)getIntent().getSerializableExtra("results");

        uri=photo.getImageUri();
        imageUri=Uri.parse(uri);
        image.setImageURI(imageUri);
        toolbar=findViewById(R.id.toolbar);
        if(photo.getTags().isEmpty()){
            tagString="No Tag";
        }
        else{
            tagString=photo.getTags().get(0);
        }

        toolbar.setTitle(tagString);
        setSupportActionBar(toolbar);
        image.setOnTouchListener(new OnSwipeTouchListener(this) {

            public void onSwipeTop() {

            }
            public void onSwipeRight() {
                prevPhoto();

            }
            public void onSwipeLeft() {
                nextPhoto();

            }
            public void onSwipeBottom() {

            }

        });

    }


    private void nextPhoto(){

        if(results.size()==pos+1){
            new AlertDialog.Builder(OpenSearchResult.this)
                    .setTitle("Cannot Go to Next")
                    .setMessage("This is last photo in search results")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
            return;
        }
        pos=pos+1;
        photo=results.get(pos);
        uri=photo.getImageUri();
        imageUri=Uri.parse(uri);
        image.setImageURI(imageUri);
        toolbar=findViewById(R.id.toolbar);
        if(photo.getTags().isEmpty()){
            tagString="No Tag";
        }
        else{
            tagString=photo.getTags().get(0);
        }

        toolbar.setTitle(tagString);
        setSupportActionBar(toolbar);




    }

    private void prevPhoto(){
        if(pos==0){
            new AlertDialog.Builder(OpenSearchResult.this)
                    .setTitle("Cannot Go to Previous")
                    .setMessage("This is first photo in search results")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
            return;
        }
        pos=pos-1;
        photo=results.get(pos);
        uri=photo.getImageUri();
        imageUri=Uri.parse(uri);
        image.setImageURI(imageUri);
        toolbar=findViewById(R.id.toolbar);
        if(photo.getTags().isEmpty()){
            tagString="No Tag";
        }
        else{
            tagString=photo.getTags().get(0);
        }

        toolbar.setTitle(tagString);
        setSupportActionBar(toolbar);



    }




}
