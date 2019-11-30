package com.example.ryan.kanakards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class TraceViewer extends AppCompatActivity {

    private DrawView drawView;
    private Button clearButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_trace_viewer);

        drawView = findViewById(R.id.drawView);
        clearButt = findViewById(R.id.clearButt);

        drawView.setDrawingCacheEnabled(true);
        drawView.setVisibility(View.VISIBLE);
        drawView.setEnabled(true);
        drawView.invalidate();

        clearButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawView.clearCanvas();
            }
        });

    }

}
