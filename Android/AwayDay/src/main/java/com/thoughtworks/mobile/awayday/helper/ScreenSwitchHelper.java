package com.thoughtworks.mobile.awayday.helper;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.activity.AgendaFragment;
import com.thoughtworks.mobile.awayday.activity.MainActivity;
import com.thoughtworks.mobile.awayday.activity.PathFragment;
import com.thoughtworks.mobile.awayday.activity.SettingFragment;

public class ScreenSwitchHelper {
    private final FragmentManager fragmentManager;

    public ScreenSwitchHelper(MainActivity mainActivity) {
        fragmentManager = mainActivity.getFragmentManager();
    }

    public void switchToAgendaScreen() {
        fragmentManager.beginTransaction().replace(R.id.screen_container, new AgendaFragment()).commit();
    }

    public void switchToPathScreen() {
        fragmentManager.beginTransaction().replace(R.id.screen_container, new PathFragment()).commit();
    }

    public void switchToSettingsScreen() {
        fragmentManager.beginTransaction().replace(R.id.screen_container, new SettingFragment()).commit();
    }
}