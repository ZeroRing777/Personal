package com.example.photosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private Button SearchPhotos;
    private Button OpenAlbumList;
    private SaveAndLoad sl;
    private ArrayList<Album> albums;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        SearchPhotos=findViewById(R.id.SearchForPhotos);
        OpenAlbumList=findViewById(R.id.OpenAlbumList);
        sl=new SaveAndLoad();
        albums=sl.load(this);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SearchPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), SearchPhotos.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("albums",albums);
                intent.putExtras(bundle);

                startActivity(intent);

            }
        });


        OpenAlbumList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("albums",albums);
                intent.putExtras(bundle);

                startActivity(intent);

            }
        });



    }


    @Override
    public void onRestart() {
        super.onRestart();
        albums=sl.load(this);
    }

}
