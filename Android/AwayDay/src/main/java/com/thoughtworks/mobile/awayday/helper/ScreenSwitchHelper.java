package com.thoughtworks.mobile.awayday.helper;

import android.app.FragmentManager;

import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.activity.AgendaFragment;
import com.thoughtworks.mobile.awayday.activity.LessonTwoFragment;
import com.thoughtworks.mobile.awayday.activity.MainActivity;
import com.thoughtworks.mobile.awayday.activity.PathFragment;
import com.thoughtworks.mobile.awayday.activity.SettingFragment;

public class ScreenSwitchHelper {
    private final FragmentManager fragmentManager;
    private LessonTwoFragment agendaFragment;
    private PathFragment pathFragment;
    private SettingFragment settingFragment;

    public ScreenSwitchHelper(MainActivity mainActivity) {
        fragmentManager = mainActivity.getFragmentManager();
//        agendaFragment = new AgendaFragment();
        agendaFragment = new LessonTwoFragment();
        pathFragment = new PathFragment();
        settingFragment = new SettingFragment();
    }

    public void switchToAgendaScreen() {
        fragmentManager.beginTransaction().replace(R.id.screen_container, agendaFragment).commit();
    }

    public void switchToPathScreen() {
        fragmentManager.beginTransaction().replace(R.id.screen_container, pathFragment).commit();
    }

    public void switchToSettingsScreen() {
        fragmentManager.beginTransaction().replace(R.id.screen_container, settingFragment).commit();
    }
}