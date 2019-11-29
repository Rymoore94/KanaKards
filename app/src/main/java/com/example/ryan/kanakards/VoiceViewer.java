package com.example.ryan.kanakards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class VoiceViewer extends AppCompatActivity {

    private final int VOICE_REQUEST_CODE = 10;
    ImageView speakButt;
    TextView outputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_viewer);
        //TODO something needs to go here
        Toast.makeText(getApplicationContext(), "You made it to the Voice Section!", Toast.LENGTH_SHORT).show();

        speakButt = findViewById(R.id.speakButt);
        outputText = findViewById(R.id.outputText);

        speakButt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ja_JP");
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak");

                startActivityForResult(intent, VOICE_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int request, int result, Intent data){
        super.onActivityResult(request, result, data);
        if(request == VOICE_REQUEST_CODE && data != null){
            ArrayList<String> output = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            outputText.setText(output.get(0));
        }
    }
}
