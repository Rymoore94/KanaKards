package com.example.ryan.kanakards;

public class Kana {
    private String kana;
    private String roma;
    private String classification;

    Kana(){
        kana = "";
        roma = "";
        classification = "";
    }
    Kana(String _kana, String _roma, String _classification){
        kana = _kana;
        roma = _roma;
        classification = _classification;
    }
}
