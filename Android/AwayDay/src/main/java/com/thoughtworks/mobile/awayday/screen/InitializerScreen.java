package com.thoughtworks.mobile.awayday.screen;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.domain.Settings;
import com.thoughtworks.mobile.awayday.utils.StringUtils;
import com.thoughtworks.mobile.awayday.utils.ViewUtils;

public class InitializerScreen extends LinearLayout {
    private ImageView submitBtn;
    private EditText userNameEditText;

    public InitializerScreen(Context paramContext) {
        super(paramContext);
        initUI();
        initListener();
    }

    public InitializerScreen(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        initUI();
        initListener();
    }

    private void handleInput() {
        if (StringUtils.isEmpty(this.userNameEditText.getText().toString()))
            return;
        ViewUtils.hideKeyboard(this.userNameEditText);
        Settings.getSettings().saveUserName(this.userNameEditText.getText().toString());
    }

    private void initListener() {
        this.submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                InitializerScreen.this.handleInput();
            }
        });
        this.userNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView paramAnonymousTextView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent) {
                InitializerScreen.this.handleInput();
                return false;
            }
        });
    }

    private void initUI() {
        inflate(getContext(), R.layout.initializer_screen_layout, this);
        this.userNameEditText = ((EditText) findViewById(R.id.initializer_username));
        this.submitBtn = ((ImageView) findViewById(R.id.initializer_submit_btn));
    }

}