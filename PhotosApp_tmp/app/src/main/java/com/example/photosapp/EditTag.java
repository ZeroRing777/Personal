package com.example.photosapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class EditTag extends AppCompatActivity {
    Button delete,add,confirm;
    EditText name;
    ListView TagList;
    Photo photo;
    SaveAndLoad sl;
    ArrayList<Album> albums;
    ArrayList<String> arr;
    ArrayList<String > tags;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tag);
        sl=new SaveAndLoad();
        albums=sl.load(this);


     photo = (Photo) getIntent().getSerializableExtra("photo2");
        arr=new ArrayList<String>();
        arr=photo.getTags();
        tags=photo.getTags();
        TagList=findViewById(R.id.TagList);
        name=findViewById(R.id.Name);

        delete=findViewById(R.id.DeleteTag);
        add=findViewById(R.id.AddTag);
        confirm=findViewById(R.id.Confirm);
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arr);
        TagList.setAdapter(adapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTag();
            }

        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirm();
            }

        });

       /* delete.setOnClickListener(new View.OnClickListener() {
           return;

        });*/


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EditTag.this)
                        .setMessage("Please select the Tag you want to delete")
                        .setPositiveButton(android.R.string.ok, null)
                        .show();


            }

        });




       TagList.setOnItemClickListener(
                (p, v, pos, id) -> DeleteTag(pos)
        );


    }

    private void DeleteTag(int pos){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Do your Yes progress

                        str=arr.get(pos);
                        photo.DeleteTag(str);
                        arr=photo.getTags();
                       // arr.remove(pos);

                        ArrayAdapter adapter=new ArrayAdapter(EditTag.this,android.R.layout.simple_list_item_1,arr);
                        TagList.setAdapter(adapter);

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




    private void Confirm(){

        Intent intent = new Intent(getApplicationContext(),OpenPhoto.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("photoTag", photo);
        for(int i=0;i<photo.getTags().size();i++)
        { Log.d("tags when going back", photo.getTags().get(i));}//for test
        Log.d("tags when going back","none");

        //bundle.putString("TagState","confirm");
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();




    }

    private void AddTag(){

        String Tag=name.getText().toString();

        if(Tag.isEmpty()){

            new AlertDialog.Builder(EditTag.this)
                    .setTitle("Cannot Add Tag")
                    .setMessage("Tag Name cannot be empty")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();

            return;
        }

        for(int i=0;i<tags.size();i++){

            if(tags.get(i).equalsIgnoreCase(Tag)){

                new AlertDialog.Builder(EditTag.this)
                        .setTitle("Cannot Add Tag")
                        .setMessage("Tag already exists")
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                return;

            }

        }


         photo.AddTag(Tag);
        arr=photo.getTags();
       // arr.add(Tag);
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arr);
        TagList.setAdapter(adapter);


    }
}


