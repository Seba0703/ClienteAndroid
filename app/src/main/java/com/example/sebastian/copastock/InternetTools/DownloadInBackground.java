package com.example.sebastian.copastock.InternetTools;

import android.os.AsyncTask;
import android.widget.Button;

import java.io.IOException;

/**
 * Created by Sebastian on 14/08/2016.
 */
public class DownloadInBackground extends AsyncTask<String, Void, Integer> {

    private InternetClient client;

    public DownloadInBackground(InternetClient client) {
        this.client = client;
    }

    @Override
    protected Integer doInBackground(String... urls) {

        // params comes from the execute() call: params[0] is the url.
        try {
            client.connect();
        } catch (IOException e) {
            client.callErrorServer();
            client.show( "No se pudo conectar a la red.");
            //return -1;
        }

        return 1;
    }

    @Override
    protected void onPostExecute(Integer result) {
    }

}
