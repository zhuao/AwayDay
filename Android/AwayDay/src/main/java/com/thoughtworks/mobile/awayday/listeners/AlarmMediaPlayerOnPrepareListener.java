package com.thoughtworks.mobile.awayday.listeners;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import com.thoughtworks.mobile.awayday.helper.AlarmAlertPlayer;

public class AlarmMediaPlayerOnPrepareListener
        implements MediaPlayer.OnPreparedListener {
    private AlarmAlertPlayer alarmAlertPlayer;

    public AlarmMediaPlayerOnPrepareListener(AlarmAlertPlayer paramAlarmAlertPlayer) {
        this.alarmAlertPlayer = paramAlarmAlertPlayer;
    }

    public void onPrepared(MediaPlayer paramMediaPlayer) {
        this.alarmAlertPlayer.startRing();
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.listeners.AlarmMediaPlayerOnPrepareListener
 * JD-Core Version:    0.6.2
 */
