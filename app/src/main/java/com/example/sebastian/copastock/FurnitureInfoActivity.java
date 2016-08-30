package com.example.sebastian.copastock;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.sebastian.copastock.Common.Consts;
import com.example.sebastian.copastock.Dialogs.AlertDialog_;
import com.example.sebastian.copastock.Dialogs.DatePickerDialog;
import com.example.sebastian.copastock.InternetTools.InternetClient;
import com.example.sebastian.copastock.Receivers.SendFurnitureInfoReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class FurnitureInfoActivity extends AppCompatActivity {

    private TextView date;
    private RadioGroup selection;
    private EditText price;
    private int sucN;
    private int memberN;
    private View view;
    private SendFurnitureInfoReceiver sendInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furniture_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sucN = getIntent().getIntExtra(Consts.N_SUC, -1);
        memberN = getIntent().getIntExtra(Consts.N_MEMBER, -1);

        date = (TextView) findViewById(R.id.dateText);
        price = (EditText) findViewById(R.id.editText4);
        selection = (RadioGroup) findViewById(R.id.radioGroup2);
        view = findViewById(R.id.infoRelative);

        sendInfo = new SendFurnitureInfoReceiver(this);
    }

    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(sendInfo,
                new IntentFilter(Consts.FURNITURE_ADD));
    }

    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(sendInfo);
    }

    public void goCalendar(View view) {
        DatePickerDialog dialogFragment = new DatePickerDialog();
        dialogFragment.attach(this);
        dialogFragment.setDate(date);
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onCancel(View view) {
        this.finish();
    }

    public void sendInfo(View view) {
        int rdBtnID = selection.getCheckedRadioButtonId();
        double priceD = 0d;
        boolean badPrice;
        if (!price.getText().toString().isEmpty()) {
            try {
                priceD = Double.parseDouble(price.getText().toString());
                badPrice = false;
            }catch (NumberFormatException e) {
                badPrice = true;
            }
        } else {
            badPrice = true;
        }

        if (date.getText().toString().isEmpty() || badPrice || rdBtnID == -1) {
            AlertDialog_.show(this, "ERROR", "Algunos campos estan vacios.");
        } else {
            RadioButton radio = (RadioButton) findViewById(rdBtnID);
            String jsonRequest = buildRequest(sucN, memberN, date.getText().toString(), priceD, radio.getText().toString());
            if (jsonRequest != null) {
                InternetClient client = new InternetClient(getApplicationContext(), view,
                        Consts.FURNITURE_ADD, Consts.FURNITURE, null, Consts.POST, jsonRequest, false);
                client.runInBackground();
            }
        }
    }

    private String buildRequest(int sucN, int memberN, String date, double price, String state) {
        JSONObject jsonO = new JSONObject();
        try {
            jsonO.put(Consts.N_SUC, sucN);
            jsonO.put(Consts.N_MEMBER, memberN);
            jsonO.put(Consts.BUY_DATE, Integer.parseInt(date));
            jsonO.put(Consts.FINAL_PRICE, price);
            jsonO.put(Consts.STATE, Consts.mapStateStringInt(state));
            Calendar calendar = Calendar.getInstance();
            String dateUpdate = Consts.parseDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
            jsonO.put(Consts.LAST_UPDATE, Integer.parseInt(dateUpdate));
            return jsonO.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
