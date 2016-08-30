package com.example.sebastian.copastock.Receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.sebastian.copastock.Common.Consts;
import com.example.sebastian.copastock.Dialogs.AlertDialog_;
import com.example.sebastian.copastock.Dialogs.AlertStock;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductStatusReceiver extends BroadcastReceiver{

    private Activity activity;

    public ProductStatusReceiver(Activity activity) {

        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getBooleanExtra(Consts.SUCESS, false)) {
            try {
                JSONObject jsonResult = new JSONObject(intent.getStringExtra(Consts.JSON_OUT));
                int quantity = jsonResult.getInt(Consts.QUANTITY);
                int result = jsonResult.getInt(Consts.RESULT);
                String message;
                String title;

                if (result == Consts.RED) {
                    title = "PELIGRO";
                    message = "Quedaron " + quantity + "u. de este producto.";
                } else if (result == Consts.YELLOW) {
                    title = "Alerta";
                    message = "Quedaron " + quantity + "u. de este producto.";
                } else {
                    title = "Ok";
                    message = "Quedaron " + quantity + "u. de este producto.";
                }

                AlertStock.show(activity, title, message, result);

            } catch (JSONException e) {}
        } else if (intent.getBooleanExtra(Consts.RESULT, false)) {
            AlertDialog_.show(activity, "ERROR", "No se pudo conectar con el servidor.");
        } else {
            AlertDialog_.show(activity, "ERROR", "No se pudo obtener el estado del producto luego del retiro.");
        }

    }
}
