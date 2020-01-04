package com.example.photosapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class SearchPhotos extends AppCompatActivity {


    Button search;
    GridView result;
    EditText tagName;

    ArrayList<Album> albums;
    ArrayList<Photo> photos;
    ArrayList<Photo> results;
    ArrayList<Photo> tmp;

    String str;

    private static ImageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_photos);
        search=findViewById(R.id.search);
        result=findViewById(R.id.Result);
        tagName=findViewById(R.id.Tag);
        photos=new ArrayList<Photo>();

       albums = (ArrayList<Album>) getIntent().getSerializableExtra("albums");

        for(int i=0;i<albums.size();i++){
            for(int j=0;j<albums.get(i).getPhotos().size();j++){

                photos.add(albums.get(i).getPhotos().get(j));
            }

        }



        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search();
            }
        });


        result.setOnItemClickListener(
                (p, v, pos, id) -> openPhoto(pos)
        );


    }


    private void Search(){




        str = tagName.getText().toString();
        if(str.equalsIgnoreCase("Show All photos")){
            results=new ArrayList<Photo>(new HashSet<Photo>(photos));
            adapter = new ImageAdapter(this,results);
            result.setAdapter(adapter);
            return;
        }
        if(str.isEmpty()){
            return;
        }
        tmp=new ArrayList<Photo>();
        for(int i=0;i<photos.size();i++){
            for(int j=0;j<photos.get(i).getTags().size();j++) {
                String Tag = photos.get(i).getTags().get(j);
                Tag=Tag.toLowerCase();
                str=str.toLowerCase();
                if(Tag.contains(str)){
                    tmp.add(photos.get(i));

                }

            }

        }


        if(results.isEmpty()){
            new AlertDialog.Builder(SearchPhotos.this)

                    .setMessage("No matching result")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();


        }


       results = new ArrayList<Photo>(new HashSet<Photo>(tmp));


        adapter = new ImageAdapter(this,results);
        result.setAdapter(adapter);


    }


    private void openPhoto(int pos){


        Photo p = results.get(pos);
        Log.d("Open Photo", p.getImageUri());

        Intent intent = new Intent(this, OpenSearchResult.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("results",results);
        bundle.putSerializable("photo", p);
        bundle.putSerializable("pos",pos);
        intent.putExtras(bundle);
        startActivity(intent);


    }


    }
