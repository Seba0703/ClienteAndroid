package com.example.sebastian.copastock.Common;

import android.os.Handler;
import android.widget.Button;

public class DisablerButton {
    public static void disable(final Button button, int time) {
        button.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                button.setEnabled(true);
            }
        }, time);
    }
}
