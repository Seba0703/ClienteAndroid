package com.example.sebastian.copastock.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.sebastian.copastock.Common.Consts;

import java.util.Calendar;

public class DatePickerDialog extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {


    private Activity activity;
    private TextView date;
    private int day;
    private int month;
    private int year;

    public void attach(Activity activity) {
        this.activity = activity;
    }

    public Dialog onCreateDialog(Bundle args) {
        final Calendar c = Calendar.getInstance();

        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);

        return new android.app.DatePickerDialog(activity, this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
        if (y <= year && m <= month && d <= day) {
            date.setText(Consts.parseDate(y, m + 1, d));
        } else {
            date.setText("");
        }
    }

    public void setDate(TextView date) {
        this.date = date;
    }
}
