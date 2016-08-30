package com.example.sebastian.copastock.Receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import com.example.sebastian.copastock.Common.Consts;
import com.example.sebastian.copastock.Dialogs.AlertDialog_;

public class FurnitureNotUpdateReceiver extends BroadcastReceiver{


    private Activity activity;
    private Button goCount;

    public FurnitureNotUpdateReceiver(Activity furnatureActivity, Button goCount) {
        this.activity = furnatureActivity;
        this.goCount = goCount;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        goCount.setEnabled(true);
        if (intent.getBooleanExtra(Consts.SUCESS, false)) {
            int intCount = Integer.parseInt(intent.getStringExtra(Consts.JSON_OUT));
            AlertDialog_.show(activity, "Desactualizados", "Aún quedan por actualizar " + intCount + " bienes.");
        } else if (intent.getBooleanExtra(Consts.RESULT, false)) {
            AlertDialog_.show(activity, "ERROR", "No se pudo conectar con el servidor.");
        } else {
            AlertDialog_.show(activity, "FALLÓ", "No se pudo obtener la información sobre bienes no actualizados.");
        }
    }
}
