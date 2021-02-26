package com.example.flickrapp.classes;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncFlickrJSONData extends AsyncTask<String, Void, JSONObject> {
    private final WeakReference<ImageView> imageViewReference;

    public AsyncFlickrJSONData(ImageView imageView) {
        imageViewReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        JSONObject jsonObject = null;

        try {
            URL url = new URL(strings[0]);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                //We get the result from the URL
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                //our custom readStream method allows us to convert an InputStream to a String
                String s = readStream(in);

                //We convert the acquired string back into a JSONObject
                jsonObject = string2JSON(s);
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        Log.i("JFL", jsonObject.toString());

        try {
            String stringUrl;
            if(jsonObject.has("title"))
            {
                stringUrl = jsonObject.getJSONArray("items").getJSONObject(0)
                        .getJSONObject("media").getString("m");
            } else {
                jsonObject = jsonObject.getJSONObject("photos").getJSONArray("photo").getJSONObject(0);
                String server = jsonObject.getString("server");
                String id = jsonObject.getString("id");
                String secret = jsonObject.getString("secret");

                stringUrl = "https://live.staticflickr.com/"+ server +"/"+ id +"_"+ secret +".jpg";
            }

            Log.i("JFL", stringUrl);

            AsyncBitmapDownloader asyncBitmapDownloader = new AsyncBitmapDownloader(imageViewReference.get());
            asyncBitmapDownloader.execute(stringUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }

    private JSONObject string2JSON(String s) throws JSONException {
        s= s.substring(14, s.length() - 1);
        if(s.charAt(0)=='(') { s = s.substring(1); }
        return new JSONObject(s);
    }
}
