package com.example.sebastian.copastock.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;

import com.example.sebastian.copastock.Common.Consts;
import com.example.sebastian.copastock.Singleton.UserSingleton;

public class CopaStock extends Application {

    private String url = null;

    public String getUrl() {
        if (url == null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            this.url = sharedPreferences.getString(Consts.URL, "");
        }
        return url;
    }

    public void setUrl(String newUrl) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Consts.URL, newUrl);
        editor.apply();
        this.url = newUrl;
        UserSingleton.getInstance().setUrl(url);
    }


}


