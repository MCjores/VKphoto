package com.mccorporation.mcjores.vkphotoapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKApiPhotoAlbum;
import com.vk.sdk.api.model.VKPhotoArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
//        import java.util.concurrent.CompletableFuture;

/**
 * Created by MCjores on 11.02.2019.
 */

public class GridAdapter extends BaseAdapter {


    private ImageView imagePhotoVK;
    private TextView textPhotoVK;
    private TextView textCountPhoto;

    private VKPhotoArray VKphoto;
    private ArrayList<String> text;
    private ArrayList<String> text1;
    private ArrayList<Integer> album_ids;
    private ArrayList<Integer> countPhoto;

    private Context context;



    public GridAdapter(Context context, ArrayList<String> images, ArrayList<String> title, ArrayList<Integer> album_ids, ArrayList<Integer> countPhoto) {
        this.context = context;
//        this.draw = new Integer[]{R.drawable.ic_ab_app, R.drawable.ic_ab_done, R.drawable.vk_clear_shape,};
        this.text = images;
        this.text1 = title;
        this.album_ids = album_ids;
        this.countPhoto = countPhoto;

    }


    @Override
    public int getCount() {
        return text.size();
    }

    @Override
    public Object getItem(int i) {
        return text.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View grid;

        if (view == null) {

            grid = new View(context);

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = layoutInflater.inflate(R.layout.cellgrid, viewGroup, false);

        } else grid = view;
//        grid.setBackgroundResource(android.R.drawable.progress_indeterminate_horizontal);
        //станавливаем высоту
        grid.getLayoutParams().height = 300;


        imagePhotoVK = (ImageView) grid.findViewById(R.id.image_VKphoto);
        textPhotoVK = (TextView) grid.findViewById(R.id.tv_VKphotoname);
        textCountPhoto = (TextView) grid.findViewById(R.id.tv_VKcount_photo);


        Picasso.with(context).load(text1.get(position))
                .into(imagePhotoVK);

        textPhotoVK.setText(text.get(position));
        textCountPhoto.setText(countPhoto.get(position).toString());



        return grid;
    }

    public Integer getAlbumId(int position){
        return album_ids.get(position) ;
    }


}
