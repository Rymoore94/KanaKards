package com.example.ryan.kanakards;

public class SettingsPacket {
    private boolean hira;
    private boolean kata;
    private boolean custom;
    private String fileName;
    private String customNames;

    SettingsPacket(){
        hira = true;
        kata = true;
        custom = false;
        fileName = "";
    }

    SettingsPacket(boolean _hira, boolean _kata, boolean _custom, String _fileName, String _customNames){
        hira = _hira;
        kata = _kata;
        custom = _custom;
        fileName = _fileName;
        customNames = _customNames;
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

    public  String getCustomNames(){
        return customNames;
    }
}
