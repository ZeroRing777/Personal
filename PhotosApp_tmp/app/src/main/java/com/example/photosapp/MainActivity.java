package com.example.photosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    private ListView AlbumList;
    private SaveAndLoad sl;
    ArrayList<String> arr;
    ArrayList<Album> albums;
    ArrayList<Photo> photos;
    ArrayList<String> uriArray;
    Album album;
    String uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlbumList= findViewById(R.id.List);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Album List");
        setSupportActionBar(toolbar);
        sl=new SaveAndLoad();

        albums=sl.load(this);

        arr=new ArrayList<String>();
        for (int i=0;i<albums.size();i++){
            String name=albums.get(i).getName();
            arr.add(name);
        }


        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arr);
        AlbumList.setAdapter(adapter);


        AlbumList.setOnItemClickListener(
                (p,v,pos,id) -> openAlbum(pos)
        );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.albumlist, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.AddAlbum) {

            Intent intent = new Intent(getApplicationContext(),CreateAlbum.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("albums",albums);
            intent.putExtras(bundle);
            startActivityForResult(intent,1);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    private void openAlbum(int pos){

        //String str=AlbumList.getSelectedItem().toString();
        String str=arr.get(pos);
        for(int i=0;i<albums.size();i++){

            if(albums.get(i).getName().equals(str)){
            album=albums.get(i);}
        }

        String name=album.getName();
        Intent intent = new Intent(this, OpenAlbum.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("album",album);
        bundle.putSerializable("name",name);



        uriArray=new ArrayList<String>();


        if(album.getPhotos()!=null)

        {//uri=album.getPhotos().get(0).getImageUri();//get the first photo for temp
            for(int i=0;i<album.getPhotos().size();i++) {
                uri=album.getPhotos().get(i).getImageUri();
                uriArray.add(uri);


            }

        }

        bundle.putSerializable("uriArray",uriArray);
      /*  if(album.getPhotos()!=null) {
            bundle.putSerializable("uri", album.getPhotos().get(0).imageUri);
            Log.d("uri when open", album.getPhotos().get(0).imageUri);
        }
        else{
            bundle.putSerializable("uri","none");
        }*/
        intent.putExtras(bundle);

        startActivityForResult(intent,2);


    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {//Add Album Reusltok
            if (resultCode == RESULT_OK) {
                Album album = (Album) data.getSerializableExtra("new_album");
                albums.add(album);

                String name = album.getName();
                arr.add(name);
                sl.Save(albums,this);

                ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arr);
                AlbumList.setAdapter(adapter);

            }
        }
        if (requestCode == 2&&resultCode == RESULT_OK) {//OpenAlbum Result

            String s = (String) data.getSerializableExtra("state");

            Album album2 = (Album) data.getSerializableExtra("album2");

            String NewName=album2.getName();
            String oldName=(String) data.getSerializableExtra("name");

            Log.d("New Name when return",NewName);

            Log.d("Od Name when return",oldName);

            ArrayList<String>  uriArray2=(ArrayList<String>) data.getSerializableExtra("uriArray2");
            photos = new ArrayList<Photo>();
            if(uriArray==null){
                uriArray=new ArrayList<String>();
            }



            if (s.equals("confirm")&&resultCode == RESULT_OK) {//going back after opening photo


            /*    for(int i=0; i<uriArray2.size();i++) {
                    uri=uriArray2.get(i);
                    Log.d("uri when going back",uri);
                    Photo photo = new Photo(uri);
                    photos.add(photo);}

                album2.setPhotos(photos);*/


                for (int j = 0; j < albums.size(); j++) {

                    if (albums.get(j).getName().equals(oldName)){
                        albums.set(j, album2);
                        arr.remove(oldName);
                        arr.add(NewName);
                    }
                }




            }
            //Log.d("Album Confrirm", String.valueOf(album2.getNumPhotos()));




            else if (s.equals("delete")) {//should be delete
                for (int i = 0; i < albums.size(); i++) {
                    if (albums.get(i).getName().equals(album2.getName())) {
                        albums.remove(i);
                    }
                }

                String name = album2.getName();
                arr.remove(name);
            }

           // SaveData();
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arr);
            AlbumList.setAdapter(adapter);


        }
    }


   /* private void SaveData () {
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(albums);
        editor.putString("task list", json);
        editor.apply();

    }


    private void loadData() {

        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Album>>() {
        }.getType();
        albums = gson.fromJson(json, type);
        if (albums == null) {

            albums = new ArrayList<Album>();
        }

    }*/







}
