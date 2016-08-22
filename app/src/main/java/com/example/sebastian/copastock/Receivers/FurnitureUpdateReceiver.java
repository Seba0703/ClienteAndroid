package com.example.sebastian.copastock.Receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.sebastian.copastock.Common.Consts;
import com.example.sebastian.copastock.Dialogs.AlertDialog_;

public class FurnitureUpdateReceiver extends BroadcastReceiver{
    private Activity activity;

    public FurnitureUpdateReceiver(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getBooleanExtra(Consts.SUCESS, false)) {
            AlertDialog_.show(activity, "Éxito", "Operación realizada con éxito");
        } else {
            AlertDialog_.show(activity, "FALLÓ", "La operación no se pudo realizar.");
        }
    }
}
