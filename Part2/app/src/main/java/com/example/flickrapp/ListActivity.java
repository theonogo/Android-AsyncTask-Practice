package com.example.flickrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.flickrapp.classes.AsyncFlickrJSONData;
import com.example.flickrapp.classes.AsyncFlickrJSONDataForList;
import com.example.flickrapp.classes.MyAdapter;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView listView = (ListView) findViewById(R.id.list);
        MyAdapter myAdapter = new MyAdapter(this);
        AsyncFlickrJSONDataForList asyncFlickrJSONDataForList = new AsyncFlickrJSONDataForList(myAdapter);
        asyncFlickrJSONDataForList.execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json");

        listView.setAdapter(myAdapter);
    }
}