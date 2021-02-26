package com.example.flickrapp.classes;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.net.MalformedURLException;
import java.net.URL;

public class GetImageOnClickListener implements View.OnClickListener {
    private LocationManager locationManager;
    private ImageView image;

    public GetImageOnClickListener(LocationManager locationManager, ImageView image) {
        this.locationManager = locationManager;
        this.image = image;
    }

    @Override
    public void onClick(View v) {
        AsyncFlickrJSONData asyncTask = new AsyncFlickrJSONData(image);
        //We obtain the location on click so that we know the location is updated
        //permissions have been obtained in the MainActivity
        Location loc = locationManager.getLastKnownLocation("gps");
        String url = "https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json";

        //if we have our location, we can construct our api request with also our api key
        if(loc!=null) {
            url ="https://api.flickr.com/services/rest/?" +
                    "method=flickr.photos.search" +
                    "&license=4" +
                    "&api_key=5e3a8c504d042f524148c05473b40665" +
                    "&has_geo=1&lat=" + loc.getLatitude() +
                    "&lon=" + loc.getLongitude() + "&per_page=1&format=json";
            Log.i("JFL", url);
            Log.i("JFL", "Location Acquired");
        }

        //start our assynchronous obtention of image url
        asyncTask.execute(url);
    }
}
