package com.example.sebastian.copastock.Receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.sebastian.copastock.Common.Consts;
import com.example.sebastian.copastock.Dialogs.AlertDialog_;
import com.example.sebastian.copastock.VisibilitySetter;

import org.json.JSONException;
import org.json.JSONObject;

/**Despues de recibir los nombres de los productos, los agrega al autocomplete y libera el
 * boton de buscar
 */
public class ProductNameStatusReceiver extends BroadcastReceiver{

    private View view;
    private VisibilitySetter visibilitySetter;
    private Button canSearch;

    public ProductNameStatusReceiver(View view, VisibilitySetter visibilitySetter, Button canSearch) {
        this.view = view;
        this.visibilitySetter = visibilitySetter;
        this.canSearch = canSearch;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getBooleanExtra(Consts.SUCESS, false)) {
            try {
                JSONObject jsonResult = new JSONObject(intent.getStringExtra(Consts.JSON_OUT));
                int quantity = jsonResult.getInt(Consts.QUANTITY);
                int result = jsonResult.getInt(Consts.RESULT);          //amarillo, rojo, verde
                visibilitySetter.visibilityOn();
                visibilitySetter.fillFields(quantity, result);
            } catch (JSONException e) {
                AlertDialog_.show((Activity) visibilitySetter, "ERROR", "Problemas con el servidor.");
            }
        } else if (intent.getBooleanExtra(Consts.RESULT, false)) {
            AlertDialog_.show((Activity) visibilitySetter, "ERROR", "No se pudo conectar con el servidor.");
        } else {
            AlertDialog_.show((Activity) visibilitySetter, "FALLÓ", "No se pudo obtener el estado del insumo.");
        }
        canSearch.setEnabled(true);
    }
}
