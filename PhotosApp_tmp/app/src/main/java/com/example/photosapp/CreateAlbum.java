package com.example.photosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateAlbum extends AppCompatActivity {
  private String Name;
    public static final String ALBUM = "new_album";

    EditText AlbumName;
    private Button loadImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_album);
        //EditText AlbumName=findViewById(R.id.AlbumName);
        Button Confirm=findViewById(R.id.ConfirmButton);
        AlbumName =(EditText) findViewById(R.id.AlbumName);
        loadImage=findViewById(R.id.LoadImage);




        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name=AlbumName.getText().toString();
                Album album=new Album(Name);
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





            }
        });

    }
}
