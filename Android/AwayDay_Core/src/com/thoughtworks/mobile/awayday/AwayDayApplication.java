package com.thoughtworks.mobile.awayday;

import android.app.Application;
import android.app.Notification;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import com.thoughtworks.mobile.awayday.domain.Settings;
import com.weibo.sdk.android.Oauth2AccessToken;

public class AwayDayApplication extends Application {

    public static Oauth2AccessToken accessToken = null;

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        initJPushNotificationView();
        JPushInterface.resumePush(this);

        initWeiboAccessToken();
    }

    private void initWeiboAccessToken() {
        accessToken = Settings.getSettings().getWeiboAccessToken();
    }

    private void initJPushNotificationView() {
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
        builder.statusBarDrawable = R.drawable.ic_launcher;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;
        builder.notificationDefaults = Notification.DEFAULT_VIBRATE;
        JPushInterface.setPushNotificationBuilder(1, builder);

    }

}
