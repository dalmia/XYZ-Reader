package com.example.xyzreader;

import android.app.Application;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by aman on 17/7/16.
 */
public class AppController extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
        Picasso instance = builder.build();
        instance.setIndicatorsEnabled(true);
        if(BuildConfig.DEBUG){
            instance.setLoggingEnabled(true);
        }
        Picasso.setSingletonInstance(instance);
    }
}
