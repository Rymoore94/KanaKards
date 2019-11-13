package com.example.ryan.kanakards;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewCardMethods {

    private List<Characters> masterPool = new ArrayList<>();
    private List<Characters> workingPool = new ArrayList<>();
    private int currentCard = -1;
    private String contents = "";
    private Context context;

    public NewCardMethods(Context _context){
        context = _context;
    }

    public void fillPool(String _contents){
        contents = _contents;
        if((Objects.equals(contents, "hira")) || (Objects.equals(contents, "kata")) || (Objects.equals(contents, "both")))
            fillDefault(contents);
        else
            fillNonTraditional();
        workingPool = masterPool;
    }

    public Characters serveCard(){
        if(currentCard < workingPool.size()-1){
            currentCard++;
            return (workingPool.get(currentCard));
        }
        else if(checkEmpty()){
            return new Characters("Finished.", "Nothing to see");
            //TODO they completed the lesson
        }
        else{
            currentCard = 0;
            return (workingPool.get(currentCard));
        }
    }

    public void removeCard(){
        workingPool.remove(currentCard);
        currentCard--;
    }

    public void moveCardToBack(){
        Characters temp = workingPool.get(currentCard);
        removeCard();
        workingPool.add(0,temp);
    }

    private boolean checkEmpty(){
        return (workingPool.isEmpty());
    }

    private void fillDefault(String _contents){
        if(_contents.equals("hira"))
            fill("hiragana.kk");
        else if(_contents.equals("kata"))
            fill("katakana.kk");
        else{
            fillDefault("hira");
            fillDefault("kata");
            return;
        }
    }

    private void fillNonTraditional(){
        //TODO read in the filename/path listed in contents
    }

    private void fill(String pathname){
        String symbol = "";
        String roma;
        boolean flipflop = true;
        try {
            InputStream file = context.getAssets().open(pathname);
            BufferedReader read = new BufferedReader(new InputStreamReader(file, "UTF8"));
            String data;
            while((data = read.readLine()) != null){
                if(flipflop == true){
                    symbol = data;
                    flipflop = false;
                }
                else {
                    roma = data;
                    masterPool.add(new Characters(symbol, roma));
                    flipflop = true;
                }
            }
            read.close();
        }
        catch (IOException e){}
    }
}