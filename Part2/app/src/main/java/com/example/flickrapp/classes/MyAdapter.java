package com.example.flickrapp.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.flickrapp.R;

import java.util.Vector;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private Vector<String> vector;

    public MyAdapter(Context context) {
        this.context = context;
        vector = new Vector<>();
    }

    public void add(String s) {
        vector.add(s);
    }

    @Override
    public int getCount() {
        return vector.size();
    }

    @Override
    public Object getItem(int position) {
        return vector.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String url = (String) getItem(position);

        RequestQueue queue = MySingleton.getInstance(context).getRequestQueue();

        if(convertView==null) {
            //convertView = LayoutInflater.from(context).inflate(R.layout.textviewlayout,parent,false);
            convertView = LayoutInflater.from(context).inflate(R.layout.bitmaplayout,parent,false);
        }

        final ImageView imageView = (ImageView) convertView.findViewById(R.id.bitmap_image);
        Response.Listener<Bitmap> rep_listener = response -> {
            imageView.setImageBitmap(response);
        };

        ImageRequest imageRequest = new ImageRequest(url, rep_listener, 1000, 1000, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888, null);
        queue.add(imageRequest);

        /*
        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        textView.setText(url);
         */
        return convertView;
    }
}
