package com.example.ryan.kanakards;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.media.ThumbnailUtils;
//Code taken and modified from java2s.com
public class CompareBitmaps {

    public static double calSimilarity(Bitmap bmp1, Bitmap bmp2) {
        bmp1 = toGrayscale(bmp1);
        bmp2 = toGrayscale(bmp2);
        //
        bmp1 = ThumbnailUtils.extractThumbnail(bmp1, 32, 32);
        bmp2 = ThumbnailUtils.extractThumbnail(bmp2, 32, 32);
        //
        int[] pixels1 = new int[bmp1.getWidth() * bmp1.getHeight()];
        int[] pixels2 = new int[bmp2.getWidth() * bmp2.getHeight()];
        bmp1.getPixels(pixels1, 0, bmp1.getWidth(), 0, 0, bmp1.getWidth(),
                bmp1.getHeight());
        bmp2.getPixels(pixels2, 0, bmp2.getWidth(), 0, 0, bmp2.getWidth(),
                bmp2.getHeight());
        //
        int averageColor1 = getAverageOfPixelArray(pixels1);
        int averageColor2 = getAverageOfPixelArray(pixels2);
        //
        int[] p1 = getPixelDeviateWeightsArray(pixels1, averageColor1);
        int[] p2 = getPixelDeviateWeightsArray(pixels2, averageColor2);
        //
        int hammingDistance = getHammingDistance(p1, p2);
        double similarity = calSimilarity(hammingDistance);
        return similarity;
    }

    private static double calSimilarity(int hammingDistance) {
        int length = 32 * 32;
        double similarity = (length - hammingDistance) / (double) length;
        //
        similarity = java.lang.Math.pow(similarity, 2);
        return similarity;
    }

    private static Bitmap toGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    private static int getAverageOfPixelArray(int[] pixels) {
        long sumRed = 0;
        for (int i = 0; i < pixels.length; i++) {
            sumRed += Color.red(pixels[i]);
        }
        int averageRed = (int) (sumRed / pixels.length);
        return averageRed;
    }

    private static int[] getPixelDeviateWeightsArray(int[] pixels, final int averageColor) {
        int[] dest = new int[pixels.length];
        for (int i = 0; i < pixels.length; i++) {
            if(Color.red(pixels[i]) - averageColor > 0)
                dest[i] = 1;
            else
                dest[i] = 0;
            //dest[i] = Color.red(pixels[i]) - averageColor > 0  1 : 0;
        }
        return dest;
    }

    private static int getHammingDistance(int[] a, int[] b) {
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            if(a[i] == b[i])
                sum += 1;
            //sum += a[i] == b[i]  0 : 1;
        }
        return sum;
    }
}
