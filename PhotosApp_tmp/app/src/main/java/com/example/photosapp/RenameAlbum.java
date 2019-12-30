package com.example.photosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RenameAlbum extends AppCompatActivity {

    Button Confirm;
    EditText newName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rename_album);
        Confirm=(Button)findViewById(R.id.Confirm);
        newName=(EditText)findViewById(R.id.NewName);
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name=newName.getText().toString();

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
