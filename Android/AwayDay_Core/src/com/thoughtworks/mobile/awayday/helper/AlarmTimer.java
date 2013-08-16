package com.thoughtworks.mobile.awayday.helper;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AlarmTimer {
    private static final int TIMER_INTERVAL = 1000;
    private static final int TIMER_TIME_OUT_IN_SECONDS = 20;
    private AlarmAlertPlayer alarmAlertPlayer;
    private final Lock lock;
    private int passedSecond;
    private Timer timer;

    public AlarmTimer(AlarmAlertPlayer paramAlarmAlertPlayer) {
        this.alarmAlertPlayer = paramAlarmAlertPlayer;
        this.lock = new ReentrantLock();
    }

    private void releaseTimer() {
        if (this.timer == null)
            return;
        this.timer.cancel();
        this.timer.purge();
        this.timer = null;
    }

    private void resetPassedTime() {
        this.lock.lock();
        this.passedSecond = 0;
        this.lock.unlock();
    }

    public void reset() {
        if (this.timer == null) {
            start();
            return;
        }
        resetPassedTime();
    }

    public void start() {
        this.timer = new Timer();
        this.passedSecond = 0;
        TimerTask local1 = new TimerTask() {
            private void updateTime() {
                AlarmTimer.this.lock.lock();
                AlarmTimer.this.passedSecond += TIMER_INTERVAL / 1000;
                AlarmTimer.this.lock.unlock();
            }

            public void run() {
                updateTime();
                if (AlarmTimer.this.passedSecond > TIMER_TIME_OUT_IN_SECONDS)
                    AlarmTimer.this.stop();
            }
        };
        this.timer.schedule(local1, 0L, TIMER_INTERVAL);
    }

    public void stop() {
        this.alarmAlertPlayer.alertTimeout();
        releaseTimer();
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.helper.AlarmTimer
 * JD-Core Version:    0.6.2
 */
