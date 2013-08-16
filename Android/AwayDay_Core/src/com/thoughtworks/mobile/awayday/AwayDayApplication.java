package com.thoughtworks.mobile.awayday;

import android.app.Application;
import android.app.Notification;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

public class AwayDayApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        initJPushNotificationView();
        JPushInterface.resumePush(this);
    }

    private void initJPushNotificationView() {
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
        builder.statusBarDrawable = R.drawable.ic_launcher;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;
        builder.notificationDefaults = Notification.DEFAULT_VIBRATE;
        JPushInterface.setPushNotificationBuilder(1, builder);

//        CustomPushNotificationBuilder builder2 = new CustomPushNotificationBuilder(this,R.layout.notification_layout,R.id.notification_icon, R.id.notification_title, R.id.notification_text);
//        builder2.layoutIconDrawable = R.drawable.ic_launcher;
//        builder2.developerArg0 = "developerArg2";
//        JPushInterface.setPushNotificationBuilder(2, builder2);
    }


}
