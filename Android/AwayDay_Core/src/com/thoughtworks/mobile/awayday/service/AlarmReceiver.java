package com.thoughtworks.mobile.awayday.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.thoughtworks.mobile.awayday.activity.AlarmAlertActivity;
import com.thoughtworks.mobile.awayday.helper.AlarmAlertWakeLock;

public class AlarmReceiver extends BroadcastReceiver {
    private Context context;

    private void prepareForAlarm() {
        AlarmAlertWakeLock.acquire(this.context);
        Intent localIntent = new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        this.context.sendBroadcast(localIntent);
    }

    private void startAlarmAlertActivity(String paramString) {
        Intent localIntent = new Intent(this.context, AlarmAlertActivity.class);
        localIntent.putExtra("message", paramString);
        localIntent.setFlags(268697600);
        this.context.startActivity(localIntent);
    }

    public void onReceive(Context paramContext, Intent paramIntent) {
        this.context = paramContext;
        String str = paramIntent.getStringExtra("message");
        if (str == null)
            str = "";
        prepareForAlarm();
        startAlarmAlertActivity(str);
    }
}