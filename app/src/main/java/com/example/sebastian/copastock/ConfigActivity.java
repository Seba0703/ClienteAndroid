package com.example.sebastian.copastock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sebastian.copastock.application.CopaStock;

public class ConfigActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText;
    private CopaStock tripTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        tripTP = (CopaStock) getApplication();
        editText = (EditText) findViewById(R.id.editText);
        editText.setText(tripTP.getUrl());

        Button btn = (Button) findViewById(R.id.button2);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String ip = editText.getText().toString();
        tripTP.setUrl(ip);
    }
}
