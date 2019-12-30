package com.example.photosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchPhotos extends AppCompatActivity {


    Button search;
    EditText tagType,tagName;
    ListView result;
    ArrayList<Album> albums;
    ArrayList<Photo> photos;
    ArrayList<Photo> results;
    PhotoListAdapter adapter;
    String condition,str;
    ArrayList<String> tagString;
    String [] strr;
    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_photos);
        search=findViewById(R.id.temp);
        tagType=findViewById(R.id.TagType);

        result=findViewById(R.id.Result);
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

      /*  adapter = new PhotoListAdapter(photos, this);
        result.setAdapter(adapter);*/

    }


    private void Search() {
        str = tagType.getText().toString();

        results = new ArrayList<Photo>();
        condition="single";
       /* if(str.isEmpty()) {

            return;
        }*/

        if(str.contains("AND")) {
            System.out.println("contains AND");
            condition="Conj";
            strr = str.split(" AND ");
            System.out.println(strr[0]);
            System.out.println(strr[1]);
            if(strr[1].isEmpty()||strr[0].isEmpty()) {

                return;
            }

        }

        if(str.contains("OR")) {
            condition="disj";
            strr = str.split(" OR ", 2);
            if(strr[1].isEmpty()||strr[0].isEmpty()) {

                return;
            }
        }



        for (int i = 0; i < photos.size(); i++) {

            Photo p = photos.get(i);

            tagString = p.getTagsString();

            //  Log.d("tag", tagString.get(i));
            if (condition.equals("single") && tagString.contains(str)) {


                results.add(p);
            } else if (condition.equals("disj")) {
                if (tagString.contains(strr[0]) || tagString.contains(strr[1])) {

                    results.add(p);

                }
            } else if (condition.equals("Conj")) {
                System.out.println("in conjuction");
                if (tagString.contains(strr[0]) && tagString.contains(strr[1])) {

                    results.add(p);

                }


            }
        }



       // flag=false;
       // results=new ArrayList<Photo>();

    /*   for(int i=0;i<photos.size();i++) {

            Photo p = photos.get(i);

            tagString = p.getTagsString();
            Log.d("tag",tagString.get(i));
            if (condition.equals("single") && tagString.contains(str) ){


                results.add(p);


            } else if (condition.equals("disj")) {
                if (tagString.contains(strr[0]) || tagString.contains(strr[1])) {

                    results.add(p);

                }
            } else if (condition.equals("Conj")) {
                System.out.println("in conjuction");
                if (tagString.contains(strr[0]) && tagString.contains(strr[1])) {

                    results.add(p);

                }


            }

        }

        }*/

            adapter = new PhotoListAdapter(results, this);
            result.setAdapter(adapter);

        }
    }
