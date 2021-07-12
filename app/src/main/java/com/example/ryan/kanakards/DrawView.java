package com.example.ryan.kanakards;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View {

    private static Path path;
    private static int paintColor = Color.BLACK;
    private Paint drawPaint, canvasPaint;
    private Canvas drawCanvas;
    private Bitmap bitmap;

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        path = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        float STROKE_WIDTH = 50f;
        drawPaint.setStrokeWidth(STROKE_WIDTH);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(bitmap);
        drawCanvas.drawColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, canvasPaint);
        canvas.drawPath(path, drawPaint);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        canvasPaint.setColor(paintColor);
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawCanvas.drawPath(path, drawPaint);
                path.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                path.lineTo(touchX, touchY);
                drawCanvas.drawPath(path, drawPaint);
                path.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    public void clearCanvas() {
        drawCanvas.drawColor(Color.WHITE);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
