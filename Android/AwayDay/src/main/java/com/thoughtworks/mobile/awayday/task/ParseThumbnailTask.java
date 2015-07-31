package com.thoughtworks.mobile.awayday.task;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import com.thoughtworks.mobile.awayday.helper.BitmapHelper;
import com.thoughtworks.mobile.awayday.listeners.ParseThumbnailListener;

import java.io.FileNotFoundException;

public class ParseThumbnailTask extends AsyncTask<Uri, Void, Bitmap> {
    private ContentResolver contentResolver;
    private ParseThumbnailListener parseThumbnailListener;

    public ParseThumbnailTask(ContentResolver paramContentResolver, ParseThumbnailListener paramParseThumbnailListener) {
        this.contentResolver = paramContentResolver;
        this.parseThumbnailListener = paramParseThumbnailListener;
    }

    protected Bitmap doInBackground(Uri[] paramArrayOfUri) {
        if (paramArrayOfUri.length == 0)
            return null;
        try {
            Bitmap localBitmap = BitmapHelper.extractCompressedBitmap(this.contentResolver, paramArrayOfUri[0], 100, 100);
            return localBitmap;
        } catch (FileNotFoundException localFileNotFoundException) {
            localFileNotFoundException.printStackTrace();
            Log.e("share image", "can not find image file " + paramArrayOfUri[0]);
        }
        return null;
    }

    protected void onPostExecute(Bitmap paramBitmap) {
        this.parseThumbnailListener.onParseEnd(paramBitmap);
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.task.ParseThumbnailTask
 * JD-Core Version:    0.6.2
 */
