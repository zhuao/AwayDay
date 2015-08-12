package com.thoughtworks.mobile.awayday.listeners;

import com.thoughtworks.mobile.awayday.helper.ScreenSwitchHelper;

public abstract interface NavigateCommand {
    public abstract void execute(ScreenSwitchHelper paramScreenSwitchHelper);
}