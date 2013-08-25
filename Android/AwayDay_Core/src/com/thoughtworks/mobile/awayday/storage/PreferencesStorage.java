package com.thoughtworks.mobile.awayday.storage;

import com.weibo.sdk.android.Oauth2AccessToken;

public abstract interface PreferencesStorage {
    public abstract String getUserName();

    public abstract void setUserName(String username);

    public abstract void saveWeiboAccessToken(String accessToken, String expiresIn);

    public abstract Oauth2AccessToken getWeiboAccessToken();
}