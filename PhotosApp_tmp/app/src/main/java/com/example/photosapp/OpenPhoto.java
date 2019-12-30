package com.example.photosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class OpenPhoto extends AppCompatActivity {


    ImageView photoImage;
    Button addTag,delete,confirm;
    Photo photo;
    String uri;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_photo);
        photoImage=findViewById(R.id.PhotoImage);
        confirm=findViewById(R.id.Confirm);
        delete=findViewById(R.id.Delete);
        addTag=findViewById(R.id.AddTag);
        photo=(Photo)getIntent().getSerializableExtra("photo");
       uri=photo.getImageUri();
        imageUri=Uri.parse(uri);
        photoImage.setImageURI(imageUri);
        confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Confirm(photo);
            }
        });
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Delete();
            }
        });

        addTag.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AddTag();
            }
        });

       // Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //setSupportActionBar(myToolbar);

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



        Intent intent = new Intent(getApplicationContext(),OpenAlbum.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("photo2", photo);
        bundle.putString("PhotoState","delete");
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();


    }


    private void AddTag(){



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
            for(int i=0;i<photo.getTagsString().size();i++){//for test use

                Log.d("Tags back opening photo",photo.getTagsString().get(i));
            }



        }
        }

//I comment you methid for test
   /* public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_tag:

                //add tag

            case R.id.delete_photo:

                Delete();

            default:
                return super.onOptionsItemSelected(item);

        }
    }*/


}
