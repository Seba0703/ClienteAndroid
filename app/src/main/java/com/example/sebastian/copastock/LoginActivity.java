package com.example.sebastian.copastock;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.sebastian.copastock.Common.ActivityStarter;
import com.example.sebastian.copastock.Common.Consts;
import com.example.sebastian.copastock.Common.DisablerButton;
import com.example.sebastian.copastock.Dialogs.AlertDialog_;
import com.example.sebastian.copastock.InternetTools.InternetClient;
import com.example.sebastian.copastock.Receivers.LoginReceiver;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText userEdit;
    private EditText passEdit;
    private View view;
    private LoginReceiver onLogin;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        view = findViewById(R.id.relativeLogin);

        login = (Button) findViewById(R.id.button);

        onLogin = new LoginReceiver(this, login);

        userEdit = (EditText) findViewById(R.id.editText);
        passEdit = (EditText) findViewById(R.id.editText2);
        CheckBox check = (CheckBox) findViewById(R.id.checkBox);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    passEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    passEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }

    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(onLogin,
                new IntentFilter(Consts.LOGIN_call));
    }

    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(onLogin);
    }

    public void login(View v) {
        login.setEnabled(false);
        String userText = userEdit.getText().toString().toUpperCase().replaceAll("\\s+$", "");
        String passText = passEdit.getText().toString();

        if (!userText.isEmpty() && !passText.isEmpty()) {
            Map<String, String> headers = new HashMap<>();
            headers.put(Consts.USER, userText);
            headers.put(Consts.PASS, passText);
            onLogin.setUserName(userText);
            InternetClient client = new InternetClient(getApplicationContext(), view, Consts.LOGIN_call,
                    Consts.LOGIN, headers, Consts.GET, null, false);
            client.runInBackground();
        } else {
            AlertDialog_.show(this, "ERROR", "Ingrese usuario y contraseña por favor.");
        }
    }

    public void goConfig(View view) {
        Button goConfig = (Button) findViewById(R.id.button4);
        DisablerButton.disable(goConfig, 250);
        ActivityStarter.start(getApplicationContext(), ConfigActivity.class);
    }
}
