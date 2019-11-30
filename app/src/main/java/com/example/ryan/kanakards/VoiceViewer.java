package com.example.ryan.kanakards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class VoiceViewer extends AppCompatActivity {

    private final int VOICE_REQUEST_CODE = 10;
    ImageView speakButt;
    Button flashCard;
    Button skipButt;    //TODO Remove this
    NewCardMethods cards;
    Characters current;
    VoiceCheck check;
    Intent intent;
    String toLoad;
    boolean isFront;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_viewer);

        intent = getIntent();
        toLoad = intent.getStringExtra("toLoad") + "w";         //the w is the "words" modifier

        speakButt = findViewById(R.id.speakButt);
        flashCard = findViewById(R.id.flashCard);
        skipButt = findViewById(R.id.skipButt);     //TODO Remove this
        isFront = true;

        cards = new NewCardMethods((getApplicationContext()));
        cards.fillPool(toLoad);
        current = cards.serveCard();
        check = new VoiceCheck(getApplicationContext(), toLoad);
        flashCard.setText(current.getSymbol());
        flashCard.setTextSize(120);

        flashCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(isFront){
                    flashCard.setText(current.getRoma());
                    flashCard.setTextSize(60);
                    isFront = false;
                }
                else{
                    flashCard.setText(current.getSymbol());
                    flashCard.setTextSize(120);
                    isFront = true;
                }
            }
        });
        //TODO Remove below this line
        skipButt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                cards.removeCard();
                current = cards.serveCard();
                flashCard.setTextSize(120);
                flashCard.setText(current.getSymbol());
                isFront = true;
            }
        });
        //TODO Remove above this line
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
            if(check.isRight(current.getSymbol(), output.get(0))){                                      //TODO make apparent that it was correct
                cards.removeCard();
                current = cards.serveCard();
                flashCard.setTextSize(120);
                flashCard.setText(current.getSymbol());
                isFront = true;
                Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
            }
            else{                                                                                       //TODO make apparent that it was incorrect
                Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                Log.d("words", current.getSymbol()+" "+output.get(0));  //prints the missed pronunciation in Log
            }
        }
    }
}
