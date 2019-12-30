package com.example.photosapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class CreateAlbum extends AppCompatActivity {
  private String Name;
    public static final String ALBUM = "new_album";
    private ArrayList<Album> albums;

    private EditText AlbumName;
    private Button loadImage;
    private static final int PICK_IMAGE = 100;
    private String uri;
    private Uri imageUri;
    private ImageView image;

    ArrayList<String> uriArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_album);
        Button Confirm=findViewById(R.id.ConfirmButton);
        AlbumName =(EditText) findViewById(R.id.AlbumName);
        loadImage=findViewById(R.id.LoadImage);
        image=findViewById(R.id.ImageView);
        albums = (ArrayList<Album>) getIntent().getSerializableExtra("albums");


        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name=AlbumName.getText().toString();
                if(Name.isEmpty()){

                    new AlertDialog.Builder(CreateAlbum.this)
                            .setTitle("Cannot Add Album")
                            .setMessage("Album Name cannot be empty")
                            .setPositiveButton(android.R.string.ok, null)
                            .show();

                    return;
                }

              for(int i=0;i<albums.size();i++){

                    if(albums.get(i).getName().equalsIgnoreCase(Name)){

                        new AlertDialog.Builder(CreateAlbum.this)
                                .setTitle("Cannot Add Album")
                                .setMessage("Album Name already exists")
                                .setPositiveButton(android.R.string.ok, null)
                                .show();
                        return;

                    }

                }



                Album album=new Album(Name);
               if(uri!=null){
                    Photo p = new Photo(uri);
                    ArrayList<Photo> photos=new ArrayList<Photo>();
                    photos.add(p);
                    album.setPhotos(photos);
                }

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ALBUM, album);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();


            }
        });

        loadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                Bundle bundle = new Bundle();

                gallery.putExtras(bundle);
                startActivityForResult(gallery, PICK_IMAGE);


            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {


            // album = (Album)data.getSerializableExtra("album");
           // album = (Album) getIntent().getSerializableExtra("album");
           // uriArray = (ArrayList<String>) getIntent().getSerializableExtra("uriArray");

            imageUri = data.getData();
            image.setImageURI(imageUri);
            uri = imageUri.toString();

         //   uriArray.add(uri);
            // ReturnMain(album,Array);

           // photos.add(p);
            //album.setPhotos(photos);
            //adapter = new PhotoListAdapter(photos, this);
            //photoList.setAdapter(adapter);


        }}



}
