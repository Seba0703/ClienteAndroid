package com.example.sebastian.copastock.Receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.sebastian.copastock.Common.Consts;
import com.example.sebastian.copastock.Dialogs.ConfirmDialog;
import com.example.sebastian.copastock.Dialogs.FurnitureUpdateDialog;
import com.example.sebastian.copastock.FurnitureInfoActivity;
import com.example.sebastian.copastock.OnClickDialog;

import org.json.JSONException;
import org.json.JSONObject;

public class FurnitureHasReceiver extends BroadcastReceiver implements OnClickDialog{


    private int sucN;
    private int memberN;
    private Activity activity;
    private boolean has_Furniture;

    public FurnitureHasReceiver(Activity activity) {
        this.activity = activity;
        has_Furniture = false;
    }

    public void setCode(int sucN, int memberN) {
        this.sucN = sucN;
        this.memberN = memberN;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getBooleanExtra(Consts.SUCESS, false)) {
            try {
                JSONObject result = new JSONObject(intent.getStringExtra(Consts.JSON_OUT));
                ConfirmDialog dialog = new ConfirmDialog();
                dialog.attachResponse(this, activity);

                Bundle args = new Bundle();
                args.putString(Consts.ACEPT, "Aceptar");
                args.putString(Consts.CANCEL, "Cancelar");
                String text;

                has_Furniture = result.getBoolean(Consts.HAS_FURNITURE);

                if (has_Furniture) {
                    String areSure = "¿Desea actualizar el estado del objeto número ";
                    text = areSure + memberN + "?";

                } else {
                    String areSure = "¿Desea agregar un nuevo objeto con número ";
                    text = areSure + memberN + " al sistema?";
                }

                args.putString(Consts.MSSG, text);
                dialog.onCreateDialog(args).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {

        }

    }

    @Override
    public void onAcept() {
        if (has_Furniture) {
            FurnitureUpdateDialog infoDialog = new FurnitureUpdateDialog();
            infoDialog.attach(activity);
            Bundle args = new Bundle();
            args.putInt(Consts.N_MEMBER, memberN);
            args.putInt(Consts.N_SUC, sucN);
            args.putString(Consts.MSSG, "¿Cómo se encuentra actualmente el objeto?");
            infoDialog.onCreateDialog(args).show();
        }else {
            Intent furnitureInfo = new Intent(activity, FurnitureInfoActivity.class);
            furnitureInfo.putExtra(Consts.N_MEMBER, memberN);
            furnitureInfo.putExtra(Consts.N_SUC, sucN);
            activity.startActivityForResult(furnitureInfo, Consts.INFO_CODE);
        }
    }

    @Override
    public void onCancel() {

    }
}
