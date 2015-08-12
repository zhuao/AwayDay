package com.thoughtworks.mobile.awayday.presenter;

import com.thoughtworks.mobile.awayday.screen.SettingScreen;

public class SettingsPresenter {
    private SettingScreen settingScreen;

    public SettingsPresenter(SettingScreen paramSettingScreen) {
        this.settingScreen = paramSettingScreen;
    }

    public void initialize(String paramString) {
        this.settingScreen.setUserName(paramString);
    }

}
