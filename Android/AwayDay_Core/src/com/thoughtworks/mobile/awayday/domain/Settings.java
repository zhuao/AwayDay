package com.thoughtworks.mobile.awayday.domain;

import com.thoughtworks.mobile.awayday.listeners.OnSettingsChangedListener;
import com.thoughtworks.mobile.awayday.storage.BeanContext;
import com.thoughtworks.mobile.awayday.storage.PreferencesStorage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Settings {
    private static Settings settings;
    private List<OnSettingsChangedListener> onSettingsChangedListenerList = new ArrayList();
    private String userName;

    private Settings() {
        loadFromPreference(getLocalStorage());
    }

    private PreferencesStorage getLocalStorage() {
        return (PreferencesStorage) BeanContext.getInstance().getBean(PreferencesStorage.class);
    }

    public static Settings getSettings() {
        if (settings == null)
            settings = new Settings();
        return settings;
    }

    private void loadFromPreference(PreferencesStorage paramPreferencesStorage) {
        this.userName = paramPreferencesStorage.getUserName();
    }

    private void notifyAllListeners() {
        Iterator localIterator = this.onSettingsChangedListenerList.iterator();
        while (localIterator.hasNext()) {
            OnSettingsChangedListener localOnSettingsChangedListener = (OnSettingsChangedListener) localIterator.next();
            if (localOnSettingsChangedListener == null)
                this.onSettingsChangedListenerList.remove(localOnSettingsChangedListener);
            else
                localOnSettingsChangedListener.onSettingsChanged();
        }
    }

    public void addListener(OnSettingsChangedListener paramOnSettingsChangedListener) {
        this.onSettingsChangedListenerList.add(paramOnSettingsChangedListener);
    }

    public String getUserName() {
        if (this.userName == null)
            this.userName = getLocalStorage().getUserName();
        return this.userName;
    }

    public void saveUserName(String paramString) {
        this.userName = paramString;
        getLocalStorage().setUserName(paramString);
        notifyAllListeners();
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.domain.Settings
 * JD-Core Version:    0.6.2
 */
