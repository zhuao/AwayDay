package com.thoughtworks.mobile.awayday.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LocalSharedPreferencesStorage
        implements PreferencesStorage {
    private static final String EMPTY = "";
    private static final String SETTINGS_FILE_NAME = "settings_file";
    private static final String SETTINGS_USERNAME_KEY = "username";
    private Context context;

    public LocalSharedPreferencesStorage(Context paramContext) {
        this.context = paramContext;
    }

    public String getUserName() {
        return this.context.getSharedPreferences("settings_file", 1).getString("username", "");
    }

    public void setUserName(String paramString) {
        SharedPreferences.Editor localEditor = this.context.getSharedPreferences("settings_file", 1).edit();
        localEditor.putString("username", paramString);
        localEditor.commit();
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.storage.LocalSharedPreferencesStorage
 * JD-Core Version:    0.6.2
 */
