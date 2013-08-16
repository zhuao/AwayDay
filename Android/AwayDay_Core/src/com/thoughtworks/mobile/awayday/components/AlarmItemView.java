package com.thoughtworks.mobile.awayday.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.thoughtworks.mobile.awayday.R;

public class AlarmItemView extends LinearLayout {
    private TextView alarmMessage;

    public AlarmItemView(Context paramContext) {
        super(paramContext);
        initUI();
    }

    private void initUI() {
        addView((ViewGroup) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.alarm_item_layout, this, false));
        this.alarmMessage = ((TextView) findViewById(R.id.alarm_message));
    }

    public void fillWithData(String paramString) {
        this.alarmMessage.setText(paramString);
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.components.AlarmItemView
 * JD-Core Version:    0.6.2
 */
