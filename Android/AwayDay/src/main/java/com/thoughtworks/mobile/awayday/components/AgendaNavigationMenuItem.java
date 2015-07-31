package com.thoughtworks.mobile.awayday.components;

import android.content.Context;
import android.util.AttributeSet;
import com.thoughtworks.mobile.awayday.helper.ScreenSwitchHelper;
import com.thoughtworks.mobile.awayday.listeners.NavigateCommand;

public class AgendaNavigationMenuItem extends NavigationMenuItem {
    public AgendaNavigationMenuItem(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public NavigateCommand getNavigateAction() {
        return new NavigateToAgendaCommand();
    }

    public boolean isInterceptKeyboardBack() {
        return false;
    }

    private class NavigateToAgendaCommand
            implements NavigateCommand {
        private NavigateToAgendaCommand() {
        }

        public void execute(ScreenSwitchHelper paramScreenSwitchHelper) {
            paramScreenSwitchHelper.switchToAgendaScreen();
        }
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.components.AgendaNavigationMenuItem
 * JD-Core Version:    0.6.2
 */
