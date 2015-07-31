package com.thoughtworks.mobile.awayday.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.thoughtworks.mobile.awayday.service.AlarmReceiver;

import java.util.Date;

public class RemindAlarmHelper {
    private static RemindAlarmHelper remindAlarmHelper;
    private AlarmManager alarmManager;
    private Context context;

    private RemindAlarmHelper(Context paramContext) {
        this.context = paramContext;
        this.alarmManager = ((AlarmManager) paramContext.getSystemService("alarm"));
    }

    public static RemindAlarmHelper getInstance(Context paramContext) {
        if (remindAlarmHelper == null)
            remindAlarmHelper = new RemindAlarmHelper(paramContext);
        return remindAlarmHelper;
    }

    private void showToast(String paramString) {
        Toast.makeText(this.context, paramString, 0).show();
    }

    public void cancelRemind(int paramInt) {
        Intent localIntent = new Intent(this.context, AlarmReceiver.class);
        this.alarmManager.cancel(PendingIntent.getBroadcast(this.context, paramInt, localIntent, 268435456));
    }

    public void startAlarm(int paramInt, String paramString, Date paramDate) {
        Intent localIntent = new Intent(this.context, AlarmReceiver.class);
        localIntent.putExtra("message", paramString);
        Log.d("reminder set", paramDate.toString());
        PendingIntent localPendingIntent = PendingIntent.getBroadcast(this.context, paramInt, localIntent, 134217728);
        this.alarmManager.set(0, paramDate.getTime(), localPendingIntent);
        showToast("Alarm set successfully.");
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.helper.RemindAlarmHelper
 * JD-Core Version:    0.6.2
 */
