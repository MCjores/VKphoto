package com.mccorporation.mcjores.vkphotoapp;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhotoAlbum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String[] scope = new String[]{VKScope.FRIENDS, VKScope.PHOTOS, VKScope.ADS};
    private GridAdapter gridAdapter;
    private ImageAdapter imageAdapter;
    private ArrayList<Integer> imageIdsAlbum;
    private ArrayList<String> imageURLinAlbum;
    private static ArrayList<String> text1;
    private ArrayList<String> text;
    private ArrayList<Integer> textPhotoVKcount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        text = new ArrayList<>();
        text1 = new ArrayList<>();
        imageURLinAlbum = new ArrayList<>();
        imageIdsAlbum = new ArrayList<>();
        textPhotoVKcount = new ArrayList<>();



//        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
//        Log.i("onCreate", String.valueOf(Arrays.asList(fingerprints)));
        VKSdk.login(this, scope);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                getPhotoAlbumRequest();
//                getPhotoInAlbumRequest();
                Toast.makeText(MainActivity.this, "Добро пожаловать", Toast.LENGTH_SHORT).show();



                final GridView gridViewForAlbums = (GridView) findViewById(R.id.grid_view);
//                final GridView gridViewForPhotoInAlbum =  (GridView) findViewById(R.id.grid_view);

                gridViewForAlbums.setAdapter(gridAdapter);

                gridViewForAlbums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        //______________НЕ РАБОТАЕТ null_________________
                        ViewPager page = (ViewPager) findViewById(R.id.pager);
                        PageAdapter adapter = new PageAdapter(getApplicationContext(),text1);
                        if(page!=null)
                            page.setAdapter(adapter);
                        //___________________________________________


                        Intent intent = new Intent(getApplicationContext(),PhotosInAlmums.class);
                        int album_pos = gridAdapter.getAlbumId(position);


                        intent.putExtra("id_album",album_pos);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(MainActivity.this, "Для работы требуется авторизация", Toast.LENGTH_LONG).show();
                Log.i("onActivityResult", "onError " + error.errorMessage);

            }
        })) {

            super.onActivityResult(requestCode, resultCode, data);
        }


    }

    private void getPhotoAlbumRequest() {
        Log.i("test", "getPhoto");
        final VKRequest request = new VKRequest("photos.getAlbums?need_covers=true", VKParameters.from(), VKApiPhotoAlbum.class);

        request.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                Log.i("Grid", response.responseString);

                VKApiPhotoAlbum photos = (VKApiPhotoAlbum) response.parsedModel;

                String reader = response.json.toString();
                requestToJSON(reader);

                Log.i("Grid", "titlw " + photos.id);

            }
        });
    }

    private void requestToJSON(String jsonString) {
        Log.i("request", "запрос" + jsonString);

        if (jsonString != null) {
            try {

                JSONObject object = new JSONObject(jsonString);
                JSONObject object1 = object.getJSONObject("response");
                JSONArray items = object1.getJSONArray("items");

                Log.i("request", "json ");


                for (int i = 0; i < items.length(); i++) {

                    //title
                    JSONObject c = items.getJSONObject(i);
                    this.text.add(c.getString("title"));
                    Log.i("request", "title = " + c.getString("title"));

                    //ссылка на изображение
                    this.text1.add(c.getString("thumb_src"));

                    //id альбома
                    this.imageIdsAlbum.add(c.getInt("id"));

                    this.textPhotoVKcount.add(c.getInt("size"));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i("grid","count = " + textPhotoVKcount.toString());
        gridAdapter = new GridAdapter(MainActivity.this,text,text1, imageIdsAlbum, textPhotoVKcount);
    }

//    public class PageAdapter extends FragmentPagerAdapter {
//        public PageAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return PageFragmet.newInstance(position);
//        }
//
//        @Override
//        public int getCount() {
//            return 0;   //count page
//        }
//    }

}
