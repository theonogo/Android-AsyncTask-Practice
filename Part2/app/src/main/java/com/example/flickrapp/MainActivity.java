package com.example.flickrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.flickrapp.classes.AsyncBitmapDownloader;
import com.example.flickrapp.classes.GetImageOnClickListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getImageButton = (Button) findViewById(R.id.get_image_button);
        Button listViewButton = (Button) findViewById(R.id.list_button);
        ImageView image = (ImageView) findViewById(R.id.image);

        getImageButton.setOnClickListener( new GetImageOnClickListener());
        AsyncBitmapDownloader asyncBitmapDownloader = new AsyncBitmapDownloader(image);
        asyncBitmapDownloader.execute("https://live.staticflickr.com/65535/50980844581_668e14b1cf_m.jpg");
        //this helps https://stackoverflow.com/questions/13416644/what-is-the-best-way-for-asynctask-to-notify-parent-activity-about-completion
        listViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launch = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(launch);
            }
        });
    }
}