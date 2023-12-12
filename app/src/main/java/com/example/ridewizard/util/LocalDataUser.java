package com.example.ridewizard.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class LocalDataUser {
    SharedPreferences sharedPreferences;
    private static LocalDataUser instance;
    private static final String PREFERENCES_NAME = "user";
    private static final String TOKEN = "accessToken";
    private static final String USER_ID = "userId";
    private static final String USER_NAME = "userName";
    private static final String CURRENT_TYPE = "type";

    private LocalDataUser(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME,Context.MODE_PRIVATE);
    }
    public static LocalDataUser getInstance(Context context){
        if(instance == null){
            instance = new LocalDataUser(context);
        }
        return instance;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
    public SharedPreferences.Editor getEditor(){
        return sharedPreferences.edit();
    }
    public String getToken(){
        return sharedPreferences.getString(TOKEN,"");
    }
    public int getUserId(){
        return sharedPreferences.getInt(USER_ID,-1);
    }
    public String getUserName(){
        return sharedPreferences.getString(USER_NAME,"");
    }
    public void setCurrentType(boolean isDriver){
        sharedPreferences.edit().putBoolean(CURRENT_TYPE,isDriver).apply();
    }
    public boolean isDriverType(){
        return sharedPreferences.getBoolean(CURRENT_TYPE,false);
    }

}
