package com.example.flickrapp.classes;

import android.view.View;

import java.net.MalformedURLException;
import java.net.URL;

public class GetImageOnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        AsyncFlickrJSONData asyncTask = new AsyncFlickrJSONData();
        asyncTask.execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json");
    }
}
