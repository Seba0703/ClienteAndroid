package com.example.sebastian.copastock;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.sebastian.copastock.Common.Consts;
import com.example.sebastian.copastock.Common.DisablerButton;
import com.example.sebastian.copastock.Dialogs.AlertDialog_;
import com.example.sebastian.copastock.InternetTools.InternetClient;
import com.example.sebastian.copastock.Receivers.FurnitureHasReceiver;
import com.example.sebastian.copastock.Receivers.FurnitureNotUpdateReceiver;
import com.example.sebastian.copastock.Receivers.FurnitureUpdateReceiver;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class FurnatureActivity extends AppCompatActivity {

    private View view;
    private FurnitureHasReceiver furnitureHasR;
    private FurnitureUpdateReceiver furnitureUpdate;
    private FurnitureNotUpdateReceiver notUpdCount;
    private Button goCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furnature);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        view = findViewById(R.id.furnatureRelative);

        goCount = (Button) findViewById(R.id.button6);

        furnitureHasR = new FurnitureHasReceiver(this);
        furnitureUpdate = new FurnitureUpdateReceiver(this);
        notUpdCount = new FurnitureNotUpdateReceiver(this, goCount);
    }

    public void goCount(View view) {
        goCount.setEnabled(false);
        InternetClient client = new InternetClient(getApplicationContext(), view, Consts.FURNITURE_NOT_UPDATE_call,
                Consts.FURNITURE_NOT_UPDATE, null, Consts.GET, null, true);
        client.runInBackground();
    }

    public void goScan(View view) {
        Button goScan = (Button) findViewById(R.id.button9);
        DisablerButton.disable(goScan, 200);
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode != Consts.INFO_CODE) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanningResult != null) {
                String code = scanningResult.getContents();
                if (code != null) {

                    int sucID = Integer.parseInt(code.substring(0, 1));
                    int memberID = Integer.parseInt(code.substring(1));

                    if (sucID == Consts.NAZCA_ID) {
                        JSONObject body = buildBody(sucID, memberID);
                        if (body != null) {
                            furnitureHasR.setCode(sucID, memberID);
                            InternetClient client = new InternetClient(getApplicationContext(), view,
                                    Consts.FURNITURE_HAS_call, Consts.FURNITURE_HAS, null, Consts.PUT, body.toString(), true);
                            client.runInBackground();
                        }
                    } else {
                        AlertDialog_.show(this, "", "Este código pertence a " + Consts.nameMatcher(sucID) + " y no puede ser agregado. Si lo desea agregar genere el código propio de NAZCA y agreguelo.");
                    }
                }
            } else {
                AlertDialog_.show(this, "ERROR", "No se recibieron los datos del escaneo.");
            }
        } else {
            if (resultCode == RESULT_OK) {
                if (intent.getBooleanExtra(Consts.SUCESS, false)) {
                    AlertDialog_.show(this, "Éxito", "Bien agregado con éxito.");
                } else if (intent.getBooleanExtra(Consts.RESULT, false)) {
                    AlertDialog_.show(this, "ERROR", "No se pudo conectar con el servidor.");
                } else {
                    AlertDialog_.show(this, "FALLÓ", "No se pudo agregar el bien.");
                }
            }
        }
    }

    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(furnitureHasR,
                new IntentFilter(Consts.FURNITURE_HAS_call));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(furnitureUpdate,
                new IntentFilter(Consts.FURNITURE_UPDATE_call));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(notUpdCount,
                new IntentFilter(Consts.FURNITURE_NOT_UPDATE_call));
    }

    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(furnitureHasR);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(furnitureUpdate);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(notUpdCount);
    }

    private JSONObject buildBody(int sucID, int prodID) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Consts.N_SUC, sucID);
            jsonObject.put(Consts.N_MEMBER, prodID);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
