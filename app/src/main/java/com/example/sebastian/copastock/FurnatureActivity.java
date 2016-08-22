package com.example.sebastian.copastock;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.sebastian.copastock.Common.SnackBar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class FurnatureActivity extends AppCompatActivity {

    private View view;
    private TextView contentView;
    private TextView formatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furnature);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        view = findViewById(R.id.furnatureRelative);
        contentView = (TextView) findViewById(R.id.scan_content);
        formatView = (TextView) findViewById(R.id.scan_format);



    }

    public void goScan(View view) {
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            contentView.setText(scanningResult.getContents());
            formatView.setText(scanningResult.getFormatName());
        } else {
            SnackBar.show(view, "No se recibieron los datos del escaneo.");
        }
    }
}
