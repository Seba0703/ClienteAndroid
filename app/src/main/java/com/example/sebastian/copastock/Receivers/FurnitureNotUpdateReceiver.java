package com.example.sebastian.copastock.Receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.sebastian.copastock.Common.Consts;
import com.example.sebastian.copastock.Dialogs.AlertDialog_;

public class FurnitureNotUpdateReceiver extends BroadcastReceiver{


    private Activity activity;

    public FurnitureNotUpdateReceiver(Activity furnatureActivity) {
        this.activity = furnatureActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getBooleanExtra(Consts.SUCESS, false)) {
            int intCount = Integer.parseInt(intent.getStringExtra(Consts.JSON_OUT));
            AlertDialog_.show(activity, "Desactualizados", "Aún quedan por actualizar " + intCount + " objetos.");
        } else {
            AlertDialog_.show(activity, "ERROR", "No se pudo obtener la información.");
        }
    }
}
