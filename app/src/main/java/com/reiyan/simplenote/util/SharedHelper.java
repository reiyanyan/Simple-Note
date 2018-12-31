package com.reiyan.simplenote.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedHelper {

    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SharedHelper(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("app_welcome", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setFirsLaunchDone(int value){
        editor.putInt("intro", value);
        editor.apply();
    }

    public int isIntroDone(){
        return preferences.getInt("intro", 0);
    }

}
