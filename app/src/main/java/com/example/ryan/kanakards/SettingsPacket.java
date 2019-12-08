package com.example.ryan.kanakards;

public class SettingsPacket {
    private boolean hira;
    private boolean kata;
    private boolean custom;
    private boolean trace;
    private boolean voice;
    private String fileName;
    private String customNames;

    SettingsPacket() {
        hira = true;
        kata = true;
        custom = false;
        fileName = "";
    }

    SettingsPacket(boolean _hira, boolean _kata, boolean _custom, boolean _trace, boolean _voice, String _fileName, String _customNames) {
        hira = _hira;
        kata = _kata;
        custom = _custom;
        trace = _trace;
        voice = _voice;
        fileName = _fileName;
        customNames = _customNames;
    }

    public boolean getHira() {
        return hira;
    }

    public boolean getKata() {
        return kata;
    }

    public boolean getCustom() {
        return custom;
    }

    public boolean getTrace() {
        return trace;
    }

    public boolean getVoice() {
        return voice;
    }

    public String getFilename() {
        return fileName;
    }

    public String getCustomNames() {
        return customNames;
    }
}