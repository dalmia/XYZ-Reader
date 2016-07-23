package com.example.xyzreader;

import android.app.Application;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Application instance
 */
public class AppController extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Build a Picasso Instance
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
        Picasso instance = builder.build();
        instance.setIndicatorsEnabled(false);
        if(BuildConfig.DEBUG){
            instance.setLoggingEnabled(true);
        }
        // Set the singleton instance
        Picasso.setSingletonInstance(instance);
    }
}
