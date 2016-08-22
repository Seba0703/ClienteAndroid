package com.example.sebastian.copastock.Singleton;

/**
 * Created by Sebastian on 15/08/2016.
 */
public class UserSingleton {

    private static UserSingleton instance = null;
    private String userName = null;

    protected  UserSingleton(){}

    public static UserSingleton getInstance() {
        if (instance == null) {
            instance = new UserSingleton();
        }
        return instance;
    }

    public void setUserName(String user) {
        if (userName == null) {
            userName = user;
        }
    }

    public String getUserName() {
        return userName;
    }


}
