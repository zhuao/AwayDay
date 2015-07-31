package com.thoughtworks.mobile.awayday.screen;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.helper.RemindAlarmHelper;
import com.thoughtworks.mobile.awayday.listeners.ScreenBackWithResultButtonClickedListener;
import com.thoughtworks.mobile.awayday.presenter.ReminderPresenter;
import com.thoughtworks.mobile.awayday.widget.OnWheelClickedListener;
import com.thoughtworks.mobile.awayday.widget.WheelView;
import com.thoughtworks.mobile.awayday.widget.adapters.ArrayWheelAdapter;

import java.util.Date;

public class ReminderScreen extends RelativeLayout {
    private ImageView backImageView;
    private RemindAlarmHelper remindAlarmHelper;
    private TextView remindTimeTextView;
    private ReminderPresenter reminderPresenter;
    private ScreenBackWithResultButtonClickedListener screenBackButtonClickedListener;
    private WheelView wheelView;

    public ReminderScreen(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        initUI();
    }

    private void initListener() {
        this.wheelView.addChangingListener(this.reminderPresenter);
        this.wheelView.addClickingListener(new OnWheelClickedListener() {
            public void onItemClicked(WheelView paramAnonymousWheelView, int paramAnonymousInt) {
                paramAnonymousWheelView.setCurrentItem(paramAnonymousInt, true);
            }
        });
        this.backImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                setRemindTime();
                ScreenBackWithResultButtonClickedListener localScreenBackWithResultButtonClickedListener;
                if (ReminderScreen.this.screenBackButtonClickedListener != null) {
                    localScreenBackWithResultButtonClickedListener = ReminderScreen.this.screenBackButtonClickedListener;

                    boolean bool = true;
                    if (reminderPresenter.getRemindDate() == null) {
                        bool = false;
                    }
                    localScreenBackWithResultButtonClickedListener.onBackWithResult(reminderPresenter.getRemindId(), bool);
                }
            }
        });
    }

    private void initUI() {
        addView((ViewGroup) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.reminder_screen_layout, this, false));
        this.wheelView = ((WheelView) findViewById(R.id.reminder_wheel_view));
        this.remindTimeTextView = ((TextView) findViewById(R.id.reminder_screen_remind_time));
        this.backImageView = ((ImageView) findViewById(R.id.reminder_screen_back_btn));
    }

    private void initWheelAdapter() {
        ArrayWheelAdapter localArrayWheelAdapter = new ArrayWheelAdapter(getContext(), this.reminderPresenter.getWheelArrayItems());
        localArrayWheelAdapter.setItemResource(R.layout.wheel_text_item);
        localArrayWheelAdapter.setItemTextResource(R.id.wheel_text_item_text);
        this.wheelView.setViewAdapter(localArrayWheelAdapter);
    }

    private void setRemindTime() {
        Date localDate = this.reminderPresenter.getRemindDate();
        if (localDate == null)
            return;
        this.remindAlarmHelper.startAlarm(this.reminderPresenter.getRemindId(), this.reminderPresenter.getRemindMessage(), localDate);
        this.reminderPresenter.setRemindForSession();
    }

    public void initComponent(ReminderPresenter paramReminderPresenter, RemindAlarmHelper paramRemindAlarmHelper) {
        this.reminderPresenter = paramReminderPresenter;
        this.remindAlarmHelper = paramRemindAlarmHelper;
        initListener();
        initWheelAdapter();
    }

    public void setBackButtonClickedListener(ScreenBackWithResultButtonClickedListener paramScreenBackWithResultButtonClickedListener) {
        this.screenBackButtonClickedListener = paramScreenBackWithResultButtonClickedListener;
    }

    public void updateRemindTime(String paramString) {
        this.remindTimeTextView.setText(paramString);
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.screen.ReminderScreen
 * JD-Core Version:    0.6.2
 */
