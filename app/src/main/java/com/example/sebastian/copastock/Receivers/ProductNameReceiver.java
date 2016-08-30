package com.example.sebastian.copastock.Receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.sebastian.copastock.Common.Consts;
import com.example.sebastian.copastock.Dialogs.AlertDialog_;
import com.example.sebastian.copastock.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ProductNameReceiver extends BroadcastReceiver{

    private List<String> names;
    private Activity activity;
    private AutoCompleteTextView autoCompleteTextView;

    public ProductNameReceiver(Activity activity, AutoCompleteTextView autoCompleteTextView, List<String> names) {
        this.activity = activity;
        this.autoCompleteTextView = autoCompleteTextView;
        this.names = names;
    }

    @Override
    public void onReceive(Context cxt, Intent intent) {
        if(intent.getBooleanExtra(Consts.SUCESS, false)) {
            String json = intent.getStringExtra(Consts.JSON_OUT);

            try {
                JSONObject jsonNames = new JSONObject(json);
                JSONArray jsonArray = jsonNames.getJSONArray(Consts.RESULT);
                for (int i = 0; i < jsonArray.length(); i++) {
                    names.add(((JSONObject)jsonArray.get(i)).getString(Consts.MATERIALS_ID));
                }

                final ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
                        R.layout.auto_complete_layout, names);
                autoCompleteTextView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
                AlertDialog_.show(activity, "ERROR", "Problemas con el servidor.");
            }
        } else if (intent.getBooleanExtra(Consts.RESULT, false)) {
            AlertDialog_.show(activity, "ERROR", "No se pudo conectar con el servidor.");
        } else {
            AlertDialog_.show(activity, "ERROR", "No se pudo obtener los nombres de los insumos.");
        }
    }
}
