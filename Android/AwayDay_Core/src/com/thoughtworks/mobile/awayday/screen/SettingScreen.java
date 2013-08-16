package com.thoughtworks.mobile.awayday.screen;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.domain.Settings;
import com.thoughtworks.mobile.awayday.listeners.OnSettingsModifyActionListener;
import com.thoughtworks.mobile.awayday.utils.StringUtils;
import com.thoughtworks.mobile.awayday.utils.ViewUtils;

public class SettingScreen extends LinearLayout {
    private ImageView modifyTextView;
    private OnSettingsModifyActionListener onSettingsModifyActionListener;
    private EditText userNameTextView;

    public SettingScreen(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        initUI();
        initListener();
    }

    private void handleInput() {
        if (StringUtils.isEmpty(this.userNameTextView.getText().toString()))
            return;
        ViewUtils.hideKeyboard(this.userNameTextView);
        Settings.getSettings().saveUserName(this.userNameTextView.getText().toString());
        notifySettingsChanged();
    }

    private void initListener() {
        this.modifyTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                SettingScreen.this.handleInput();
            }
        });
        this.userNameTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView paramAnonymousTextView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent) {
                SettingScreen.this.handleInput();
                return false;
            }
        });
    }

    private void initUI() {
        addView((ViewGroup) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.settings_screen_layout, this, false));
        this.userNameTextView = ((EditText) findViewById(R.id.settings_username_text));
        this.modifyTextView = ((ImageView) findViewById(R.id.settings_modify_btn));
    }

    private void notifySettingsChanged() {
        if (this.onSettingsModifyActionListener != null)
            this.onSettingsModifyActionListener.onSettingsModifyAction();
    }

    public void setOnSettingsModifyActionListener(OnSettingsModifyActionListener paramOnSettingsModifyActionListener) {
        this.onSettingsModifyActionListener = paramOnSettingsModifyActionListener;
    }

    public void setUserName(String paramString) {
        this.userNameTextView.setText(paramString);
        this.userNameTextView.setSelection(this.userNameTextView.getText().length());
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.screen.SettingScreen
 * JD-Core Version:    0.6.2
 */
