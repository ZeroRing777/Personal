package com.example.photosapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MotionEventCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class OpenPhoto extends AppCompatActivity {


    ImageView photoImage;

    Photo photo;
    Album album;
    String uri;
    Uri imageUri;
    Toolbar toolbar;
    String tagString;
    ArrayList<Album> albums;
    ArrayList<Photo> photos;
    SaveAndLoad sl;
    int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_photo);
        photoImage=findViewById(R.id.PhotoImage);

        photo=(Photo)getIntent().getSerializableExtra("photo");

        album=(Album)getIntent().getSerializableExtra("album");
        Log.d("OpenPhoto",album.getName());
        pos= (int) getIntent().getSerializableExtra("pos");
        Log.d("photo pos", String.valueOf(pos));
        Log.d("photo number", String.valueOf(album.getNumPhotos()));

        photos=album.getPhotos();

        sl=new SaveAndLoad();
        albums=sl.load(this);

       uri=photo.getImageUri();
        imageUri=Uri.parse(uri);
        photoImage.setImageURI(imageUri);
        toolbar=findViewById(R.id.toolbar);
        if(photo.getTagsString().isEmpty()){
            tagString="No Tag";
        }
        else{
            tagString=photo.getTagsString().get(0);
        }

        toolbar.setTitle(tagString);
        setSupportActionBar(toolbar);


        photoImage.setOnTouchListener(new OnSwipeTouchListener(this) {

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




     //   ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
      //  viewPager.setAdapter(new CustomPagerAdapter(this,album,photo));





    private void nextPhoto(){

        if(album.getNumPhotos()==pos+1){
            new AlertDialog.Builder(OpenPhoto.this)
                    .setTitle("Cannot Go to Next")
                    .setMessage("This is last photo in the album")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
            return;
        }
        pos=pos+1;
        photo=album.getPhotos().get(pos);
        uri=photo.getImageUri();
        imageUri=Uri.parse(uri);
        photoImage.setImageURI(imageUri);
        toolbar=findViewById(R.id.toolbar);
        if(photo.getTagsString().isEmpty()){
            tagString="No Tag";
        }
        else{
            tagString=photo.getTagsString().get(0);
        }

        toolbar.setTitle(tagString);
        setSupportActionBar(toolbar);




    }

    private void prevPhoto(){
        if(pos==0){
            new AlertDialog.Builder(OpenPhoto.this)
                    .setTitle("Cannot Go to Previous")
                    .setMessage("This is first photo in the album")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
            return;
        }
        pos=pos-1;
        photo=album.getPhotos().get(pos);
        uri=photo.getImageUri();
        imageUri=Uri.parse(uri);
        photoImage.setImageURI(imageUri);
        toolbar=findViewById(R.id.toolbar);
        if(photo.getTagsString().isEmpty()){
            tagString="No Tag";
        }
        else{
            tagString=photo.getTagsString().get(0);
        }

        toolbar.setTitle(tagString);
        setSupportActionBar(toolbar);



    }








    @Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        albums=sl.load(this);
        if(photo.getTagsString().isEmpty()){
            tagString="No Tag";
        }
        else{
            tagString=photo.getTagsString().get(0);
        }

        toolbar.setTitle(tagString);
        setSupportActionBar(toolbar);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.openphoto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


       if (id == R.id.EditTag) {

            EditTag();

            return true;
        }

       if(id==R.id.DeletePhoto){


           Delete();
           return true;
       }



        return super.onOptionsItemSelected(item);
    }






    private void Confirm(Photo photo){


        Intent intent = new Intent(getApplicationContext(),OpenAlbum.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("photo2", photo);

        bundle.putString("PhotoState","confirm");
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();


    }

    private void Delete(){


        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Do your Yes progress
                        Intent intent = new Intent(getApplicationContext(),OpenAlbum.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("photo2", photo);
                        bundle.putString("PhotoState","delete");
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


    private void EditTag(){



        Intent intent = new Intent(getApplicationContext(),EditTag.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("photo2", photo);
        intent.putExtras(bundle);
        startActivityForResult(intent, 5);



    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 5) {//AddTag Result

            photo = (Photo) data.getSerializableExtra("photoTag");


          for (int i = 0; i < photos.size(); i++) {
                if (photos.get(i).getImageUri().equals(photo.getImageUri())) {
                    photos.set(i,photo);

                }
            }
          album.setPhotos(photos);
            for (int i = 0; i < albums.size(); i++) {
                if (albums.get(i).getName().equalsIgnoreCase(album.getName())) {
                    albums.set(i,album);
                }
            }
            sl.Save(albums,this);



        }
        }




}
