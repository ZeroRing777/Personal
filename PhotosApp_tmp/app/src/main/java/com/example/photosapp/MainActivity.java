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
        if(albums.isEmpty()){
            Album a=new Album("Default album");
            albums.add(a);
        }

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
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
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

        {
            for(int i=0;i<album.getPhotos().size();i++) {
                uri=album.getPhotos().get(i).getImageUri();
                uriArray.add(uri);


            }

        }

        bundle.putSerializable("uriArray",uriArray);

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
        //Contains unnecessary code here, too lazy to modify it
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





                for (int j = 0; j < albums.size(); j++) {

                    if (albums.get(j).getName().equals(oldName)){
                        albums.set(j, album2);
                        arr.remove(oldName);
                        arr.add(NewName);
                    }
                }




            }





            else if (s.equals("delete")) {//should be delete
                for (int i = 0; i < albums.size(); i++) {
                    if (albums.get(i).getName().equals(album2.getName())) {
                        albums.remove(i);
                    }
                }

                String name = album2.getName();
                arr.remove(name);
            }

           sl.Save(albums,this);
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arr);
            AlbumList.setAdapter(adapter);


        }
    }


}
