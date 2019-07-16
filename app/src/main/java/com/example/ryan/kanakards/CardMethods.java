package com.example.ryan.kanakards;
import com.example.ryan.kanakards.Kana;

import java.util.Collections;
import java.util.List;

public class CardMethods {
    private Kana masterPool[] = {
            new Kana("あ", "a", "Hiragana"),
            new Kana("い", "i", "Hiragana"),
            new Kana("う", "u", "Hiragana"),
            new Kana("え", "e", "Hiragana"),
            new Kana("お", "o", "Hiragana"),
            new Kana("か", "ka", "Hiragana"),
            new Kana("き", "ki", "Hiragana"),
            new Kana("く", "ku", "Hiragana"),
            new Kana("け", "ke", "Hiragana"),
            new Kana("こ", "ko", "Hiragana"),
            new Kana("さ", "sa", "Hiragana"),
            new Kana("し", "shi", "Hiragana"),
            new Kana("す", "su", "Hiragana"),
            new Kana("せ", "se", "Hiragana"),
            new Kana("そ", "so", "Hiragana"),
            new Kana("た", "ta", "Hiragana"),
            new Kana("ち", "chi", "Hiragana"),
            new Kana("つ", "tsu", "Hiragana"),
            new Kana("て", "te", "Hiragana"),
            new Kana("と", "to", "Hiragana"),
            new Kana("な", "na", "Hiragana"),
            new Kana("に", "ni", "Hiragana"),
            new Kana("ぬ", "nu", "Hiragana"),
            new Kana("ね", "ne", "Hiragana"),
            new Kana("の", "no", "Hiragana"),
            new Kana("は", "ha", "Hiragana"),
            new Kana("ひ", "hi", "Hiragana"),
            new Kana("ふ", "fu", "Hiragana"),
            new Kana("へ", "he", "Hiragana/Katakana"),
            new Kana("ほ", "ho", "Hiragana"),
            new Kana("ま", "ma", "Hiragana"),
            new Kana("み", "mi", "Hiragana"),
            new Kana("む", "mu", "Hiragana"),
            new Kana("め", "me", "Hiragana"),
            new Kana("も", "mo", "Hiragana"),
            new Kana("や", "ya", "Hiragana"),
            new Kana("ゆ", "yu", "Hiragana"),
            new Kana("よ", "yo", "Hiragana"),
            new Kana("ら", "ra", "Hiragana"),
            new Kana("り", "ri", "Hiragana"),
            new Kana("る", "ru", "Hiragana"),
            new Kana("れ", "re", "Hiragana"),
            new Kana("ろ", "ro", "Hiragana"),
            new Kana("わ", "wa", "Hiragana"),
            new Kana("を", "wo", "Hiragana"),
            new Kana("ん", "n", "Hiragana"),
            new Kana("が", "ga", "Hiragana"),
            new Kana("ぎ", "gi", "Hiragana"),
            new Kana("ぐ", "gu", "Hiragana"),
            new Kana("げ", "ge", "Hiragana"),
            new Kana("ご", "go", "Hiragana"),
            new Kana("ざ", "za", "Hiragana"),
            new Kana("じ", "ji (zi)", "Hiragana"),
            new Kana("ず", "zu", "Hiragana"),
            new Kana("ぜ", "ze", "Hiragana"),
            new Kana("ぞ", "zo", "Hiragana"),
            new Kana("だ", "da", "Hiragana"),
            new Kana("ぢ", "ji (di)", "Hiragana"),
            new Kana("づ", "zu (du)", "Hiragana"),
            new Kana("で", "de", "Hiragana"),
            new Kana("ど", "do", "Hiragana"),
            new Kana("ば", "ba", "Hiragana"),
            new Kana("び", "bi", "Hiragana"),
            new Kana("ぶ", "bu", "Hiragana"),
            new Kana("べ", "be", "Hiragana"),
            new Kana("ぼ", "bo", "Hiragana"),
            new Kana("ぱ", "pa", "Hiragana"),
            new Kana("ぴ", "pi", "Hiragana"),
            new Kana("ぷ", "pu", "Hiragana"),
            new Kana("ぺ", "pe", "Hiragana"),
            new Kana("ぽ", "po", "Hiragana"),
            new Kana("ア", "a", "Katakana"),
            new Kana("イ", "i", "Katakana"),
            new Kana("ウ", "u", "Katakana"),
            new Kana("エ", "e", "Katakana"),
            new Kana("オ", "o", "Katakana"),
            new Kana("カ", "ka", "Katakana"),
            new Kana("キ", "ki", "Katakana"),
            new Kana("ク", "ku", "Katakana"),
            new Kana("ケ", "ke", "Katakana"),
            new Kana("コ", "ko", "Katakana"),
            new Kana("サ", "sa", "Katakana"),
            new Kana("シ", "shi", "Katakana"),
            new Kana("ス", "su", "Katakana"),
            new Kana("セ", "se", "Katakana"),
            new Kana("ソ", "so", "Katakana"),
            new Kana("タ", "ta", "Katakana"),
            new Kana("チ", "chi", "Katakana"),
            new Kana("ツ", "tsu", "Katakana"),
            new Kana("テ", "te", "Katakana"),
            new Kana("ト", "to", "Katakana"),
            new Kana("ナ", "na", "Katakana"),
            new Kana("ニ", "ni", "Katakana"),
            new Kana("ヌ", "nu", "Katakana"),
            new Kana("ネ", "ne", "Katakana"),
            new Kana("ノ", "no", "Katakana"),
            new Kana("ハ", "ha", "Katakana"),
            new Kana("ヒ", "hi", "Katakana"),
            new Kana("フ", "fu", "Katakana"),
            new Kana("ホ", "ho", "Katakana"),
            new Kana("マ", "ma", "Katakana"),
            new Kana("ミ", "mi", "Katakana"),
            new Kana("ム", "mu", "Katakana"),
            new Kana("メ", "me", "Katakana"),
            new Kana("モ", "mo", "Katakana"),
            new Kana("ヤ", "ya", "Katakana"),
            new Kana("ユ", "yu", "Katakana"),
            new Kana("ヨ", "yo", "Katakana"),
            new Kana("ラ", "ra", "Katakana"),
            new Kana("リ", "ri", "Katakana"),
            new Kana("ル", "ru", "Katakana"),
            new Kana("レ", "re", "Katakana"),
            new Kana("ロ", "ro", "Katakana"),
            new Kana("ワ", "wa", "Katakana"),
            new Kana("ヲ", "wo", "Katakana"),
            new Kana("ン", "n", "Katakana"),
            new Kana("ガ", "ga", "Katakana"),
            new Kana("ギ", "gi", "Katakana"),
            new Kana("グ", "gu", "Katakana"),
            new Kana("ゲ", "ge", "Katakana"),
            new Kana("ゴ", "go", "Katakana"),
            new Kana("ザ", "za", "Katakana"),
            new Kana("ジ", "ji (zi)", "Katakana"),
            new Kana("ズ", "zu", "Katakana"),
            new Kana("ゼ", "ze", "Katakana"),
            new Kana("ゾ", "zo", "Katakana"),
            new Kana("ダ", "da", "Katakana"),
            new Kana("ヂ", "ji (di)", "Katakana"),
            new Kana("ヅ", "zu (du)", "Katakana"),
            new Kana("デ", "de", "Katakana"),
            new Kana("ド", "do", "Katakana"),
            new Kana("バ", "ba", "Katakana"),
            new Kana("ビ", "bi", "Katakana"),
            new Kana("ブ", "bu", "Katakana"),
            new Kana("ベ", "be", "Katakana"),
            new Kana("ボ", "bo", "Katakana"),
            new Kana("パ", "pa", "Katakana"),
            new Kana("ピ", "pi", "Katakana"),
            new Kana("プ", "pu", "Katakana"),
            new Kana("ペ", "pe", "Katakana"),
            new Kana("ポ", "po", "Katakana")
    };
    private Kana workingPool[] = masterPool;
    private int currentCard = 0;

    public Kana serveCard(){
        if(workingPool[currentCard+1] != null){
            currentCard++;
            return workingPool[currentCard];
        }
        else{
            reset();
            return workingPool[currentCard];
        }
    }

    public void reset(){
        currentCard = 0;
        workingPool = shuffle();
    }

    public Kana[] shuffle(){
        Kana tempPool[] = new Kana[masterPool.length];
        List<Integer> nums = null;
        for(int x = 0; x < masterPool.length; x++){
            nums.add(x);
        }
        Collections.shuffle(nums);
        for(int x = 0; x < masterPool.length; x++){
            tempPool[x] = masterPool[nums.get(x)];
        }
        return tempPool;
    }

    public void removeFromPool(int loc){
        //remove kana Objecet
    }

    public void popToBack(int loc){
        //move current kana object to back
    }

}
