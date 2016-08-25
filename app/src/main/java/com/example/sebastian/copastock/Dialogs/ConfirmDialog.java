package com.example.sebastian.copastock.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.example.sebastian.copastock.Common.Consts;
import com.example.sebastian.copastock.OnClickDialog;


public class ConfirmDialog extends DialogFragment {

    private OnClickDialog onClick;
    private Activity activity;

    public void attachResponse(OnClickDialog dialog, Activity activity) {
        onClick = dialog;
        this.activity = activity;
    }

    @Override
    public Dialog onCreateDialog(final Bundle args) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(args.getString(Consts.MSSG))
                .setPositiveButton(args.getString(Consts.ACEPT), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onClick.onAcept();
                    }
                })
                .setNegativeButton(args.getString(Consts.CANCEL), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        return builder.create();
    }

}
