package com.example.ryan.kanakards;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Vector;

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
        saver.putString("customNames", data.getCustomNames());
        saver.apply();
    }

    public void saveCustom(Vector<String> contents, String fileName){
        SharedPreferences saveData = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor saver = saveData.edit();
        saver.putString(fileName, parseVector(contents));
        String temp = saveData.getString("customNames", "");
        saver.putString("customNames", temp + " " + fileName);
        saver.apply();
    }

    private String parseVector(Vector<String> contents){
        String contentString = "";
        for(int x = 0; x < contents.size(); x++)
            contentString += " " + contents.get(x);
        return contentString;
    }

    public SettingsPacket load(){
        SharedPreferences saveData = PreferenceManager.getDefaultSharedPreferences(context);
        if(!saveData.contains("hira"))
            save(new SettingsPacket(true, true, false, "", ""));
        return(new SettingsPacket(saveData.getBoolean("hira", true),
                saveData.getBoolean("kata", true),
                saveData.getBoolean("custom", false),
                saveData.getString("fileName", ""),
                saveData.getString("customNames", "")));
    }

    public Vector<String> loadCustom(String fileName){
        String contentString;
        SharedPreferences saveData = PreferenceManager.getDefaultSharedPreferences(context);
        contentString = saveData.getString(fileName, "");

        return parseString(contentString);
    }

    private Vector<String> parseString(String contentString){
        Vector<String> contents = new Vector<>(0);
        String[] contentArray = contentString.split(" ");
        for(int x = 0; x < contentArray.length; x++)
            contents.add(contentArray[x]);

        return contents;
    }

    public boolean doesExist(String fileName){
        SharedPreferences saveData = PreferenceManager.getDefaultSharedPreferences(context);
        return saveData.contains(fileName);
    }
}
