package com.example.flickrapp.classes;

import android.view.View;

import java.net.MalformedURLException;
import java.net.URL;

public class GetImageOnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        try {
            AsyncFlickrJSONData asyncTask = new AsyncFlickrJSONData(new URL("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
