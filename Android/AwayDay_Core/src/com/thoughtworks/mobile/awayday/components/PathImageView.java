package com.thoughtworks.mobile.awayday.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import com.thoughtworks.mobile.awayday.factory.BitmapCacheManager;
import com.thoughtworks.mobile.awayday.helper.BitmapHelper;

import java.io.FileNotFoundException;

public class PathImageView extends ImageView {
    public PathImageView(Context paramContext) {
        super(paramContext);
    }

    private Bitmap loadFromUri(Uri paramUri) {
        try {
            Bitmap localBitmap1 = BitmapHelper.loadPathBitmap(getContext().getContentResolver(), paramUri);
            if (localBitmap1 == null)
                return null;
            Bitmap localBitmap2 = BitmapHelper.convertToRoundedBitmap(localBitmap1, 8);
            if (localBitmap2 == null)
                return null;
            saveBitmapToCache(paramUri.toString(), localBitmap2);
            return localBitmap2;
        } catch (FileNotFoundException localFileNotFoundException) {
            Log.e("Loading image", " load image " + paramUri.toString() + " error!");
        }
        return null;
    }

    private void saveBitmapToCache(String paramString, Bitmap paramBitmap) {
        BitmapCacheManager.getInstance().saveImageToCache(paramString, paramBitmap);
    }

    public void loadImage(Uri paramUri) {
        Bitmap localBitmap = BitmapCacheManager.getInstance().getCacheableBitmap(paramUri.toString());
        if (localBitmap == null)
            localBitmap = loadFromUri(paramUri);
        if (localBitmap == null)
            return;
        setImageBitmap(localBitmap);
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.components.PathImageView
 * JD-Core Version:    0.6.2
 */
