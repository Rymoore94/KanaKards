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
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.util.Log;
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

        drawView.setDrawingCacheEnabled(true);
        drawView.setVisibility(View.VISIBLE);
        drawView.setEnabled(true);
        drawView.invalidate();

        kanaView.setDrawingCacheEnabled(true);

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
                kanaView.buildDrawingCache();

                double similarity = gradeKana(kanaView.getDrawingCache(), drawView.getBitmap());

                Toast.makeText(getApplicationContext(), ""+similarity, Toast.LENGTH_SHORT).show();
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
                kanaView.buildDrawingCache();
            }
        });

    }

    private double gradeKana(Bitmap b1, Bitmap b2){     //b1 is Original, b2 is drawing
        b1 = makeMonochrome(b1);
        b1 = compress(b1);
        b2 = compress(b2);
        return compareBitmaps(b1, b2);
    }

    private Bitmap makeMonochrome(Bitmap bitmap){
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        return newBitmap;
    }

    private Bitmap compress(Bitmap bitmap){
        return ThumbnailUtils.extractThumbnail(bitmap, bitmap.getWidth()/4, bitmap.getHeight()/4);
    }

    private double compareBitmaps(Bitmap bitmap1, Bitmap bitmap2){
        int pixel1, pixel2, r1, r2, g1, g2, b1, b2;
        int match = 0;
        int width = bitmap2.getWidth();
        int height = bitmap2.getHeight();

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                pixel2 = bitmap2.getPixel(x, y);
                r2 = Color.red(pixel2);
                g2 = Color.green(pixel2);
                b2 = Color.blue(pixel2);
                pixel1 = bitmap1.getPixel(x, y);
                r1 = Color.red(pixel1);
                g1 = Color.green(pixel1);
                b1 = Color.blue(pixel1);
                if(r2 == r1 && g2 == g1 && b2 == b1)
                    match++;
            }
        }
        double matchD = match;
        double volume = width*height;
        return matchD/volume;
    }
}
