package com.example.ryan.kanakards;

public class Characters {
    private String symbol;
    private String roma;

    Characters(){
        symbol = "";
        roma = "";
    }
    Characters(String _symbol, String _roma){
        symbol = _symbol;
        roma = _roma;
    }

    public String getSymbol(){
        return symbol;
    }

    public String getRoma() {
        return roma;
    }
}
