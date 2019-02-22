package com.mccorporation.mcjores.vkphotoapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MCjores on 19.02.2019.
 */

public class PhotosInAlmums extends AppCompatActivity {
    private ImageAdapter imageAdapter;
    private static ArrayList<String> imageURLinAlbum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photos_layout);

        imageURLinAlbum = new ArrayList<>();
        final GridView gridViewForPhotoInAlbum = (GridView) findViewById(R.id.grid_view_photos);

        Intent intent = getIntent();
        int id_Albums = intent.getExtras().getInt("id_album");

        getPhotoInAlbumRequest(id_Albums);

        gridViewForPhotoInAlbum.setAdapter(imageAdapter);

        gridViewForPhotoInAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //полный жкран
                Intent intent = new Intent(getApplicationContext(), FullImageActivity.class);
                intent.putExtra("id", position);
                startActivity(intent);
            }
        });

    }


    //// TODO: 19.02.2019 album_id нужно передавать отрицательный
    private void getPhotoInAlbumRequest(int album_id) {
        final VKRequest request = new VKRequest("photos.get?album_id=" + album_id, VKParameters.from(), VKApiPhoto.class);

        request.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                String json = response.json.toString();
                try {
                    requestToJsonPhotoInAlbum(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void requestToJsonPhotoInAlbum(String json) throws JSONException {
        if (json != null) {
            JSONObject o = new JSONObject(json);
            JSONObject o1 = o.getJSONObject("response");
            JSONArray items = o1.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject ob = items.getJSONObject(i);
                imageURLinAlbum.add(ob.getString("photo_604"));   //ссылка на изображение
            }

        }
        Log.i("Photo", "items = " + imageURLinAlbum.toString());
        imageAdapter = new ImageAdapter(getApplicationContext(), imageURLinAlbum);
    }

    public static ArrayList<String> getImageInAlbums() {
        return imageURLinAlbum;
    }
}
