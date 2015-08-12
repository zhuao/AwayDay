package com.thoughtworks.mobile.awayday.helper;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.activity.AgendaFragment;
import com.thoughtworks.mobile.awayday.activity.MainActivity;
import com.thoughtworks.mobile.awayday.activity.PathFragment;
import com.thoughtworks.mobile.awayday.activity.SettingFragment;

public class ScreenSwitchHelper {
    private final AgendaFragment agendaFragement;
    private final FragmentManager fragmentManager;
    private PathFragment pathFragment;
    private SettingFragment settingFragement;

    public ScreenSwitchHelper(MainActivity mainActivity, AgendaFragment agendaFragment, PathFragment pathFragment, SettingFragment settingFragment) {
        fragmentManager = mainActivity.getFragmentManager();
        this.agendaFragement = agendaFragment;
        this.pathFragment = pathFragment;
        this.settingFragement = settingFragment;
    }

    public void switchToAgendaScreen() {
        fragmentManager.popBackStack();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.screen_container, agendaFragement);
        fragmentTransaction.commit();
    }

    public void switchToPathScreen() {
        fragmentManager.popBackStack();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.screen_container, pathFragment);
        fragmentTransaction.commit();
    }

    public void switchToSettingsScreen() {
        fragmentManager.popBackStack();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.screen_container, settingFragement);
        fragmentTransaction.commit();
    }
}