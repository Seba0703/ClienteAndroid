package com.example.sebastian.copastock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.sebastian.copastock.Common.ActivityStarter;
import com.example.sebastian.copastock.Common.DisablerButton;

public class ChooseModelActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_model);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void goProducts(View view) {
        Button goProd = (Button) findViewById(R.id.button2);
        DisablerButton.disable(goProd, 250);
        ActivityStarter.start(getApplicationContext(), ProductLessActivity.class);
    }

    public void goMuebles(View view) {
        Button goMuebles = (Button) findViewById(R.id.button3);
        DisablerButton.disable(goMuebles, 250);
        ActivityStarter.start(getApplicationContext(), FurnatureActivity.class);
    }

}
