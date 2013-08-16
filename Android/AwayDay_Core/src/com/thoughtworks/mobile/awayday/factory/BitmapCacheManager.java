package com.thoughtworks.mobile.awayday.factory;

import android.graphics.Bitmap;
import android.util.Log;
import com.thoughtworks.mobile.awayday.storage.Lru2Cache;

public class BitmapCacheManager {
    private static final int CAPACITY = 25;
    private static BitmapCacheManager bitmapCacheManager;
    private static Lru2Cache<String, Bitmap> cache = new Lru2Cache(25);

    public static BitmapCacheManager getInstance() {
        if (bitmapCacheManager == null)
            bitmapCacheManager = new BitmapCacheManager();
        return bitmapCacheManager;
    }

    public void clearCache() {
        Log.w("low memory", "clear path images!");
        cache.clear();
    }

    public Bitmap getCacheableBitmap(String paramString) {
        Bitmap localBitmap = (Bitmap) cache.get(paramString);
        StringBuilder localStringBuilder = new StringBuilder().append(paramString).append(", is in cache=");
        if (localBitmap != null) {
            Log.i("load image from cache", localStringBuilder.toString());
            return localBitmap;
        }
        return null;
    }

    public void saveImageToCache(String paramString, Bitmap paramBitmap) {
        cache.put(paramString, paramBitmap);
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.factory.BitmapCacheManager
 * JD-Core Version:    0.6.2
 */
