package com.example.sebastian.copastock.InternetTools;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;

import com.example.sebastian.copastock.Common.Consts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Sebastian on 14/08/2016.
 */
public class InternetClient {

    private static final int timeOUT_R = 20000;
    private static final int timeOUT_C = 25000;
    private final Context context;
    private final View view;
    private final String toCall;

    private InputStream is;
    private HttpURLConnection connection;
    private String nURL;

    private String jsonBody;
    private String requestMethod;
    private Map<String, String> headers;
    private boolean expectResponse;
    private int responseCode;

    public static final String CONNECTION = "Connection";

    public InternetClient(Context context, View view, String toCall, String path, Map<String, String> headerM, String rMethod, String jBody, boolean response ) {
        this.context = context;
        this.view = view;
        this.toCall = toCall;
        nURL = "http://192.168.0.11:4567" + path;
        requestMethod = rMethod;
        jsonBody = jBody;
        headers = headerM;
        is = null;
        connection = null;
        expectResponse = response;
        responseCode = 0;
    }

    public void connect() throws IOException {

        try {

            URL urlOK = new URL(nURL);
            connection = (HttpURLConnection) urlOK.openConnection();

            connection.setReadTimeout(timeOUT_R /* milliseconds */);
            connection.setConnectTimeout(timeOUT_C /* milliseconds */);
            connection.setRequestMethod(requestMethod);

            Log.i(CONNECTION, "1");

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            Log.i(CONNECTION, "2");

            if (jsonBody != null){
                connection.setDoOutput(true);
                byte[] outputInBytes = jsonBody.getBytes("UTF-8");
                OutputStream os = connection.getOutputStream();
                os.write( outputInBytes );
                os.close();
            } else {
                connection.setDoOutput(false);
            }

            Log.i(CONNECTION, "3");

            connection.connect();

            Log.i(CONNECTION, "4");

            responseCode = connection.getResponseCode();

            Log.i(CONNECTION, "code: " + responseCode);

            Intent activityMsg = new Intent(toCall);

            if ( responseCode < 300 && responseCode >= 200 ) {
                if (expectResponse) {
                    String jsonResponse =  readIt();
                    activityMsg.putExtra(Consts.JSON_OUT, jsonResponse);
                }
                activityMsg.putExtra(Consts.SUCESS, true);
            }else {
                activityMsg.putExtra(Consts.SUCESS, false);
            }
            LocalBroadcastManager.getInstance(context).sendBroadcast(activityMsg);
        } finally {
            closeConnection();
        }
    }

    private void closeConnection() throws IOException {
        if (connection != null) {
            connection.disconnect();
        }

        if (is != null) {
            is.close();
        }
    }

    private String readIt() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    public void runInBackground() {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadInBackground(this).execute();
        } else {
            callErrorServer();

        }
    }

    public void callErrorServer() {
        Intent activityMsg = new Intent(toCall);
        activityMsg.putExtra(Consts.SUCESS, false);
        activityMsg.putExtra(Consts.RESULT, true); // error de server
        LocalBroadcastManager.getInstance(context).sendBroadcast(activityMsg);
    }
}
