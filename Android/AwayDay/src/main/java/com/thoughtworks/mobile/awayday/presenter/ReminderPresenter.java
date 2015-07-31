package com.thoughtworks.mobile.awayday.presenter;

import com.thoughtworks.mobile.awayday.domain.Session;
import com.thoughtworks.mobile.awayday.domain.WheelItem;
import com.thoughtworks.mobile.awayday.helper.ReminderWheelDataSource;
import com.thoughtworks.mobile.awayday.screen.ReminderScreen;
import com.thoughtworks.mobile.awayday.storage.BeanContext;
import com.thoughtworks.mobile.awayday.storage.LocalStorage;
import com.thoughtworks.mobile.awayday.utils.StringUtils;
import com.thoughtworks.mobile.awayday.widget.OnWheelChangedListener;
import com.thoughtworks.mobile.awayday.widget.WheelView;

import java.util.Date;

public class ReminderPresenter implements OnWheelChangedListener {
    private ReminderScreen reminderScreen;
    private int selectedReminderItemIndex;
    private Session session;

    public ReminderPresenter(ReminderScreen paramReminderScreen, Session paramSession) {
        this.reminderScreen = paramReminderScreen;
        this.session = paramSession;
    }

    private int parseMinuteToMilli(int paramInt) {
        return 1000 * (paramInt * 60);
    }

    public Date getRemindDate() {
        int i = ReminderWheelDataSource.getReminderItemValueByIndex(this.selectedReminderItemIndex);
        if (i == 0)
            return null;
        return new Date(this.session.sessionStartTime.getTime() + parseMinuteToMilli(i));
    }

    public int getRemindId() {
        return this.session.sessionId;
    }

    public String getRemindMessage() {
        return StringUtils.formatAlarmMessage(this.session);
    }

    public WheelItem[] getWheelArrayItems() {
        return ReminderWheelDataSource.getWheelItems();
    }

    public void onChanged(WheelView paramWheelView, int paramInt1, int paramInt2) {
        this.selectedReminderItemIndex = paramInt2;
        this.reminderScreen.updateRemindTime(ReminderWheelDataSource.getReminderItemLabelByIndex(paramInt2));
    }

    public void setRemindForSession() {
        this.session.hasReminder = true;
        ((LocalStorage) BeanContext.getInstance().getBean(LocalStorage.class)).reminderSession(this.session.sessionId, true);
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.presenter.ReminderPresenter
 * JD-Core Version:    0.6.2
 */
