package com.example.sebastian.copastock.Receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import com.example.sebastian.copastock.ChooseModelActivity;
import com.example.sebastian.copastock.Common.ActivityStarter;
import com.example.sebastian.copastock.Common.Consts;
import com.example.sebastian.copastock.Dialogs.AlertDialog_;
import com.example.sebastian.copastock.Singleton.UserSingleton;

public class LoginReceiver extends BroadcastReceiver {

    private Activity activity;
    private Button login;
    private String userName;

    public LoginReceiver(Activity activity, Button login) {
        this.activity = activity;
        this.login = login;
    }

    @Override
    public void onReceive(Context cxt, Intent intent) {
        login.setEnabled(true);
        if (intent.getBooleanExtra(Consts.SUCESS, false)) {
            UserSingleton.getInstance().setUserName(userName);
            ActivityStarter.startClear(activity, ChooseModelActivity.class);
        } else if (intent.getBooleanExtra(Consts.RESULT, false)) {
            AlertDialog_.show(activity, "ERROR",  "No se pudo conectar con el servidor");
        } else {
            AlertDialog_.show(activity, "ERROR",  "No se puedo ingresar. Asegurese de tener los permisos necesarios");
        }
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
