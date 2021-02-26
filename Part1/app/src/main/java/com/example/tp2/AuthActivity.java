package com.example.tp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class AuthActivity extends AppCompatActivity {
    private String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        //Get the appropriate objects from their xml IDs
        Button buttonAuth = (Button) findViewById(R.id.ButtonAuth);
        EditText loginBox = (EditText) findViewById(R.id.editTextLogin);
        EditText passwordBox = (EditText) findViewById(R.id.editTextPassword);
        TextView result = (TextView) findViewById(R.id.textResult);

        //Start a listener waiting for a button click
        buttonAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread() {
                    public void run() {
                        URL url = null;
                        try {
                            //url needs to be https be authorized
                            url = new URL("https://httpbin.org/basic-auth/bob/sympa");
                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                            //Since the address requires a username and password, we acquire these from the
                            // user and put them in our HttpURLConnection object
                            String authInfo = loginBox.getText() + ":" + passwordBox.getText();
                            String basicAuth = "Basic " + Base64.encodeToString(authInfo.getBytes(),
                                    Base64.NO_WRAP);
                            urlConnection.setRequestProperty ("Authorization", basicAuth);

                            try {
                                //We get the result from the URL
                                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                                //our custom readStream method allows us to convert an InputStream to a String
                                String s = readStream(in);
                                //We log our resulting string to see in in logcat and identify errors more easily
                                Log.i("JFL", s);

                                //We convert the acquired string back into a JSONObject to read the value of the
                                // authenticated property
                                JSONObject jsonO = new JSONObject(s);

                                //We store the result in the res property of the activity so it can be accessed in
                                // the runnable
                                res = jsonO.getString("authenticated");

                                //We refresh the result TextView in runOnUiThread so this code runs on the main thread
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        result.setText(AuthActivity.this.res);
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } finally {
                                urlConnection.disconnect();
                            }
                        }
                        //We of course catch the appropriate exceptions and log the error message
                        catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();


            }
        });
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
}