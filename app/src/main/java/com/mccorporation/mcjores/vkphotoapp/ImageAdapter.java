package com.mccorporation.mcjores.vkphotoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MCjores on 19.02.2019.
 */

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String>  image;

    public ImageAdapter(Context context, ArrayList<String> imageURL){
        this.context = context;
        this.image = imageURL;
    }

    @Override
    public int getCount() {
        return image.size();
    }

    @Override
    public Object getItem(int position) {
        return image.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;

        if (convertView ==null){
            grid = new View(context);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.grid_photo,parent,false);
        } else grid = convertView;

        ImageView photos = (ImageView) grid.findViewById(R.id.image_grid);
            //заполнение фотографий
        Picasso.with(context).load(image.get(position))
                .into(photos);

        return grid;
    }
}
