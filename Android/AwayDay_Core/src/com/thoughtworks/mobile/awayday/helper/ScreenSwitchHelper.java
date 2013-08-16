package com.thoughtworks.mobile.awayday.helper;

import android.view.View;
import com.thoughtworks.mobile.awayday.domain.Settings;
import com.thoughtworks.mobile.awayday.screen.AgendaScreen;
import com.thoughtworks.mobile.awayday.screen.PathScreen;
import com.thoughtworks.mobile.awayday.screen.SettingScreen;

public class ScreenSwitchHelper {
    private AgendaScreen agendaScreen;
    private PathScreen pathScreen;
    private SettingScreen settingScreen;

    public ScreenSwitchHelper(AgendaScreen paramAgendaScreen, PathScreen paramPathScreen, SettingScreen paramSettingScreen) {
        this.agendaScreen = paramAgendaScreen;
        this.pathScreen = paramPathScreen;
        this.settingScreen = paramSettingScreen;
    }

    public void switchToAgendaScreen() {
        this.agendaScreen.setVisibility(View.VISIBLE);
        this.pathScreen.setVisibility(View.GONE);
        this.settingScreen.setVisibility(View.GONE);
    }

    public void switchToPathScreen() {
        this.agendaScreen.setVisibility(View.GONE);
        this.pathScreen.setVisibility(View.VISIBLE);
        this.settingScreen.setVisibility(View.GONE);
    }

    public void switchToSettingsScreen() {
        this.settingScreen.setUserName(Settings.getSettings().getUserName());
        this.agendaScreen.setVisibility(View.GONE);
        this.pathScreen.setVisibility(View.GONE);
        this.settingScreen.setVisibility(View.VISIBLE);
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.helper.ScreenSwitchHelper
 * JD-Core Version:    0.6.2
 */
