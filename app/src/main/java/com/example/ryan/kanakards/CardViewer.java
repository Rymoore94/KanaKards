package com.example.ryan.kanakards;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CardViewer extends AppCompatActivity {
    Button flashCard;
    Button correct;
    Button wrong;
    boolean isFront;
    Characters card;
    NewCardMethods cardMethods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_viewer);
        isFront = true;
        flashCard = findViewById(R.id.flashCard);
        correct = findViewById(R.id.correctButt);
        wrong = findViewById(R.id.wrongButt);
        Intent intent = getIntent();
        cardMethods = new NewCardMethods(this);
        String characterSet = intent.getStringExtra("toLoad");
        cardMethods.fillPool(characterSet);
        card = cardMethods.serveCard();
        flashCard.setText(card.getSymbol());
        flashCard.setTextSize(120);

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
                flashCard.setText(card.getSymbol());
            }
        });

        wrong.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                cardMethods.moveCardToBack();
                card = cardMethods.serveCard();
                flashCard.setText(card.getSymbol());
            }
        });
    }
}
