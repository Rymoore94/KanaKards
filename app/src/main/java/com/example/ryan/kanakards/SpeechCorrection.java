package com.example.ryan.kanakards;

public class SpeechCorrection {
    String []pairings = new String[141];

    SpeechCorrection(){
        pairings[0] = "ああ";     pairings[1] = "いい";     pairings[2] = "うう";
        pairings[3] = "ええ";     pairings[4] = "おお";     pairings[5] = "かあ";
        pairings[6] = "きい";     pairings[7] = "くう";     pairings[8] = "けえ";
        pairings[9] = "こお";     pairings[10] = "さあ";    pairings[11] = "しい";
        pairings[12] = "すう";    pairings[13] = "せえ";    pairings[14] = "そお";
        pairings[15] = "たあ";    pairings[16] = "ちい";    pairings[17] = "つう";
        pairings[18] = "てえ";    pairings[19] = "とお";    pairings[20] = "なあ";
        pairings[21] = "にい";    pairings[22] = "ぬう";    pairings[23] = "ねえ";
        pairings[24] = "のお";    pairings[25] = "はあ";    pairings[26] = "ひい";
        pairings[27] = "ふう";    pairings[28] = "へえ";    pairings[29] = "ほお";
        pairings[30] = "まあ";    pairings[31] = "みい";    pairings[32] = "むう";
        pairings[33] = "めえ";    pairings[34] = "もお";    pairings[35] = "やあ";
        pairings[36] = "ゆう";    pairings[37] = "よお";    pairings[38] = "らあ";
        pairings[39] = "りい";    pairings[40] = "るう";    pairings[41] = "れえ";
        pairings[42] = "ろお";    pairings[43] = "わあ";    pairings[44] = "をお";
        pairings[45] = "んん";    pairings[46] = "があ";    pairings[47] = "ぎい";
        pairings[48] = "ぐう";    pairings[49] = "げえ";    pairings[50] = "ごお";
        pairings[51] = "ざあ";    pairings[52] = "じい";    pairings[53] = "ずう";
        pairings[54] = "ぜえ";    pairings[55] = "ぞお";    pairings[56] = "だあ";
        pairings[57] = "ぢい";    pairings[58] = "づう";    pairings[59] = "でえ";
        pairings[60] = "どお";    pairings[61] = "ばあ";    pairings[62] = "びい";
        pairings[63] = "ぶう";    pairings[64] = "べえ";    pairings[65] = "ぼお";
        pairings[66] = "ぱあ";    pairings[67] = "ぴい";    pairings[68] = "ぷう";
        pairings[69] = "ぺえ";    pairings[70] = "ぽお";    pairings[71] = "アア";
        pairings[72] = "イイ";    pairings[73] = "ウウ";    pairings[74] = "エエ";
        pairings[75] = "オオ";    pairings[76] = "カア";    pairings[77] = "キイ";
        pairings[78] = "クウ";    pairings[79] = "ケエ";    pairings[80] = "コオ";
        pairings[81] = "サア";    pairings[82] = "シイ";    pairings[83] = "スウ";
        pairings[84] = "セエ";    pairings[85] = "ソオ";    pairings[86] = "タア";
        pairings[87] = "チイ";    pairings[88] = "ツウ";    pairings[89] = "テエ";
        pairings[90] = "トオ";    pairings[91] = "ナア";    pairings[92] = "ニイ";
        pairings[93] = "ヌウ";    pairings[94] = "ネエ";    pairings[95] = "ノオ";
        pairings[96] = "ハア";    pairings[97] = "ヒイ";    pairings[98] = "フウ";
        pairings[99] = "ホオ";    pairings[100] = "マア";   pairings[101] = "ミイ";
        pairings[102] = "ムウ";   pairings[103] = "メエ";   pairings[104] = "モオ";
        pairings[105] = "ヤア";   pairings[106] = "ユウ";   pairings[107] = "ヨオ";
        pairings[108] = "ラア";   pairings[109] = "リイ";   pairings[110] = "ルウ";
        pairings[111] = "レエ";   pairings[112] = "ロオ";   pairings[113] = "ワア";
        pairings[114] = "ヲオ";   pairings[115] = "ンン";   pairings[116] = "ガア";
        pairings[117] = "ギイ";   pairings[118] = "グウ";   pairings[119] = "ゲエ";
        pairings[120] = "ゴオ";   pairings[121] = "ザア";   pairings[122] = "ジイ";
        pairings[123] = "ズウ";   pairings[124] = "ゼエ";   pairings[125] = "ゾオ";
        pairings[126] = "ダア";   pairings[127] = "ヂイ";   pairings[128] = "ヅウ";
        pairings[129] = "デエ";   pairings[130] = "ドオ";   pairings[131] = "バア";
        pairings[132] = "ビイ";   pairings[133] = "ブウ";   pairings[134] = "ベエ";
        pairings[135] = "ボオ";   pairings[136] = "パア";   pairings[137] = "ピイ";
        pairings[138] = "プウ";   pairings[139] = "ペエ";   pairings[140] = "ポオ";
    }

    public String correction(String original){
        for(int x = 0; x < pairings.length; x++){
            if(pairings[x].contains(original))
                return pairings[x];
        }
        return "error";
    }
}
