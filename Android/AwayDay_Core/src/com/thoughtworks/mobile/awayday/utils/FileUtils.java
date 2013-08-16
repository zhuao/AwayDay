package com.thoughtworks.mobile.awayday.utils;

import android.content.Context;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    public static final String TAG = "FileUtils";

    public static String readFiles(Context paramContext, String paramString) {
        FileInputStream localFileInputStream = null;
        ByteArrayOutputStream localByteArrayOutputStream = null;
        try {
            localFileInputStream = paramContext.openFileInput(paramString);
            localByteArrayOutputStream = new ByteArrayOutputStream();
            byte[] arrayOfByte = new byte[1024];
            while (true) {
                int i = localFileInputStream.read(arrayOfByte);
                if (i == -1)
                    break;
                localByteArrayOutputStream.write(arrayOfByte, 0, i);
            }

        } catch (FileNotFoundException localFileNotFoundException) {

            localFileNotFoundException.printStackTrace();
            Log.d("FileUtils", localFileNotFoundException.getMessage());
            return null;

        } catch (IOException localIOException) {
            localIOException.printStackTrace();
            Log.d("FileUtils", localIOException.getMessage());
        } finally {
            try {
                localFileInputStream.close();
                localByteArrayOutputStream.close();
            } catch (IOException localIOException) {
                localIOException.printStackTrace();
                Log.d("FileUtils", localIOException.getMessage());
            }


        }

        String str = localByteArrayOutputStream.toString();
        return str;
    }

    public static void saveFile(Context paramContext, String paramString1, String paramString2) {
        try {
            FileOutputStream localFileOutputStream = paramContext.openFileOutput(paramString1, 0);
            localFileOutputStream.write(paramString2.getBytes());
            localFileOutputStream.close();
            return;
        } catch (FileNotFoundException localFileNotFoundException) {
            localFileNotFoundException.printStackTrace();
            Log.d("FileUtils", localFileNotFoundException.getMessage());
            return;
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
            Log.d("FileUtils", localIOException.getMessage());
        }
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.utils.FileUtils
 * JD-Core Version:    0.6.2
 */
