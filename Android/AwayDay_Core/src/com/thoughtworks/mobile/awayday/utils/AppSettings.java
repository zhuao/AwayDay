package com.thoughtworks.mobile.awayday.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppSettings {
    private static Properties properties;

    private static void ensurePropertyLoaded() {
        if (properties == null) {
            properties = new Properties();
            readFile("/res/raw/app.properties");
        }
    }

    public static int getBitmapCompressedFactor() {
        return Integer.parseInt(getProperty("bitmap.compress.factor"));
    }

    public static int getBitmapCompressedHeight() {
        return Integer.parseInt(getProperty("bitmap.compress.width"));
    }

    public static int getBitmapCompressedQuality() {
        return Integer.parseInt(getProperty("bitmap.compress.quality"));
    }

    public static int getBitmapCompressedWidth() {
        return Integer.parseInt(getProperty("bitmap.compress.height"));
    }

    private static String getProperty(String paramString) {
        ensurePropertyLoaded();
        return properties.getProperty(paramString);
    }

    public static String getServerUrl() {
        return getProperty("server.url");
    }

    public static String getShareServerUrl() {
        return getProperty("share.url");
    }

    public static boolean isSharedImageOn() {
        return Boolean.parseBoolean(getProperty("share.image.switcher"));
    }

    private static void readFile(String propertyFilePath) {
        InputStream localInputStream = null;
        try {
            localInputStream = AppSettings.class.getResourceAsStream(propertyFilePath);
            properties.load(localInputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (localInputStream != null) {
                try {
                    localInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}