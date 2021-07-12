package com.example.ryan.kanakards;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class CardViewer extends AppCompatActivity {
    private Button flashCard;
    private boolean isFront;
    private Characters card;
    private NewCardMethods cardMethods;
    private TextToSpeech speech;

    /*Todo:
    *Return to a workable state
	    -Get sound working again
	    -Get voice working again

    *Add checks for if sound and voice packages are installed

    *Make notification usage not annoying

    *Have UI adjust dynamically
	    -make sure tracing works

    *Clean the code
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_card_viewer);
        isFront = true;
        flashCard = findViewById(R.id.flashCard);
        Button correct = findViewById(R.id.correctButt);
        Button wrong = findViewById(R.id.wrongButt);
        Button sound = findViewById(R.id.soundButt);
        Intent intent = getIntent();
        cardMethods = new NewCardMethods(getApplicationContext());
        String characterSet = intent.getStringExtra("toLoad");
        boolean isCustom = intent.getBooleanExtra("isCustom", false);
        cardMethods.fillPool(characterSet);
        card = cardMethods.serveCard();
        flashCard.setText(card.getSymbol());
        flashCard.setTextSize(120);

        if (isCustom)
            sound.setVisibility(View.GONE);

        speech = new TextToSpeech(getApplicationContext(), i -> speech.setLanguage(Locale.JAPANESE));

        sound.setOnClickListener(view -> {
            if (card.getSymbol().equals("ん") || card.getSymbol().equals("ン"))
                speech.speak("んんん", TextToSpeech.QUEUE_FLUSH, null, null);
            else
                speech.speak(card.getSymbol() + "ー", TextToSpeech.QUEUE_FLUSH, null, null);
        });

        flashCard.setOnClickListener(view -> {
            if (isFront) {
                flashCard.setText(card.getRoma());
                flashCard.setTextSize(60);
                isFront = false;
            } else {
                flashCard.setText(card.getSymbol());
                flashCard.setTextSize(120);
                isFront = true;
            }
        });

        correct.setOnClickListener(view -> {
            cardMethods.removeCard();
            card = cardMethods.serveCard();
            flashCard.setTextSize(120);
            flashCard.setText(card.getSymbol());
            isFront = true;
        });

        wrong.setOnClickListener(view -> {
            cardMethods.moveCardToBack();
            card = cardMethods.serveCard();
            flashCard.setTextSize(120);
            flashCard.setText(card.getSymbol());
            isFront = true;
        });
    }
}
