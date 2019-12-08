package com.example.ryan.kanakards;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Vector;

public class Save {

    private Context context;

    public Save(Context _context) {
        context = _context;
    }

    public void save(SettingsPacket data) {
        SharedPreferences saveData = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor saver = saveData.edit();
        saver.putBoolean("hira", data.getHira());
        saver.putBoolean("kata", data.getKata());
        saver.putBoolean("custom", data.getCustom());
        saver.putBoolean("trace", data.getTrace());
        saver.putBoolean("voice", data.getVoice());
        saver.putString("fileName", data.getFilename());
        saver.putString("customNames", data.getCustomNames());
        saver.apply();
    }

    void saveCustom(Vector<String> contents, String fileName) {
        SharedPreferences saveData = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor saver = saveData.edit();
        saver.putString(fileName, parseVector(contents));
        String temp = saveData.getString("customNames", "");
        saver.putString("customNames", temp + " " + fileName);
        saver.apply();
    }

    private String parseVector(Vector<String> contents) {
        StringBuilder contentString = new StringBuilder();
        for (int x = 0; x < contents.size(); x++)
            contentString.append(" ").append(contents.get(x));
        return contentString.toString();
    }

    public SettingsPacket load() {
        SharedPreferences saveData = PreferenceManager.getDefaultSharedPreferences(context);
        if (!saveData.contains("hira"))
            save(new SettingsPacket(true, true, false, false, false, "", ""));
        return (new SettingsPacket(saveData.getBoolean("hira", true),
                saveData.getBoolean("kata", true),
                saveData.getBoolean("custom", false),
                saveData.getBoolean("trace", false),
                saveData.getBoolean("voice", false),
                saveData.getString("fileName", ""),
                saveData.getString("customNames", "")));
    }

    void reset() {
        SharedPreferences saveData = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor saver = saveData.edit();
        saver.clear();
        saver.apply();
    }

    boolean doesExist(String fileName) {
        SharedPreferences saveData = PreferenceManager.getDefaultSharedPreferences(context);
        return saveData.contains(fileName);
    }
}
