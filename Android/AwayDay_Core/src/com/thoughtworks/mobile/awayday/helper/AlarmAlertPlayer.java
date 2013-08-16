package com.thoughtworks.mobile.awayday.helper;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;
import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.listeners.AlarmMediaOnErrorListener;
import com.thoughtworks.mobile.awayday.listeners.AlarmMediaPlayerOnPrepareListener;

import java.io.IOException;

public class AlarmAlertPlayer {
    private static final long[] VIBRATE_PATTERN = {500L, 500L};
    private AlarmTimer alarmTimer;
    private boolean isReleased = false;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    public AlarmAlertPlayer(Context paramContext) {
        initialize(paramContext);
        addMediaPlayerListeners();
        configureMediaPlayer();
        setDataSource(paramContext);
    }

    private void addMediaPlayerListeners() {
        this.mediaPlayer.setOnErrorListener(new AlarmMediaOnErrorListener(this));
        this.mediaPlayer.setOnPreparedListener(new AlarmMediaPlayerOnPrepareListener(this));
    }

    private void configureMediaPlayer() {
        this.mediaPlayer.setAudioStreamType(4);
        this.mediaPlayer.setLooping(true);
    }

    private void initialize(Context paramContext) {
        this.alarmTimer = new AlarmTimer(this);
        this.vibrator = ((Vibrator) paramContext.getSystemService("vibrator"));
        this.mediaPlayer = new MediaPlayer();
    }

    private void prepareRing() {
        this.mediaPlayer.prepareAsync();
    }

    private void setDataSource(Context paramContext) {
        try {
            setMediaDataSourceFromResource(this.mediaPlayer, paramContext.getResources(), R.raw.fallbackring);
            return;
        } catch (IOException localIOException) {
            Log.e("AlarmAlertActivity", "Can not load alarm ring");
        }
    }

    private void setMediaDataSourceFromResource(MediaPlayer paramMediaPlayer, Resources paramResources, int paramInt)
            throws IOException {
        AssetFileDescriptor localAssetFileDescriptor = paramResources.openRawResourceFd(paramInt);
        if (localAssetFileDescriptor != null) {
            paramMediaPlayer.setDataSource(localAssetFileDescriptor.getFileDescriptor(), localAssetFileDescriptor.getStartOffset(), localAssetFileDescriptor.getLength());
            localAssetFileDescriptor.close();
        }
    }

    private void startVibrate() {
        this.vibrator.vibrate(VIBRATE_PATTERN, 0);
    }

    public void alertTimeout() {
        release();
        this.isReleased = true;
    }

    public boolean isReleased() {
        return this.isReleased;
    }

    public void release() {
        if (this.vibrator != null) {
            this.vibrator.cancel();
            this.vibrator = null;
        }
        if (this.mediaPlayer != null) {
            this.mediaPlayer.stop();
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }
    }

    public void resetAlarm() {
        this.alarmTimer.reset();
    }

    public void startAlarm() {
        startVibrate();
        prepareRing();
    }

    public void startRing() {
        this.mediaPlayer.start();
        this.alarmTimer.start();
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.helper.AlarmAlertPlayer
 * JD-Core Version:    0.6.2
 */
