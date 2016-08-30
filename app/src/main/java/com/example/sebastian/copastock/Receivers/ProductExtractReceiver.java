package com.example.sebastian.copastock.Receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import com.example.sebastian.copastock.Common.Consts;
import com.example.sebastian.copastock.Dialogs.AlertDialog_;
import com.example.sebastian.copastock.InternetTools.InternetClient;
import com.example.sebastian.copastock.VisibilitySetter;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductExtractReceiver extends BroadcastReceiver{

    private final VisibilitySetter visibilitySetter;
    private Button lessBtn;
    private String materialID;

    public ProductExtractReceiver(VisibilitySetter visibilitySetter, Button lessBtn) {

        this.visibilitySetter = visibilitySetter;
        this.lessBtn = lessBtn;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getBooleanExtra(Consts.SUCESS, false)) {
            try {
                JSONObject jsonO = new JSONObject();
                jsonO.put(Consts.MATERIALS_ID, materialID);
                Context ctx = ((Activity) visibilitySetter).getApplicationContext();
                InternetClient client = new InternetClient(ctx, lessBtn, Consts.PROD_STATUS_call_after,
                        Consts.PROD_STATUS, null, Consts.PUT, jsonO.toString(), true);
                client.runInBackground();
            } catch (JSONException e) {}

            visibilitySetter.clearFields();
            visibilitySetter.visibilityOff();
        } else if (intent.getBooleanExtra(Consts.RESULT, false)) {
            AlertDialog_.show((Activity) visibilitySetter, "ERROR", "No se pudo conectar con el servidor.");
        } else  {
            AlertDialog_.show((Activity) visibilitySetter, "FALLÓ", "No se pudo retirar el producto.");
        }

        lessBtn.setEnabled(true);

    }

    public void setMaterialID(String materialID) {
        this.materialID = materialID;
    }
}
