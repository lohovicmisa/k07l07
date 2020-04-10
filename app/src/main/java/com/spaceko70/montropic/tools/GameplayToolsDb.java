package com.spaceko70.montropic.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class GameplayToolsDb {
    private static String ship = "k07ship";
    private SharedPreferences preferences;

    public GameplayToolsDb(Context context){
        String NAME = "k07ship";
        preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public void setDbDataTool(String data){
        preferences.edit().putString(GameplayToolsDb.ship, data).apply();
    }

    public String getDbDataTool(){
        return preferences.getString(ship, "");
    }
}
