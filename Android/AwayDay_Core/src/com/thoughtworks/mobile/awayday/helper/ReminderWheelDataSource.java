package com.thoughtworks.mobile.awayday.helper;

import com.thoughtworks.mobile.awayday.domain.WheelItem;

import java.util.ArrayList;
import java.util.List;

public class ReminderWheelDataSource {
    private static List<WheelItem> wheelItems = new ArrayList();

    static {
        wheelItems.add(new WheelItem("No Alarm", 0));
        wheelItems.add(new WheelItem("5 Minutes before", -5));
        wheelItems.add(new WheelItem("10 Minutes before", -10));
        wheelItems.add(new WheelItem("30 Minutes before", -30));
        wheelItems.add(new WheelItem("1 Hour before", -60));
    }

    public static String getReminderItemLabelByIndex(int paramInt) {
        return ((WheelItem) wheelItems.get(paramInt)).label;
    }

    public static int getReminderItemValueByIndex(int paramInt) {
        return ((WheelItem) wheelItems.get(paramInt)).value;
    }

    public static WheelItem[] getWheelItems() {
        return (WheelItem[]) wheelItems.toArray(new WheelItem[0]);
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.helper.ReminderWheelDataSource
 * JD-Core Version:    0.6.2
 */
