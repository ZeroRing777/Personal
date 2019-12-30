package com.example.photosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class EditTag extends AppCompatActivity {
    Button delete,add,confirm;
    EditText name,type;
    ListView TagList;
    Photo photo;
     ArrayList<String> arr;
     String str,value,key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tag);

     photo = (Photo) getIntent().getSerializableExtra("photo2");
        arr=new ArrayList<String>();
        arr=photo.getTagsString();
        TagList=findViewById(R.id.TagList);
        name=findViewById(R.id.Name);
        type=findViewById(R.id.Type);
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


        TagList.setOnItemClickListener(
                (p, v, pos, id) -> DeleteTag(pos)
        );


    }

    private void DeleteTag(int pos){

        String str=arr.get(pos);

        try {

            // add 100 in each value using forEach()
            photo.tagTable.forEach((k, v) -> {
                for(int i=0;i<v.size();i++)
                {	String str1=k+"="+v.get(i);
                    if(str1.equals(str)) {
                        key=k;
                        value=v.get(i);
                        break;

                    }

                }

            });
        }
        catch (Exception e) {

            System.out.println("Exception: " + e);
        }

        photo.removeTag(key,value);
        arr=photo.getTagsString();
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arr);
        TagList.setAdapter(adapter);


    }




    private void Confirm(){

        Intent intent = new Intent(getApplicationContext(),OpenPhoto.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("photoTag", photo);
        for(int i=0;i<photo.getTagsString().size();i++)
        { Log.d("tags when going back", photo.getTagsString().get(i));}//for test
        Log.d("tags when going back","none");

        //bundle.putString("TagState","confirm");
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();




    }

    private void AddTag(){

        String t=type.getText().toString();
        if(!(t.equalsIgnoreCase("person")||t.equalsIgnoreCase("location"))){

            return;
        }
        if(!photo.getTags().contains(t)){
            photo.addTag(t);
        }
        String n=name.getText().toString();
        photo.addTag(t,n);
        arr=photo.getTagsString();


        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arr);
        TagList.setAdapter(adapter);


    }
}


