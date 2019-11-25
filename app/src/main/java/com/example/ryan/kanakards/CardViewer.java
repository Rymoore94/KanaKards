package com.example.ryan.kanakards;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
    SpeechCorrection pronounce;
    TextToSpeech speech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_viewer);
        isFront = true;
        flashCard = findViewById(R.id.flashCard);
        correct = findViewById(R.id.correctButt);
        wrong = findViewById(R.id.wrongButt);
        sound = findViewById(R.id.soundButt);
        Intent intent = getIntent();
        cardMethods = new NewCardMethods(getApplicationContext());
        pronounce = new SpeechCorrection();
        String characterSet = intent.getStringExtra("toLoad");
        isCustom = intent.getBooleanExtra("isCustom", false);
        cardMethods.fillPool(characterSet);
        card = cardMethods.serveCard();
        flashCard.setText(card.getSymbol());
        flashCard.setTextSize(120);

        if(isCustom == true)
            sound.setVisibility(View.GONE);

        speech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                speech.setLanguage(Locale.JAPANESE);
                Toast.makeText(CardViewer.this, "success", Toast.LENGTH_SHORT).show();
            }
        });

        sound.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                speech.speak(pronounce.correction(card.getSymbol()), TextToSpeech.QUEUE_FLUSH, null, null);
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
