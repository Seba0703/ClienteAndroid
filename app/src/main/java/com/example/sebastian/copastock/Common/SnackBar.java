package com.example.sebastian.copastock.Common;

import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackBar {

    public static void show(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

}
