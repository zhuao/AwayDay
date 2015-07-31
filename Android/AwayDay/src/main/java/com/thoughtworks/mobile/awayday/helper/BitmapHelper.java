package com.thoughtworks.mobile.awayday.helper;

import android.content.ContentResolver;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.PorterDuff.Mode;
import android.net.Uri;
import android.util.Log;
import com.thoughtworks.mobile.awayday.factory.BitmapCacheManager;

import java.io.FileNotFoundException;

public class BitmapHelper {
    private static int maxHeight;
    private static int maxWidth;

    private static Bitmap adjustBitmap(BitmapFactory.Options paramOptions, ContentResolver paramContentResolver, Uri paramUri, int paramInt)
            throws FileNotFoundException {
        paramOptions.inJustDecodeBounds = false;
        paramOptions.inPurgeable = true;
        paramOptions.inSampleSize = paramInt;
        Log.d("original image size:", "width=" + paramOptions.outWidth + ", height=" + paramOptions.outHeight + ". convert to: width=" + paramOptions.outWidth / paramInt + ", height=" + paramOptions.outHeight / paramInt + ", pathMaxWidth=" + maxWidth + ",pathMaxHeight=" + maxHeight);
        try {
            Bitmap localBitmap = BitmapFactory.decodeStream(paramContentResolver.openInputStream(paramUri), null, paramOptions);
            return localBitmap;
        } catch (OutOfMemoryError localOutOfMemoryError) {
            localOutOfMemoryError.printStackTrace();
            BitmapCacheManager.getInstance().clearCache();
        }
        return null;
    }

    public static Bitmap convertToRoundedBitmap(Bitmap paramBitmap, int paramInt) {
        int width = paramBitmap.getWidth();
        int height = paramBitmap.getHeight();
        if (width <= height) {
            height = width;
        } else {
            width = height;
        }
        Bitmap localBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint();
        Rect localRect = new Rect(0, 0, width, height);
        RectF localRectF = new RectF(localRect);
        float f = paramInt;
        localPaint.setAntiAlias(true);
        localCanvas.drawARGB(0, 0, 0, 0);
        localCanvas.drawRoundRect(localRectF, f, f, localPaint);
        localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        localCanvas.drawBitmap(paramBitmap, localRect, localRect, localPaint);
        return localBitmap;
    }

    public static Bitmap extractCompressedBitmap(ContentResolver paramContentResolver, Uri paramUri, int paramInt1, int paramInt2)
            throws FileNotFoundException {
        BitmapFactory.Options localOptions = getBoundsOptions(paramContentResolver, paramUri);
        int i = localOptions.outWidth;
        int j = localOptions.outHeight;
        return adjustBitmap(localOptions, paramContentResolver, paramUri, Math.max(i / paramInt1, j / paramInt2));
    }

    private static BitmapFactory.Options getBoundsOptions(ContentResolver paramContentResolver, Uri paramUri)
            throws FileNotFoundException {
        BitmapFactory.Options localOptions = new BitmapFactory.Options();
        localOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(paramContentResolver.openInputStream(paramUri), null, localOptions);
        return localOptions;
    }

    public static Bitmap loadPathBitmap(ContentResolver paramContentResolver, Uri paramUri)
            throws FileNotFoundException {
        BitmapFactory.Options localOptions = getBoundsOptions(paramContentResolver, paramUri);
        int i = localOptions.outWidth;
        int j = localOptions.outHeight;
        int k = Math.min(i / maxWidth, j / maxHeight);
        return scaleBitmap(adjustBitmap(localOptions, paramContentResolver, paramUri, k), i / k, j / k);
    }

    private static Bitmap scaleBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2) {
        if (paramBitmap == null)
            return null;
        try {
            Bitmap localBitmap = Bitmap.createScaledBitmap(paramBitmap, paramInt1, paramInt2, false);
            return localBitmap;
        } catch (OutOfMemoryError localOutOfMemoryError) {
            localOutOfMemoryError.printStackTrace();
            BitmapCacheManager.getInstance().clearCache();
        }
        return null;
    }

    public static void setPathImageMaxSize(int paramInt1, int paramInt2) {
        maxWidth = paramInt1;
        maxHeight = paramInt2;
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.helper.BitmapHelper
 * JD-Core Version:    0.6.2
 */
