package com.example.sebastian.copastock;

import android.app.Dialog;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sebastian.copastock.Common.Consts;
import com.example.sebastian.copastock.Dialogs.AlertDialog_;
import com.example.sebastian.copastock.Dialogs.ConfirmDialog;
import com.example.sebastian.copastock.InternetTools.InternetClient;
import com.example.sebastian.copastock.Receivers.ProductExtractReceiver;
import com.example.sebastian.copastock.Receivers.ProductNameReceiver;
import com.example.sebastian.copastock.Receivers.ProductNameStatusReceiver;
import com.example.sebastian.copastock.Receivers.ProductStatusReceiver;
import com.example.sebastian.copastock.Singleton.UserSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductLessActivity extends AppCompatActivity implements VisibilitySetter, OnClickDialog{

    private View view;
    private TextView actualStock;
    private EditText countEdit;
    private Spinner spinner;
    private Button lessBtn;
    private AutoCompleteTextView auto;
    private List<String> names;
    private int actualQuantity;
    private String nameSearched;
    private TextView destinyLabel;
    private Button searchBtn;
    private TextView count;
    private ProductExtractReceiver onProdExtract;
    private ProductNameReceiver onProdsName;
    private ProductNameStatusReceiver onProdStatus;
    private ProductStatusReceiver onProdExtrStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_less);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        view = findViewById(R.id.lessRelative);
        auto = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        names = new ArrayList<>();
        actualQuantity = 0;
        searchBtn = (Button) findViewById(R.id.button4);
        lessBtn = (Button) findViewById(R.id.button5);

        // recibo los nombres de los productos
        onProdsName = new ProductNameReceiver(this, auto, names);

        // de un producto en particular recibo la cantidad y su estado ROJO, NARAJA, BLANCO
        onProdStatus = new ProductNameStatusReceiver(view, this, searchBtn);

        //recibo la confirmacion de que se saco correctamente el producto
        onProdExtract = new ProductExtractReceiver(this, lessBtn);

        //luego de retirado las cantidad del producto, se pide el estado del mismo
        onProdExtrStatus = new ProductStatusReceiver(this);

        //Descargo nombres de la base de datos
        InternetClient client = new InternetClient(getApplicationContext(), view, Consts.PROD_NAME, Consts.MAT_MATERIALS_ID,
                null, Consts.GET, null, true);
        client.runInBackground();

        actualStock = (TextView) findViewById(R.id.textView2);
        count = (TextView) findViewById(R.id.textView4);
        countEdit = (EditText) findViewById(R.id.editText3);
        spinner = (Spinner) findViewById(R.id.spinner);
        destinyLabel = (TextView) findViewById(R.id.textView3);
        String[] list = {Consts.NAZCA, Consts.EVA, Consts.CELINA, Consts.NOTHING};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list);
        spinner.setAdapter(adapter);
        spinner.setSelection(3);

        visibilityOff();
    }

    public void searchStock(View v) {

        clearFields();

        if (names.contains(auto.getText().toString().toUpperCase())) {
            searchBtn.setEnabled(false);

            try {
                JSONObject jsonO = new JSONObject();
                jsonO.put(Consts.MATERIALS_ID, auto.getText().toString());
                InternetClient client = new InternetClient(getApplicationContext(), view, Consts.PROD_STATUS_call, Consts.PROD_STATUS,
                        null, Consts.PUT, jsonO.toString(), true);
                client.runInBackground();
                nameSearched = auto.getText().toString().toUpperCase();
            } catch (JSONException e) {
                AlertDialog_.show(this, "ERROR", "Error en el servidor.");
            }
        } else {
            AlertDialog_.show(this, "ERROR", "Ingrese un nombre de un insumo existente.");
        }
    }

    @Override
    public void visibilityOn() {
        actualStock.setVisibility(View.VISIBLE);
        count.setVisibility(View.VISIBLE);
        countEdit.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.VISIBLE);
        lessBtn.setVisibility(View.VISIBLE);
        destinyLabel.setVisibility(View.VISIBLE);
    }

    @Override
    public void fillFields(int quantity, int result) {
        actualQuantity = quantity;
        String text = String.valueOf(quantity);
        Spannable spannable = new SpannableString(text);
        ForegroundColorSpan fcs;
        if (result == Consts.RED) {
            fcs = new ForegroundColorSpan(Color.rgb(178, 34, 34));      //firebrick
        } else if (result == Consts.YELLOW) {
            fcs = new ForegroundColorSpan(Color.rgb(255, 165, 0));      //orange
        } else {
            fcs = new ForegroundColorSpan(Color.rgb(34, 139, 34));      //forest green
        }
        spannable.setSpan(fcs, 0, text.length(),Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new StyleSpan(Typeface.BOLD), 0, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        count.setText(spannable);
    }

    @Override
    public void visibilityOff() {
        actualStock.setVisibility(View.GONE);
        count.setVisibility(View.GONE);
        countEdit.setVisibility(View.GONE);
        spinner.setVisibility(View.GONE);
        lessBtn.setVisibility(View.GONE);
        destinyLabel.setVisibility(View.GONE);
    }

    @Override
    public void clearFields() {
        spinner.setSelection(3);
        countEdit.setText("");
        count.setText("");
    }

    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(onProdsName,
                new IntentFilter(Consts.PROD_NAME));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(onProdStatus,
                new IntentFilter(Consts.PROD_STATUS_call));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(onProdExtrStatus,
                new IntentFilter(Consts.PROD_STATUS_call_after));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(onProdExtract,
                new IntentFilter(Consts.PROD_EXTRACT));
    }

    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(onProdsName);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(onProdStatus);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(onProdExtrStatus);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(onProdExtract);
    }

    public void extract(View v) {
        boolean badInput;
        String quantityS = countEdit.getText().toString();
        int lessQuantity = 0;
        try {
            lessQuantity = Integer.parseInt(quantityS);
            badInput = lessQuantity > actualQuantity || lessQuantity <= 0;
        }catch (NumberFormatException e) {
            badInput = true;
        }

        String selection =  spinner.getSelectedItem().toString();
        if (quantityS.isEmpty() || badInput || selection.equals(Consts.NOTHING) || !nameSearched.equals(auto.getText().toString().toUpperCase())) {
            AlertDialog_.show(this, "ERROR", "Seleccione un destino y una cantidad válida.");
        } else {
            lessBtn.setEnabled(false);

            Bundle args = new Bundle();
            args.putString(Consts.ACEPT, "Aceptar");
            args.putString(Consts.CANCEL, "Cancelar");

            String areSure = "¿Esta seguro de retirar, ";
            String units = "u. del producto ";
            String text = areSure + lessQuantity + units + nameSearched + "?";

            args.putString(Consts.MSSG, text);

            ConfirmDialog confirm = new ConfirmDialog();
            confirm.attachResponse(this, this);

            Dialog d = confirm.onCreateDialog(args);
            d.show();
        }
    }

    @Override
    public void onAcept() {
        try {
            int lessQuantity = Integer.parseInt(countEdit.getText().toString());
            JSONObject jsonRq = new JSONObject();
            onProdExtract.setMaterialID(nameSearched);
            jsonRq.put(Consts.MATERIALS_ID, nameSearched);
            jsonRq.put(Consts.QUANTITY, lessQuantity);
            jsonRq.put(Consts.USER, UserSingleton.getInstance().getUserName());
            jsonRq.put(Consts.DESTINY, Consts.getMapNameSuc(spinner.getSelectedItem().toString()));
            InternetClient client = new InternetClient(getApplicationContext(), view,
                    Consts.PROD_EXTRACT, Consts.MAT_SUB, null, Consts.PUT, jsonRq.toString(), false);
            client.runInBackground();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCancel() {
        lessBtn.setEnabled(true);
    }
}
