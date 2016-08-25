package com.example.sebastian.copastock;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.sebastian.copastock.Common.ActivityStarter;
import com.example.sebastian.copastock.Common.Consts;
import com.example.sebastian.copastock.Common.DisablerButton;
import com.example.sebastian.copastock.Common.SnackBar;
import com.example.sebastian.copastock.Dialogs.AlertDialog_;
import com.example.sebastian.copastock.InternetTools.InternetClient;
import com.example.sebastian.copastock.Receivers.FurnitureHasReceiver;
import com.example.sebastian.copastock.Receivers.FurnitureUpdateReceiver;
import com.example.sebastian.copastock.Singleton.UserSingleton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class ChooseModelActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_model);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.i("CHOOSE", UserSingleton.getInstance().getUserName());
    }

    public void goProducts(View view) {
        Button goProd = (Button) findViewById(R.id.button2);
        DisablerButton.disable(goProd, 250);
        ActivityStarter.start(getApplicationContext(), ProductLessActivity.class);
    }

    public void goMuebles(View view) {
        final Button goMuebles = (Button) findViewById(R.id.button3);
        DisablerButton.disable(goMuebles, 250);
        ActivityStarter.start(getApplicationContext(), FurnatureActivity.class);
    }

}
