package com.example.photosapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class OpenAlbumList extends AppCompatActivity {
    private ListView AlbumList;
    private ArrayList<Album> albums;
    private Toolbar toolbar;
    private ArrayList<String> arr;
    private Album album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_album_list);
        AlbumList=findViewById(R.id.AlbumList);
        toolbar=findViewById(R.id.toolbar);
        albums=(ArrayList<Album>)getIntent().getSerializableExtra("albums");
        toolbar.setTitle("Please select the desired album");
        setSupportActionBar(toolbar);

        arr=new ArrayList<String>();
        for (int i=0;i<albums.size();i++){
            String name=albums.get(i).getName();
            arr.add(name);
        }


        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arr);
        AlbumList.setAdapter(adapter);


        AlbumList.setOnItemClickListener(
                (p,v,pos,id) -> Result(pos)
        );

    }

    private void Result(int pos){
        album=new Album("");
        String str=arr.get(pos);
        for(int i=0;i<albums.size();i++){

            if(albums.get(i).getName().equals(str)){
                album=albums.get(i);}
        }

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Do your Yes progress


                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("album", album);

                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //Do your No progress
                        break;
                }
            }
        };
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setMessage("Are you sure to move photo into this album?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();



    }
}
