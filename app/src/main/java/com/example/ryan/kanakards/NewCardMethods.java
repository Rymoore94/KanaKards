package com.example.ryan.kanakards;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

class NewCardMethods {

    private List<Characters> masterPool = new ArrayList<>();
    private List<Characters> workingPool = new ArrayList<>();
    private int currentCard = -1;
    private String contents = "";
    private Context context;

    NewCardMethods(Context _context) {
        context = _context;
    }

    void fillPool(String _contents) {
        contents = _contents;
        if ((Objects.equals(contents, "hira")) || (Objects.equals(contents, "kata")) || (Objects.equals(contents, "both")))
            fillDefault(contents);
        else if ((Objects.equals(contents, "hiraw")) || (Objects.equals(contents, "kataw")) || (Objects.equals(contents, "bothw")))
            fillWords(contents);
        else
            fillNonTraditional();
        refillPool();
    }

    List<Characters> getPool() {
        return masterPool;
    }

    Characters serveCard() {
        if (currentCard < workingPool.size() - 1) {
            currentCard++;
            return (workingPool.get(currentCard));
        } else if (checkEmpty()) {
            shufflePool();
            currentCard = 0;
            Toast.makeText(context, "Lesson Complete! Shuffling...", Toast.LENGTH_SHORT).show();
            return (workingPool.get(currentCard));
        } else {
            currentCard = 0;
            return (workingPool.get(currentCard));
        }
    }

    void removeCard() {
        workingPool.remove(currentCard);
        currentCard--;
    }

    void moveCardToBack() {
        Characters temp = workingPool.get(currentCard);
        removeCard();
        currentCard++;
        workingPool.add(0, temp);
    }

    private void refillPool() {
        workingPool.addAll(masterPool);
    }

    private boolean checkEmpty() {
        return (workingPool.isEmpty());
    }

    private void shufflePool() {
        refillPool();
        Collections.shuffle(workingPool);
    }

    private void fillWords(String _contents) {
        if (_contents.equals("hiraw"))
            fill("hiraganawords.kk");
        else if (_contents.equals("kataw"))
            fill("katakanawords.kk");
        else {
            fillWords("hiraw");
            fillWords("kataw");
        }
    }

    private void fillDefault(String _contents) {
        if (_contents.equals("hira"))
            fill("hiragana.kk");
        else if (_contents.equals("kata"))
            fill("katakana.kk");
        else {
            fillDefault("hira");
            fillDefault("kata");
        }
    }

    private void fillNonTraditional() {
        SharedPreferences saveData = PreferenceManager.getDefaultSharedPreferences(context);
        String[] temp = Objects.requireNonNull(saveData.getString(contents, null)).split(" ");
        for (int x = 1; x < temp.length; x += 2) {
            masterPool.add(new Characters(temp[x], temp[x + 1]));
        }
    }

    private void fill(String pathname) {
        String symbol = "";
        String roma;
        boolean flipflop = true;
        try {
            InputStream file = context.getAssets().open(pathname);
            BufferedReader read = new BufferedReader(new InputStreamReader(file, StandardCharsets.UTF_8));
            String data;
            while ((data = read.readLine()) != null) {
                if (flipflop) {
                    symbol = data;
                    flipflop = false;
                } else {
                    roma = data;
                    masterPool.add(new Characters(symbol, roma));
                    flipflop = true;
                }
            }
            read.close();
        } catch (IOException e) {
            Toast.makeText(context, "*internal screaming*", Toast.LENGTH_SHORT).show();
        }
    }
}