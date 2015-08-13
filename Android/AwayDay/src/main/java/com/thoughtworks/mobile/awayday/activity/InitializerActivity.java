package com.thoughtworks.mobile.awayday.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.domain.Settings;
import com.thoughtworks.mobile.awayday.factory.BeanRegister;
import com.thoughtworks.mobile.awayday.screen.InitializerScreen;
import com.thoughtworks.mobile.awayday.utils.StringUtils;

public class InitializerActivity extends AppCompatActivity {
    private InitializerScreen initializerScreen;
    private ImageView logoScreen;

    private void hideSplash() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                InitializerActivity.this.switchToInitializeScreen();
            }
        }, 900L);
    }

    private void initUI() {
        setContentView(R.layout.initializer_layout);
        initializerScreen = ((InitializerScreen) findViewById(R.id.initializer_screen));
        logoScreen = ((ImageView) findViewById(R.id.app_screen_logo));
    }

    private void showLogoScreen() {
        Animation localAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_out);
        localAnimation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation paramAnonymousAnimation) {
                InitializerActivity.this.hideSplash();
            }

            public void onAnimationRepeat(Animation paramAnonymousAnimation) {
            }

            public void onAnimationStart(Animation paramAnonymousAnimation) {
            }
        });
        logoScreen.startAnimation(localAnimation);
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void switchToInitializeScreen() {
        logoScreen.setVisibility(View.GONE);
        initializerScreen.setVisibility(View.VISIBLE);
    }

    private boolean willLeaveSettingsScreen(Settings paramSettings) {
        return !StringUtils.isEmpty(paramSettings.getUserName());
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        initUI();
        new BeanRegister(getApplicationContext()).onRegister();
    }

    protected void onPostCreate(Bundle paramBundle) {
        super.onPostCreate(paramBundle);
        if (willLeaveSettingsScreen(Settings.getSettings())) {
            startMainActivity();
            return;
        }
        showLogoScreen();
    }

}