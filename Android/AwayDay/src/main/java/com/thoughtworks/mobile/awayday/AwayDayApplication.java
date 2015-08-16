package com.thoughtworks.mobile.awayday;

import android.app.Application;

import com.thoughtworks.mobile.awayday.factory.BeanRegister;
import com.weibo.sdk.android.Oauth2AccessToken;

public class AwayDayApplication extends Application {

    public static Oauth2AccessToken accessToken = null;

    @Override
    public void onCreate() {
        super.onCreate();
        new BeanRegister(getApplicationContext()).onRegister();
    }
}
