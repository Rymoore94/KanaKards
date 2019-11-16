package com.example.ryan.kanakards;

public class SettingsPacket {
    private boolean hira;
    private boolean kata;
    private boolean custom;
    private String fileName;

    SettingsPacket(){
        hira = true;
        kata = true;
        custom = false;
        fileName = "";
    }

    SettingsPacket(boolean _hira, boolean _kata, boolean _custom, String _fileName){
        hira = _hira;
        kata = _kata;
        custom = _custom;
        fileName = _fileName;
    }

    public boolean getHira(){
        return hira;
    }

    public boolean getKata(){
        return kata;
    }

    public boolean getCustom(){
        return custom;
    }

    public String getFilename(){
        return fileName;
    }
}
