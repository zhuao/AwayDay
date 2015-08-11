package com.thoughtworks.mobile.awayday.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.components.NavigationMenu;
import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.domain.Path;
import com.thoughtworks.mobile.awayday.domain.Session;
import com.thoughtworks.mobile.awayday.domain.Settings;
import com.thoughtworks.mobile.awayday.factory.AgendaItemBuilder;
import com.thoughtworks.mobile.awayday.factory.BitmapCacheManager;
import com.thoughtworks.mobile.awayday.factory.PathItemBuilder;
import com.thoughtworks.mobile.awayday.factory.TaskProvider;
import com.thoughtworks.mobile.awayday.helper.RemindAlarmHelper;
import com.thoughtworks.mobile.awayday.helper.ScreenSwitchHelper;
import com.thoughtworks.mobile.awayday.listeners.AgendaItemActionClickedListener;
import com.thoughtworks.mobile.awayday.listeners.OnPathAddActionListener;
import com.thoughtworks.mobile.awayday.listeners.OnRemindSetListener;
import com.thoughtworks.mobile.awayday.listeners.OnSessionJoinedListener;
import com.thoughtworks.mobile.awayday.presenter.AgendaPresenter;
import com.thoughtworks.mobile.awayday.presenter.PathPresenter;
import com.thoughtworks.mobile.awayday.presenter.SettingsPresenter;
import com.thoughtworks.mobile.awayday.screen.AgendaScreen;
import com.thoughtworks.mobile.awayday.screen.PathScreen;
import com.thoughtworks.mobile.awayday.screen.SettingScreen;
import com.thoughtworks.mobile.awayday.service.JoinService;
import com.thoughtworks.mobile.awayday.service.implement.FetchTrackServiceImpl;
import com.thoughtworks.mobile.awayday.storage.BeanContext;
import com.thoughtworks.mobile.awayday.storage.LocalStorage;

public class MainActivity extends Activity {
    private AgendaScreen agendaScreen;
    private NavigationMenu navigationMenu;
    private ScreenSwitchHelper screenSwitchHelper;

    private void initComponent() {
        AgendaFragment agendaFragment = new AgendaFragment();
        PathFragment pathFragment = new PathFragment();
        SettingFragment settingFragment = new SettingFragment();

        initNavigation(agendaFragment, pathFragment, settingFragment);
    }

    private void initNavigation(AgendaFragment agendaFragment, PathFragment pathFragment, SettingFragment settingFragment) {
        this.screenSwitchHelper = new ScreenSwitchHelper(this, agendaFragment, pathFragment, settingFragment);
        this.navigationMenu.setScreenSwitchHelper(this.screenSwitchHelper);
    }

    private void initUI() {
        setContentView(R.layout.main_layout);
        this.navigationMenu = ((NavigationMenu) findViewById(R.id.navigation_menu));
    }

    public void onBackPressed() {
        if (this.navigationMenu.isInterceptKeyboardBack()) {
            this.navigationMenu.onKeyboardBack();
            return;
        }
        super.onBackPressed();
    }

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        initUI();
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