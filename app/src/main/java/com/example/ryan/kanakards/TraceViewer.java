package com.example.ryan.kanakards;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TraceViewer extends AppCompatActivity {

    private DrawView drawView;
    private Button hideButt;
    private Button minButt;
    private Button addButt;
    private TextView kanaView;
    private TextView accuView;
    private NewCardMethods cardMethods;
    private Characters card;
    private Boolean isHidden = false;
    private double tolerance = 0.9400000000000001;  //Gotta be specific or else the doubles truncate weirdly for the percentage display

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_trace_viewer);

        Intent intent = getIntent();
        String toLoad = intent.getStringExtra("toLoad");

        drawView = findViewById(R.id.drawView);
        accuView = findViewById(R.id.accuView);
        Button clearButt = findViewById(R.id.clearButt);
        hideButt = findViewById(R.id.hideButt);
        minButt = findViewById(R.id.minButt);
        addButt = findViewById(R.id.addButt);
        Button submitButt = findViewById(R.id.submitButt);
        kanaView = findViewById(R.id.kanaView);
        cardMethods = new NewCardMethods(getApplicationContext());
        cardMethods.fillPool(toLoad);
        card = cardMethods.serveCard();
        kanaView.setText(card.getSymbol());
        accuView.setText("Expected Accuracy\n" + (int) (tolerance * 100) + "%");
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

        addButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tolerance < 0.99)
                    tolerance += 0.01;
                if (tolerance > 0.98) {
                    addButt.setClickable(false);
                    addButt.setAlpha(0.3f);
                }
                if (tolerance > 0.88) {
                    minButt.setClickable(true);
                    minButt.setAlpha(1.0f);
                }
                accuView.setText("Expected Accuracy\n" + (int) (tolerance * 100) + "%");
                Toast.makeText(getApplicationContext(), "" + tolerance, Toast.LENGTH_SHORT).show();
            }
        });

        minButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tolerance > 0.88)
                    tolerance -= 0.01;
                if (tolerance < 0.89) {
                    minButt.setClickable(false);
                    minButt.setAlpha(0.3f);
                }
                if (tolerance < 0.99) {
                    addButt.setClickable(true);
                    addButt.setAlpha(1.0f);
                }
                accuView.setText("Expected Accuracy\n" + (int) (tolerance * 100) + "%");
            }
        });

        hideButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHidden) {
                    kanaView.setVisibility(View.VISIBLE);
                    hideButt.setText("hide");
                    isHidden = false;
                } else {
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
                String percentage = "" + (int) (similarity * 100) + "%";

                if (similarity >= tolerance) {
                    drawView.clearCanvas();
                    card = cardMethods.serveCard();
                    kanaView.setText(card.getSymbol());
                    kanaView.setVisibility(View.VISIBLE);
                    isHidden = false;
                    hideButt.setText("hide");
                    kanaView.buildDrawingCache();
                    Toast.makeText(getApplicationContext(), "Looks good! " + percentage, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Hmm.. try that again " + percentage, Toast.LENGTH_SHORT).show();
                    drawView.clearCanvas();
                }
            }
        });
    }

    private double gradeKana(Bitmap b1, Bitmap b2) {     //b1 is Original, b2 is drawing
        b1 = makeMonochrome(b1);
        b1 = compress(b1);
        b2 = compress(b2);
        return compareBitmaps(b1, b2);
    }

    private Bitmap makeMonochrome(Bitmap bitmap) {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        return newBitmap;
    }

    private Bitmap compress(Bitmap bitmap) {
        return ThumbnailUtils.extractThumbnail(bitmap, bitmap.getWidth() / 4, bitmap.getHeight() / 4);
    }

    private double compareBitmaps(Bitmap bitmap1, Bitmap bitmap2) {
        int pixel1, pixel2, r1, r2, g1, g2, b1, b2;
        int match = 0;
        int width = bitmap2.getWidth();
        int height = bitmap2.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixel2 = bitmap2.getPixel(x, y);
                r2 = Color.red(pixel2);
                g2 = Color.green(pixel2);
                b2 = Color.blue(pixel2);
                pixel1 = bitmap1.getPixel(x, y);
                r1 = Color.red(pixel1);
                g1 = Color.green(pixel1);
                b1 = Color.blue(pixel1);
                if (r2 == r1 && g2 == g1 && b2 == b1)
                    match++;
            }
        }
        double matchD = match;
        double volume = width * height;
        return matchD / volume;
    }
}
