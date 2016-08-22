package com.example.sebastian.copastock.Receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.sebastian.copastock.Common.Consts;
import com.example.sebastian.copastock.Common.SnackBar;
import com.example.sebastian.copastock.Dialogs.AlertDialog_;
import com.example.sebastian.copastock.VisibilitySetter;

public class ProductExtractReceiver extends BroadcastReceiver{

    private final VisibilitySetter visibilitySetter;
    private Button lessBtn;

    public ProductExtractReceiver(VisibilitySetter visibilitySetter, Button lessBtn) {

        this.visibilitySetter = visibilitySetter;
        this.lessBtn = lessBtn;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getBooleanExtra(Consts.SUCESS, false)) {
            visibilitySetter.clearFields();
            visibilitySetter.visibilityOff();
        } else {
            AlertDialog_.show((Activity) visibilitySetter, "ERROR", "No se pudo realizar la operación.");
        }

        lessBtn.setEnabled(true);

    }
}
