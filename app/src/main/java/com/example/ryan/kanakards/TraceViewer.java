package com.example.ryan.kanakards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TraceViewer extends AppCompatActivity {

    private DrawView drawView;
    private Button clearButt, hideButt, submitButt, nextButt;
    private TextView kanaView;
    private NewCardMethods cardMethods;
    private Characters card;
    private String toLoad;
    private Boolean isHidden = false;
    private CompareBitmaps compareBit; //TODO remove if don't work
    private double similar; //TODO remove if dont work
    private String msg; //TODO remove if dont work

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_trace_viewer);

        Intent intent = getIntent();
        toLoad = intent.getStringExtra("toLoad");

        drawView = findViewById(R.id.drawView);
        clearButt = findViewById(R.id.clearButt);
        hideButt = findViewById(R.id.hideButt);
        submitButt = findViewById(R.id.submitButt);
        kanaView = findViewById(R.id.kanaView);
        nextButt = findViewById(R.id.nextButt);
        cardMethods = new NewCardMethods(getApplicationContext());
        cardMethods.fillPool(toLoad);
        card = cardMethods.serveCard();
        kanaView.setText(card.getSymbol());
        compareBit = new CompareBitmaps();

        drawView.setDrawingCacheEnabled(true);
        drawView.setVisibility(View.VISIBLE);
        drawView.setEnabled(true);
        drawView.invalidate();

        kanaView.setDrawingCacheEnabled(true);

        submitButt.setVisibility(View.GONE);    //TODO remove if I get working

        clearButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawView.clearCanvas();
            }
        });

        hideButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isHidden){
                    kanaView.setVisibility(View.VISIBLE);
                    hideButt.setText("hide");
                    isHidden = false;
                }
                else{
                    kanaView.setVisibility(View.INVISIBLE);
                    hideButt.setText("show");
                    isHidden = true;
                }
            }
        });

        submitButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO add checker of some kind
                kanaView.buildDrawingCache();
                //TODO remove below if don't work
                similar = compareBit.calSimilarity(kanaView.getDrawingCache(), drawView.getBitmap());
                if(similar >= .90)
                    msg = "Very good!";
                else if (similar >= .88)
                    msg = "Pretty good!";
                else if (similar >= .86)
                    msg = "Practice this more";
                else
                    msg = "Hmm.. try again...";
                Toast.makeText(getApplicationContext(), msg + " " + similar, Toast.LENGTH_SHORT).show();
                //TODO remove above if don't work
            }
        });

        nextButt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                drawView.clearCanvas();
                card = cardMethods.serveCard();
                kanaView.setText(card.getSymbol());
                kanaView.setVisibility(View.VISIBLE);
                isHidden = false;
                hideButt.setText("hide");
            }
        });

    }

}
