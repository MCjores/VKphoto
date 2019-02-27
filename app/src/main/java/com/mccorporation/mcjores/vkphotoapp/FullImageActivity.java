package com.mccorporation.mcjores.vkphotoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;



public class FullImageActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image_web);

        Intent intent = getIntent();
//        ImageView full_Image = (ImageView) findViewById(R.id.full_image_VK);

        WebView full_image_web = (WebView) findViewById(R.id.web_full_image);
        full_image_web.setBackgroundColor(Color.BLACK);
        //поддержка масштабирования
        full_image_web.getSettings().setSupportZoom(true);
        full_image_web.getSettings().setBuiltInZoomControls(true);
        //больше места для нашей картинки
        full_image_web.setPadding(0,0,0,0);
        //полосы прокрутки – внутри изображения, увеличение места для просмотра
        full_image_web.setScrollbarFadingEnabled(true);
        full_image_web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        //фото открываются во всю ширину и длину
        full_image_web.getSettings().setUseWideViewPort(true);
        full_image_web.getSettings().setLoadWithOverviewMode(true);


//        ViewPager page = (ViewPager) findViewById(R.id.pager);
//        PagerAdapter adapter = new PageAdapter(getSupportFragmentManager());
//        page.setAdapter(adapter);

        int position  = intent.getExtras().getInt("id");
        String URL = PhotosInAlmums.getImageInAlbums().get(position);

        full_image_web.loadUrl(URL);

//        Picasso.with(this).load(URL)
//                .into(full_Image);
    }

    //Вызывается при повороте устройства
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

}
