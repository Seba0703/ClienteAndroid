package com.example.sebastian.copastock.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.sebastian.copastock.Common.Consts;
import com.example.sebastian.copastock.InternetTools.InternetClient;
import com.example.sebastian.copastock.R;

import org.json.JSONException;
import org.json.JSONObject;

public class FurnitureUpdateDialog extends DialogFragment {

    private Activity activity;

    public void attach(Activity activity) {
        this.activity = activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle args) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(args.getString(Consts.MSSG));
        LayoutInflater inflater = activity.getLayoutInflater();
        final View view = inflater.inflate(R.layout.furniture_update, null);
        final RadioGroup rGrpup = (RadioGroup) view.findViewById(R.id.radioGroup);
        final int memberID = args.getInt(Consts.N_MEMBER);
        final int sucID = args.getInt(Consts.N_SUC);
        builder.setView(view).setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                int rdBtnID = rGrpup.getCheckedRadioButtonId();

                if (rdBtnID != -1) {
                    RadioButton rBtn = (RadioButton) view.findViewById(rdBtnID);
                    String json = buildJson(Consts.mapStateStringInt(rBtn.getText().toString()), sucID, memberID);
                    if (json != null) {
                        InternetClient client = new InternetClient(activity.getApplicationContext(),
                                view, Consts.FURNITURE_UPDATE_call, Consts.FURNITURE_UPDATE, null, Consts.PUT, json, false);
                        client.runInBackground();
                    }
                } else {
                    AlertDialog_.show(activity, "NULL","Por favor elija una opción.");
                }
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return builder.create();
    }

    private String buildJson(int state, int sucID, int memberID) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Consts.N_SUC, sucID);
            jsonObject.put(Consts.N_MEMBER, memberID);
            jsonObject.put(Consts.STATE, state);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


}
