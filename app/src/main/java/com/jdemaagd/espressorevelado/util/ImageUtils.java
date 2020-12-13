package com.jdemaagd.espressorevelado.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.jdemaagd.espressorevelado.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.NoSuchElementException;

public class ImageUtils {

    public static BitmapDrawable scaleAndSetImage(Uri uri, Context context, int dimension)
            throws NoSuchElementException {
        Bitmap bitmap;
        Drawable drawable;

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            drawable = Drawable.createFromStream(inputStream, uri.toString() );
        } catch (FileNotFoundException e) {
            drawable = context.getResources().getDrawable(R.drawable.logo, null);
        }

        try {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("No drawable on given view");
        }

        // Get current dimensions AND desired bounding box
        int width = 0;

        try {
            width = bitmap.getWidth();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("Cannot find bitmap on given view/drawable");
        }

        int height = bitmap.getHeight();
        int bounding = dpToPx(dimension, context);

        // Determine how much to scale:
        // dimension requiring less scaling is closer to the its side
        // image always stays inside bounding box AND either x/y axis touches it
        float xScale = ((float) bounding) / width;
        float yScale = ((float) bounding) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;

        // Create a matrix for the scaling and add the scaling data
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        width = scaledBitmap.getWidth();        // re-use
        height = scaledBitmap.getHeight();      // re-use
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);

        return result;
    }

    private static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;

        return Math.round((float)dp * density);
    }
}