package com.example.ryan.kanakards;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class InspectCharacter extends AppCompatActivity {
    private String symbol;
    private TextToSpeech speech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_inspect_character);

        speech = new TextToSpeech(getApplicationContext(), i -> speech.setLanguage(Locale.JAPANESE));

        Intent intent = getIntent();
        symbol = intent.getStringExtra("symbol");
        String roma = intent.getStringExtra("roma");
        boolean isCustom = intent.getBooleanExtra("isCustom", false);

        TextView symbolView = findViewById(R.id.symbolView);
        TextView romaView = findViewById(R.id.romaView);
        Button listenButt = findViewById(R.id.listenButt);

        if (isCustom)
            listenButt.setVisibility(View.GONE);

        symbolView.setText(symbol);
        romaView.setText(roma);

        listenButt.setOnClickListener(view -> {
            if (symbol.equals("ん") || symbol.equals("ン"))
                speech.speak("んんん", TextToSpeech.QUEUE_FLUSH, null, null);
            else
                speech.speak(symbol + "ー", TextToSpeech.QUEUE_FLUSH, null, null);
        });
    }
}
