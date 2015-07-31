package com.thoughtworks.mobile.awayday.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.domain.Session;
import com.thoughtworks.mobile.awayday.helper.RemindAlarmHelper;
import com.thoughtworks.mobile.awayday.listeners.ScreenBackWithResultButtonClickedListener;
import com.thoughtworks.mobile.awayday.presenter.ReminderPresenter;
import com.thoughtworks.mobile.awayday.screen.ReminderScreen;

public class ReminderActivity extends Activity implements ScreenBackWithResultButtonClickedListener {
    private ReminderScreen reminderScreen;

    private void initComponent(Session paramSession) {
        ReminderPresenter localReminderPresenter = new ReminderPresenter(this.reminderScreen, paramSession);
        this.reminderScreen.initComponent(localReminderPresenter, RemindAlarmHelper.getInstance(this));
    }

    private void initListener() {
        this.reminderScreen.setBackButtonClickedListener(this);
    }

    private void initUI() {
        setContentView(R.layout.reminder_activity_layout);
        this.reminderScreen = ((ReminderScreen) findViewById(R.id.reminder_screen));
    }

    public void onBackWithResult(int paramInt, boolean paramBoolean) {
        Intent localIntent = new Intent();
        localIntent.putExtra("sessionId", paramInt);
        if (paramBoolean) {
            localIntent.putExtra("setResult", true);
        }else {
            localIntent.putExtra("setResult", false);
        }
        setResult(-1, localIntent);
        finish();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        initUI();
        initListener();
    }

    protected void onPostCreate(Bundle paramBundle) {
        super.onPostCreate(paramBundle);
        initComponent((Session) getIntent().getSerializableExtra("session"));
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.activity.ReminderActivity
 * JD-Core Version:    0.6.2
 */
