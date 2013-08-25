package com.thoughtworks.mobile.awayday.listeners;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import com.thoughtworks.mobile.awayday.AwayDayApplication;
import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.domain.Path;
import com.thoughtworks.mobile.awayday.domain.Settings;
import com.thoughtworks.mobile.awayday.storage.BeanContext;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;

public class AuthDialogListener implements WeiboAuthListener {

    private final Activity activity;
    private final Footprint paramFootprint;

    public AuthDialogListener(Activity activity, Footprint paramFootprint) {
        this.activity = activity;
        this.paramFootprint = paramFootprint;
    }

    @Override
    public void onComplete(Bundle values) {

        String token = values.getString("access_token");
        String expiresIn = values.getString("expiresIn");

        Oauth2AccessToken accessToken = new Oauth2AccessToken(token, expiresIn);
        if (accessToken.isSessionValid()) {
            AwayDayApplication.accessToken = accessToken;
            Settings.getSettings().saveWeiboAccessToken(token, expiresIn);
            ((Path) BeanContext.getInstance().getBean(Path.class)).shareFootprint(paramFootprint);
        }
    }

    @Override
    public void onError(WeiboDialogError e) {
        Toast.makeText(activity, "Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(activity, "Auth cancel", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onWeiboException(WeiboException e) {
        Toast.makeText(activity, "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}
