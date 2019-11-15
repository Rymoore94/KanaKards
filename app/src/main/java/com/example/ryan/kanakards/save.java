package com.example.ryan.kanakards;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class save {

    Context context;

    public save(Context _context){
        context = _context;
    }

    public void save(SettingsPacket data){
        SharedPreferences saveData = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor saver = saveData.edit();
        saver.putBoolean("hira", data.getHira());
        saver.putBoolean("kata", data.getKata());
        saver.putBoolean("custom", data.getCustom());
        saver.putString("fileName", data.getFilename());
        saver.apply();
    }

    public SettingsPacket load(){
        SharedPreferences saveData = PreferenceManager.getDefaultSharedPreferences(context);
        if(!saveData.contains("hira"))
            save(new SettingsPacket(true, true, false, ""));
        return(new SettingsPacket(saveData.getBoolean("hira", true),
                saveData.getBoolean("kata", true),
                saveData.getBoolean("custom", false),
                saveData.getString("fileName", null)));
    }
}
