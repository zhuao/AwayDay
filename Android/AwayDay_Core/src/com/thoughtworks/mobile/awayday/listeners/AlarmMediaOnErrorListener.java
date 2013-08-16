package com.thoughtworks.mobile.awayday.listeners;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import com.thoughtworks.mobile.awayday.helper.AlarmAlertPlayer;

public class AlarmMediaOnErrorListener
        implements MediaPlayer.OnErrorListener {
    private AlarmAlertPlayer alarmAlertPlayer;

    public AlarmMediaOnErrorListener(AlarmAlertPlayer paramAlarmAlertPlayer) {
        this.alarmAlertPlayer = paramAlarmAlertPlayer;
    }

    public boolean onError(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2) {
        this.alarmAlertPlayer.release();
        return false;
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.listeners.AlarmMediaOnErrorListener
 * JD-Core Version:    0.6.2
 */
