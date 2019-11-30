package com.example.ryan.kanakards;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.speech.tts.TextToSpeech;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class CardViewer extends AppCompatActivity {
    Button flashCard;
    Button correct;
    Button wrong;
    Button sound;
    boolean isFront;
    boolean isCustom;
    Characters card;
    NewCardMethods cardMethods;
    TextToSpeech speech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_card_viewer);
        isFront = true;
        flashCard = findViewById(R.id.flashCard);
        correct = findViewById(R.id.correctButt);
        wrong = findViewById(R.id.wrongButt);
        sound = findViewById(R.id.soundButt);
        Intent intent = getIntent();
        cardMethods = new NewCardMethods(getApplicationContext());
        String characterSet = intent.getStringExtra("toLoad");
        isCustom = intent.getBooleanExtra("isCustom", false);
        cardMethods.fillPool(characterSet);
        card = cardMethods.serveCard();
        flashCard.setText(card.getSymbol());
        flashCard.setTextSize(120);

        if(isCustom)
            sound.setVisibility(View.GONE);

        speech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                speech.setLanguage(Locale.JAPANESE);
            }
        });

        sound.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(card.getSymbol().equals("ん") || card.getSymbol().equals("ン"))
                    speech.speak("んんん", TextToSpeech.QUEUE_FLUSH, null, null);
                else
                    speech.speak(card.getSymbol()+"ー", TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });

        flashCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(isFront){
                    flashCard.setText(card.getRoma());
                    flashCard.setTextSize(60);
                    isFront = false;
                }
                else{
                    flashCard.setText(card.getSymbol());
                    flashCard.setTextSize(120);
                    isFront = true;
                }
            }
        });

        correct.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                cardMethods.removeCard();
                card = cardMethods.serveCard();
                flashCard.setTextSize(120);
                flashCard.setText(card.getSymbol());
                isFront = true;
            }
        });

        wrong.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                cardMethods.moveCardToBack();
                card = cardMethods.serveCard();
                flashCard.setTextSize(120);
                flashCard.setText(card.getSymbol());
                isFront = true;
            }
        });
    }
}
