package com.example.photosapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;

import android.widget.GridView;
import android.widget.ImageView;


import java.util.ArrayList;

public class OpenAlbum extends AppCompatActivity {


    Album album;
    Toolbar toolbar;

    Button addPhoto;
    private static final int PICK_IMAGE = 100;
    ArrayList<String> uriArray;
    ArrayList<Album> albums;

    String uri;
    Uri imageUri;
    private SaveAndLoad sl;

    ArrayList<Photo> photos;

    Button confirm, delete, rename;

    ImageView photo;
    GridView photoList;

    String oldname;
    private static ImageAdapter adapter;
    Photo p;

    String State;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_album);
        albums=new ArrayList<Album>();
        sl=new SaveAndLoad();
        albums=sl.load(this);


        photoList = findViewById(R.id.photoList);


        album = (Album) getIntent().getSerializableExtra("album");
        photos=album.getPhotos();
        oldname=(String) getIntent().getSerializableExtra("name");

        uriArray = (ArrayList<String>) getIntent().getSerializableExtra("uriArray");
         toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle(album.getName());
        setSupportActionBar(toolbar);

        if (uriArray == null) {
            uriArray = new ArrayList<String>();
        }

        adapter = new ImageAdapter(this,photos);
        photoList.setAdapter(adapter);



        photoList.setOnItemClickListener(
                (p, v, pos, id) -> openPhoto(pos)
        );


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photogrid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.AddPhoto) {

            openGallery();

            return true;
        }

        if(id==R.id.DeleteAlbum){
            DeleteAlbum();
            return true;
        }

        if(id==R.id.RenameAlbum){

            RenameAlbum();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }











    private void openPhoto(int pos) {

        // Photo p=(Photo) photoList.getItemAtPosition(pos);
        // Photo p = (Photo) photoList.getAdapter().getItem(pos);
        // Log.d("Open Photo",p.getImageUri());
        Photo p = photos.get(pos);
        Log.d("Open Photo", p.getImageUri());

        Intent intent = new Intent(this, OpenPhoto.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("photo", p);
        intent.putExtras(bundle);
        startActivityForResult(intent, 3);


    }


    private void RenameAlbum(){

        album = (Album) getIntent().getSerializableExtra("album");

        Intent intent = new Intent(getApplicationContext(), RenameAlbum.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("album2", album);
        bundle.putString("state", "rename");
        intent.putExtras(bundle);
        startActivityForResult(intent, 4);



    }


    private void DeleteAlbum() {


        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Do your Yes progress
                        album = (Album) getIntent().getSerializableExtra("album");

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("album2", album);
                        bundle.putSerializable("name", oldname);

                        bundle.putString("state", "delete");
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
        ab.setMessage("Are you sure to delete?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();



    }

  /*  private void ReturnMain(Album album, ArrayList<String> uriArray) {


        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        Bundle bundle = new Bundle();


        bundle.putSerializable("album2", album);
        bundle.putSerializable("name", oldname);
        bundle.putSerializable("MainPhoto",p);

        bundle.putSerializable("uriArray2", uriArray);


        bundle.putString("state", "confirm");

        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();

    }*/


    private void openGallery() {

        Intent gallery = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        Bundle bundle = new Bundle();
        bundle.putSerializable("uriArray3", uriArray);
        gallery.putExtras(bundle);
        startActivityForResult(gallery, PICK_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {


            // album = (Album)data.getSerializableExtra("album");
            album = (Album) getIntent().getSerializableExtra("album");

            uriArray = (ArrayList<String>) getIntent().getSerializableExtra("uriArray");


            imageUri = data.getData();
            // photo.setImageURI(imageUri);
            uri = imageUri.toString();
            for(int i=0;i<uriArray.size();i++){
                if(uriArray.contains(uri)){

                    new AlertDialog.Builder(OpenAlbum.this)
                            .setTitle("Cannot Add Photo")
                            .setMessage("Photo already exists in this album")
                            .setPositiveButton(android.R.string.ok, null)
                            .show();


                    return;
                }


            }

            uriArray.add(uri);
            // ReturnMain(album,Array);
            Photo p = new Photo(uri);
            photos.add(p);
            album.setPhotos(photos);
            adapter = new ImageAdapter(this,photos);
            photoList.setAdapter(adapter);
            for(int i=0;i<albums.size();i++){
                if(albums.get(i).getName().equalsIgnoreCase(album.getName())){
                    albums.set(i,album);
                }

            }



            sl.Save(albums,this);


        }

        if (resultCode == RESULT_OK && requestCode == 3) {//openPhoto
           String state2=(String) data.getSerializableExtra("PhotoState");

             p = (Photo) data.getSerializableExtra("photo2");
            if(state2.equals("delete")){

                for (int i = 0; i < photos.size(); i++) {
                    if (photos.get(i).getImageUri().equals(p.getImageUri())) {
                        photos.remove(i);
                        uriArray.remove(i);
                    }
                }


                adapter = new ImageAdapter(this,photos);
                photoList.setAdapter(adapter);

            }
            if(state2.equals("confirm")){
                Log.d("Confirm Photo","In Confirm");
                for(int i=0;i<p.getTagsString().size();i++){//for test use

                    Log.d("Tags back opening album",p.getTagsString().get(i));
                }
                for (int i = 0; i < photos.size(); i++) {
                    if (photos.get(i).getImageUri().equals(p.getImageUri())) {
                        photos.set(i,p);
                        uriArray.set(i,p.getImageUri());
                    }
                }



            }

        }

        if(resultCode == RESULT_OK && requestCode == 4) {//rename Album



            String Oldname=album.getName();
            String Newname=(String) data.getSerializableExtra("NewName");
            for(int i=0;i<albums.size();i++){
                if(albums.get(i).getName().equalsIgnoreCase(Oldname)){
                    albums.get(i).setName(Newname);
                }

            }
            sl.Save(albums,this);

            toolbar.setTitle(Newname);
            setSupportActionBar(toolbar);
           // State=(String) data.getSerializableExtra("State");




        }

    }
}
