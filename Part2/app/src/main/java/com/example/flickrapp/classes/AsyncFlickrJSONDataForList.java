package com.example.flickrapp.classes;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncFlickrJSONDataForList  extends AsyncTask<String, Void, JSONObject> {
    private MyAdapter myAdapter;

    public AsyncFlickrJSONDataForList(MyAdapter myAdapter) {
        this.myAdapter = myAdapter;
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
            JSONArray items = jsonObject.getJSONArray("items");

            int arrayLength = items.length();

            //get the url of all photos in our JSONobject
            for (int i = 0; i < arrayLength; i++) {
                String url =items.getJSONObject(i).getJSONObject("media").getString("m");
                myAdapter.add(url);
                Log.i("JFL", "Adding to adapter url : " + url);
            }

            //refresh the adapter to reload it
            myAdapter.notifyDataSetChanged();
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
        s= s.substring(15, s.length() - 1);
        return new JSONObject(s);
    }
}