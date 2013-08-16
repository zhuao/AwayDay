package com.thoughtworks.mobile.awayday.helper;

import android.content.Context;
import android.os.PowerManager;

public class AlarmAlertWakeLock {
    private static PowerManager.WakeLock sWakeLock;

    public static void acquire(Context paramContext) {
        if (sWakeLock != null)
            sWakeLock.release();
        sWakeLock = ((PowerManager) paramContext.getSystemService("power")).newWakeLock(805306394, "AlarmClock");
        sWakeLock.acquire();
    }

    public static void release() {
        if (sWakeLock != null) {
            sWakeLock.release();
            sWakeLock = null;
        }
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.helper.AlarmAlertWakeLock
 * JD-Core Version:    0.6.2
 */
