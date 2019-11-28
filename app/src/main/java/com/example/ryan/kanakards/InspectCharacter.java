package com.example.ryan.kanakards;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.speech.tts.TextToSpeech;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class InspectCharacter extends AppCompatActivity {
    String symbol, roma;
    boolean isCustom;
    TextView symbolView, romaView;
    Button listenButt;
    SpeechCorrection pronounce;
    TextToSpeech speech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_inspect_character);
        pronounce = new SpeechCorrection();

        speech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                speech.setLanguage(Locale.JAPANESE);
            }
        });

        Intent intent = getIntent();
        symbol = intent.getStringExtra("symbol");
        roma = intent.getStringExtra("roma");
        isCustom = intent.getBooleanExtra("isCustom", false);

        symbolView = findViewById(R.id.symbolView);
        romaView = findViewById(R.id.romaView);
        listenButt = findViewById(R.id.listenButt);

        if(isCustom)
            listenButt.setVisibility(View.GONE);

        symbolView.setText(symbol);
        romaView.setText(roma);

        listenButt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                speech.speak(pronounce.correction(symbol), TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });
    }
}
