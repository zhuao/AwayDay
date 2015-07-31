package com.thoughtworks.mobile.awayday.presenter;

import com.thoughtworks.mobile.awayday.components.NavigationMenu;
import com.thoughtworks.mobile.awayday.listeners.OnSettingsModifyActionListener;
import com.thoughtworks.mobile.awayday.screen.SettingScreen;

public class SettingsPresenter
        implements OnSettingsModifyActionListener {
    private NavigationMenu navigationMenu;
    private SettingScreen settingScreen;

    public SettingsPresenter(SettingScreen paramSettingScreen, NavigationMenu paramNavigationMenu) {
        this.settingScreen = paramSettingScreen;
        this.navigationMenu = paramNavigationMenu;
    }

    public void initialize(String paramString) {
        this.settingScreen.setUserName(paramString);
    }

    public void onSettingsModifyAction() {
        this.navigationMenu.activeLastItem();
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.presenter.SettingsPresenter
 * JD-Core Version:    0.6.2
 */
