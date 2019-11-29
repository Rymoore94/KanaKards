package com.example.ryan.kanakards;

import java.util.ArrayList;
import java.util.List;

public class VoiceCheck {
    private String symbol, pronunciation;
    private int index;
    private boolean correct;
    private List<Characters> key = new ArrayList<>(0);

    VoiceCheck(){   //TODO load only ones that are needed
        key.add(new Characters("あお", "青"));     key.add(new Characters("あか", "赤"));
        key.add(new Characters("おに", "鬼"));     key.add(new Characters("なな", "7"));
        key.add(new Characters("なつ", "夏"));     key.add(new Characters("はは", "母"));
        key.add(new Characters("はい", "はい"));   key.add(new Characters("ちち", "父"));
        key.add(new Characters("いえ", "家"));     key.add(new Characters("うえ", "上"));
        key.add(new Characters("よし", "よし"));    key.add(new Characters("この", "この"));
        key.add(new Characters("これ", "これ"));    key.add(new Characters("とら", "虎"));
        key.add(new Characters("たろ", "タロ"));    key.add(new Characters("しろ", "白"));
        key.add(new Characters("じん", "神"));      key.add(new Characters("にく", "肉"));
        key.add(new Characters("のむ", "飲む"));    key.add(new Characters("です", "です"));
        key.add(new Characters("みず", "水"));      key.add(new Characters("いま", "今"));
        key.add(new Characters("そか", "そっか"));   key.add(new Characters("さけ", "酒"));
        key.add(new Characters("ねこ", "猫"));      key.add(new Characters("きみ", "木目"));
        key.add(new Characters("とり", "鳥"));      key.add(new Characters("せん", "1000"));
        key.add(new Characters("いぬ", "犬"));      key.add(new Characters("ふゆ", "そうよ"));
        key.add(new Characters("ほん", "本"));      key.add(new Characters("ひと", "人"));
        key.add(new Characters("ひざ", "膝"));      key.add(new Characters("おば", "おば"));
        key.add(new Characters("ばか", "バカ"));    key.add(new Characters("かぜ", "風邪"));
        key.add(new Characters("ぼく", "北"));      key.add(new Characters("ちず", "地図"));
        key.add(new Characters("がま", "釜"));      key.add(new Characters("だめ", "ダメ"));
        key.add(new Characters("ども", "どうも"));   key.add(new Characters("みぎ", "右"));
        key.add(new Characters("どぞ", "どうぞ"));   key.add(new Characters("わたし", "私"));
        key.add(new Characters("ごはん", "ご飯"));   key.add(new Characters("げんき", "元気"));
        key.add(new Characters("たべる", "食べる")); key.add(new Characters("てくび", "手首"));
        key.add(new Characters("へいし", "兵士"));   key.add(new Characters("まぐろ", "マグロ"));
        key.add(new Characters("あそぶ", "遊ぶ"));   key.add(new Characters("やめろ", "やめろ"));
    }

    public boolean isRight(String symbol, String pronunciation){
        this.symbol = symbol;
        this.pronunciation = pronunciation;
        searchSymbol();
        check();
        return correct;
    }

    private void searchSymbol(){
        for(int x = 0; x < key.size(); x++){
            if(key.get(x).getSymbol().equals(symbol)){
                index = x;
                return;
            }
        }
    }

    private void check(){
        if(key.get(index).getRoma().equals(pronunciation))
            correct = true;
        else
            correct = false;
    }
}
