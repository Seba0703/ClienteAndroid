package com.example.sebastian.copastock.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.sebastian.copastock.Common.Consts;

public class AlertDialog_ {

    public static void show(Context act, String title, String message) {
        AlertDialog alert = new AlertDialog.Builder(act).create();

        alert.setTitle(title);
        alert.setMessage(message);
        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alert.show();
    }

}