package com.thoughtworks.mobile.awayday.components;

import android.content.Context;
import android.util.AttributeSet;
import com.thoughtworks.mobile.awayday.helper.ScreenSwitchHelper;
import com.thoughtworks.mobile.awayday.listeners.NavigateCommand;

public class SettingsNavigationMenuItem extends NavigationMenuItem {
    public SettingsNavigationMenuItem(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public NavigateCommand getNavigateAction() {
        return new NavigateToSettingsCommand();
    }

    public boolean isInterceptKeyboardBack() {
        return true;
    }

    private class NavigateToSettingsCommand implements NavigateCommand {
        private NavigateToSettingsCommand() {
        }

        public void execute(ScreenSwitchHelper paramScreenSwitchHelper) {
            paramScreenSwitchHelper.switchToSettingsScreen();
        }
    }
}