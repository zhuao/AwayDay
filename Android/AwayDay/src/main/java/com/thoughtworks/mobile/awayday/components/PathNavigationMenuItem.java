package com.thoughtworks.mobile.awayday.components;

import android.content.Context;
import android.util.AttributeSet;
import com.thoughtworks.mobile.awayday.helper.ScreenSwitchHelper;
import com.thoughtworks.mobile.awayday.listeners.NavigateCommand;

public class PathNavigationMenuItem extends NavigationMenuItem {
    public PathNavigationMenuItem(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public NavigateCommand getNavigateAction() {
        return new NavigateToPathCommand();
    }

    public boolean isInterceptKeyboardBack() {
        return true;
    }

    private class NavigateToPathCommand
            implements NavigateCommand {
        private NavigateToPathCommand() {
        }

        public void execute(ScreenSwitchHelper paramScreenSwitchHelper) {
            paramScreenSwitchHelper.switchToPathScreen();
        }
    }
}