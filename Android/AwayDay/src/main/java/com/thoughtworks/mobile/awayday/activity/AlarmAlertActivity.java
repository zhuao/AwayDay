package com.thoughtworks.mobile.awayday.activity;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.components.AlarmItemView;
import com.thoughtworks.mobile.awayday.helper.AlarmAlertPlayer;
import com.thoughtworks.mobile.awayday.helper.AlarmAlertWakeLock;

public class AlarmAlertActivity extends Activity {
    private AlarmAlertPlayer alarmAlertPlayer;
    private ViewGroup alarmItemContainer;
    private Button cancelBtn;
    private KeyguardManager.KeyguardLock keyguardLock;
    private KeyguardManager keyguardManager;

    private void fillInMessage(Intent paramIntent) {
        AlarmItemView localAlarmItemView = new AlarmItemView(this);
        localAlarmItemView.fillWithData(paramIntent.getStringExtra("message"));
        LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -2);
        this.alarmItemContainer.addView(localAlarmItemView, 0, localLayoutParams);
    }

    private void initAlertPlayer() {
        this.alarmAlertPlayer = new AlarmAlertPlayer(this);
    }

    private void initListener() {
        this.cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                AlarmAlertActivity.this.alarmAlertPlayer.release();
                AlarmAlertActivity.this.finish();
            }
        });
    }

    private void initUI() {
        setContentView(R.layout.alarm_alert_layout);
        this.alarmItemContainer = ((ViewGroup) findViewById(R.id.alarm_item_container));
        this.cancelBtn = ((Button) findViewById(R.id.alarm_cancel_button));
    }

    private void lockKeyBoard() {
        this.keyguardLock = this.keyguardManager.newKeyguardLock("");
        this.keyguardLock.reenableKeyguard();
    }

    private void prepareAlarm() {
        AlarmAlertWakeLock.acquire(this);
        getWindow().addFlags(2621568);
        this.keyguardManager = ((KeyguardManager) getSystemService("keyguard"));
        lockKeyBoard();
    }

    public void finish() {
        AlarmAlertWakeLock.release();
        super.finish();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        prepareAlarm();
        initUI();
        initAlertPlayer();
        initListener();
    }

    protected void onNewIntent(Intent paramIntent) {
        super.onNewIntent(paramIntent);
        fillInMessage(paramIntent);
        if (!this.alarmAlertPlayer.isReleased()) {
            this.alarmAlertPlayer.resetAlarm();
            return;
        }
        this.alarmAlertPlayer = new AlarmAlertPlayer(this);
        this.alarmAlertPlayer.startAlarm();
    }

    protected void onPostCreate(Bundle paramBundle) {
        super.onPostCreate(paramBundle);
        fillInMessage(getIntent());
        this.alarmAlertPlayer.startAlarm();
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.activity.AlarmAlertActivity
 * JD-Core Version:    0.6.2
 */
