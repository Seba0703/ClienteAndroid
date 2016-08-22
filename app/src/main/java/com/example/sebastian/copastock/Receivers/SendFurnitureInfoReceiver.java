package com.example.sebastian.copastock.Receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SendFurnitureInfoReceiver extends BroadcastReceiver {


    private Activity activity;

    public SendFurnitureInfoReceiver(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        activity.setResult(Activity.RESULT_OK, intent);
        activity.finish();
    }
}
