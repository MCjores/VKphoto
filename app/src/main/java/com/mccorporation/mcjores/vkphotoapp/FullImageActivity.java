package com.mccorporation.mcjores.vkphotoapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;



public class FullImageActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);

        Intent intent = getIntent();
        ImageView full_Image = (ImageView) findViewById(R.id.full_image_VK);

        int position  = intent.getExtras().getInt("id");
        String URL = PhotosInAlmums.getImageInAlbums().get(position);

        Picasso.with(this).load(URL)
                .into(full_Image);
    }
}
