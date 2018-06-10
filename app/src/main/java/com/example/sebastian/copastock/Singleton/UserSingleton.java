package com.example.sebastian.copastock.Singleton;


public class UserSingleton {

    private static UserSingleton instance = null;
    private String userName = null;
    private String url = null;


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

    public String getUrl() {
        return url;
    }

    public void setUrl(String newUrl) {
        this.url = newUrl;
    }


}
