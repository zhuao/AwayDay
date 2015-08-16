package com.thoughtworks.mobile.awayday.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.components.NavigationMenu;
import com.thoughtworks.mobile.awayday.factory.BitmapCacheManager;
import com.thoughtworks.mobile.awayday.helper.ScreenSwitchHelper;
import com.thoughtworks.mobile.awayday.listeners.NavigateCommand;
import com.thoughtworks.mobile.awayday.listeners.NavigationListener;

public class MainActivity extends AppCompatActivity {
    private NavigationMenu navigationMenu;
    private ScreenSwitchHelper screenSwitchHelper;

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.main_layout);
        this.navigationMenu = ((NavigationMenu) findViewById(R.id.navigation_menu));
    }

    private void initComponent() {
        initNavigation();

        screenSwitchHelper.switchToAgendaScreen();
    }

    private void initNavigation() {
        this.screenSwitchHelper = new ScreenSwitchHelper(this);
        this.navigationMenu.setNavigationListener(new NavigationListener() {
            @Override
            public void switchTo(NavigateCommand navigateCommand) {
                navigateCommand.execute(screenSwitchHelper);
            }
        });
    }

    public void onBackPressed() {
        if (this.navigationMenu.isInterceptKeyboardBack()) {
            this.navigationMenu.onKeyboardBack();
            return;
        }
        super.onBackPressed();
    }


    public void onLowMemory() {
        super.onLowMemory();
        BitmapCacheManager.getInstance().clearCache();
    }

    protected void onPostCreate(Bundle paramBundle) {
        super.onPostCreate(paramBundle);
        initComponent();
    }

}