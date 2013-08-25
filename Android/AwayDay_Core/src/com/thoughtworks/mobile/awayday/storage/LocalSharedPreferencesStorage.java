package com.thoughtworks.mobile.awayday.storage;

import android.content.Context;
import android.content.SharedPreferences;
import com.weibo.sdk.android.Oauth2AccessToken;

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
        return getSharedPreferences().getString(SETTINGS_USERNAME_KEY, EMPTY);
    }

    public void setUserName(String username) {
        SharedPreferences.Editor localEditor = getSharedPreferences().edit();
        localEditor.putString("username", username);
        localEditor.commit();
    }

    public void saveWeiboAccessToken(String accessToken, String expireIn) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString("weibo_access_token", accessToken);
        editor.putString("weibo_expire_in", expireIn);
        editor.commit();
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(SETTINGS_FILE_NAME, 1);
    }

    public Oauth2AccessToken getWeiboAccessToken() {
        if (getSharedPreferences().getString("weibo_access_token", null) != null && getSharedPreferences().getString("weibo_expire_in", null) != null) {
            return new Oauth2AccessToken(getSharedPreferences().getString("weibo_access_token", null), getSharedPreferences().getString("weibo_expire_in", null));
        }
        return null;
    }

}