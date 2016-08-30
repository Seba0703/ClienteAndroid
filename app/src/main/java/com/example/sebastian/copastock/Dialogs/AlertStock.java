package com.example.sebastian.copastock.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.sebastian.copastock.Common.Consts;
import com.example.sebastian.copastock.R;

public class AlertStock {

    public static void show(Activity act, String title, String message, int alertType) {
        AlertDialog alert = new AlertDialog.Builder(act).create();

        Spannable spannable = new SpannableString(message);
        ForegroundColorSpan fcs;
        fcs = new ForegroundColorSpan(Color.WHITE);
        spannable.setSpan(fcs, 0, message.length(),Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new StyleSpan(Typeface.BOLD), 0, message.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        LayoutInflater inflater = act.getLayoutInflater();
        final View view;
        TextView textView;
        if (alertType == Consts.RED) {
            view = inflater.inflate(R.layout.alert_stock_red, null);
            textView = (TextView) view.findViewById(R.id.textView8);
        } else if (alertType == Consts.YELLOW){
            view = inflater.inflate(R.layout.alert_stock_orange, null);
            textView = (TextView) view.findViewById(R.id.textView7);
        } else {
            view = inflater.inflate(R.layout.alert_stock_green, null);
            textView = (TextView) view.findViewById(R.id.textView9);
        }

        textView.setText(spannable);

        alert.setTitle(title);
        alert.setView(view);
        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alert.show();
    }

}
