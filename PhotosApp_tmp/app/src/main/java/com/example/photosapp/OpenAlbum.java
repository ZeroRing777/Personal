package com.example.photosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class OpenAlbum extends AppCompatActivity {


    Album album;
    GridView photoGrid;
    Button addPhoto;
    private static final int PICK_IMAGE = 100;
    ArrayList<String> uriArray;
    ArrayList<ImageView> imageArray;
    ArrayList<Uri> imageUriArray;
    String uri;
    Uri imageUri;
    ImageView imageview;
    ArrayList<Album> albums;
    ArrayList<Photo> photos;
    Photo onePhoto;
    Button confirm, delete, rename;
    Bitmap image;
    ImageView photo;
    ListView photoList;
    ImageLoader imageLoader;
    String oldname;
    private static PhotoListAdapter adapter;
    Photo p;

    boolean flag;
    String State;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_album);

        //photoGrid = findViewById(R.id.PhotoGrid);

        //  photo=findViewById(R.id.photo);
        photoList = findViewById(R.id.photoList);
       // photos = new ArrayList<Photo>();

        addPhoto = findViewById((R.id.AddPhoto));
        confirm = findViewById(R.id.RenameAlbum);
        delete = findViewById(R.id.DeleteAlbum);
        rename = findViewById(R.id.Confirm);//something strange happens in openAlbum xml...



        album = (Album) getIntent().getSerializableExtra("album");
        photos=album.getPhotos();
        oldname=(String) getIntent().getSerializableExtra("name");

        uriArray = (ArrayList<String>) getIntent().getSerializableExtra("uriArray");
        if (uriArray == null) {
            uriArray = new ArrayList<String>();
        }
      /*  if (uriArray != null) {
            for (int i = 0; i < uriArray.size(); i++) {
                uri = uriArray.get(i);
                Log.d("uri when open", uriArray.get(i));
                Photo photo = new Photo(uri);
                photos.add(photo);


            }
            adapter = new PhotoListAdapter(photos, this);
            photoList.setAdapter(adapter);
            //   imageUri = Uri.parse(uri);
            //  photo.setImageURI(imageUri);//get the last photo for now


        }*/
        adapter = new PhotoListAdapter(photos, this);
        photoList.setAdapter(adapter);


        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteAlbum();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RenameAlbum();
            }
        });


        rename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReturnMain(album, uriArray);
            }
        });

   /*     PhotoAdapter customAdapter = new PhotoAdapter(getApplicationContext(), album.getPhotos());
        photoGrid.setAdapter(customAdapter);*/

        photoList.setOnItemClickListener(
                (p, v, pos, id) -> openPhoto(pos)
        );


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

        album = (Album) getIntent().getSerializableExtra("album");

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("album2", album);
        bundle.putSerializable("name", oldname);

        bundle.putString("state", "delete");
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();


    }

    private void ReturnMain(Album album, ArrayList<String> uriArray) {


        //uriArray.add(uri);

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

    }


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

            uriArray.add(uri);
            // ReturnMain(album,Array);
            Photo p = new Photo(uri);
            photos.add(p);
            album.setPhotos(photos);
            adapter = new PhotoListAdapter(photos, this);
            photoList.setAdapter(adapter);


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

             /*   for (int i = 0; i < uriArray.size(); i++) {
                    if (photos.get(i).getImageUri().equals(uriArray.get(i))) {
                        uriArray.remove(i);
                    }
                }*/


                adapter = new PhotoListAdapter(photos, this);
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



            String name=(String) data.getSerializableExtra("NewName");
            album.setName(name);
            State=(String) data.getSerializableExtra("State");



        }

    }
}
