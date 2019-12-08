package com.example.ryan.kanakards;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Vector;

class VoiceCheck {
    private String symbol, pronunciation;
    private int index;
    private Context context;
    private String toLoad;
    private Vector<Vector<String>> key = new Vector<>(0);

    VoiceCheck(Context context, String toLoad) {
        this.context = context;
        this.toLoad = toLoad;
        load();
    }

    boolean isRight(String symbol, String pronunciation) {
        this.symbol = symbol;
        this.pronunciation = pronunciation;
        searchSymbol();
        return check();
    }

    private void searchSymbol() {
        for (int x = 0; x < key.size(); x++) {
            if (key.get(x).get(0).equals(symbol)) {
                index = x;
                return;
            }
        }
    }

    private boolean check() {
        for (int x = 0; x < key.get(index).size(); x++)
            if (key.get(index).get(x).equals(pronunciation)) {
                return true;
            }
        return false;
    }

    private void load() {
        if (toLoad.equals("hiraw"))
            loadIn("hirakey.kk");
        else if (toLoad.equals("kataw"))
            loadIn("katakey.kk");
        else {
            toLoad = "hiraw";
            load();
            toLoad = "kataw";
            load();
            toLoad = "both";
        }
    }

    private void loadIn(String path) {
        String data;
        String[] multi;
        try {
            InputStream file = context.getAssets().open(path);
            BufferedReader read = new BufferedReader(new InputStreamReader(file, StandardCharsets.UTF_8));
            while ((data = read.readLine()) != null) {
                multi = data.split(" ");
                Vector<String> temp = new Vector<>(0);
                for (String s : multi) {
                    if (!Objects.equals(s, ""))
                        temp.add(s);
                }
                key.add(temp);
            }
            read.close();
        } catch (IOException e) {
            Toast.makeText(context, "*internal screaming*", Toast.LENGTH_SHORT).show();
        }
    }
}