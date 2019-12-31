package com.example.photosapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class RenameAlbum extends AppCompatActivity {

    Button Confirm;
    EditText newName;
    private ArrayList<Album> albums;
    private SaveAndLoad sl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rename_album);
        albums=new ArrayList<Album>();
        sl=new SaveAndLoad();
        albums=sl.load(this);

        Confirm=(Button)findViewById(R.id.Confirm);
        newName=(EditText)findViewById(R.id.NewName);
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name=newName.getText().toString();
                if(Name.isEmpty()){

                    new AlertDialog.Builder(RenameAlbum.this)
                            .setTitle("Cannot Rename Album")
                            .setMessage("Album Name cannot be empty")
                            .setPositiveButton(android.R.string.ok, null)
                            .show();

                    return;
                }

                for(int i=0;i<albums.size();i++){

                    if(albums.get(i).getName().equalsIgnoreCase(Name)){

                        new AlertDialog.Builder(RenameAlbum.this)
                                .setTitle("Cannot Rename Album")
                                .setMessage("Album Name already exists")
                                .setPositiveButton(android.R.string.ok, null)
                                .show();
                        return;

                    }

                }



                Intent intent = new Intent(getApplicationContext(),OpenAlbum.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("NewName", Name);
                bundle.putSerializable("State", "Rename");
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();


            }
        });

    }


}
