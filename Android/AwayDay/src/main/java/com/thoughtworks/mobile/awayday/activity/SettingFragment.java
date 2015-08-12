package com.thoughtworks.mobile.awayday.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.components.NavigationMenu;
import com.thoughtworks.mobile.awayday.domain.Settings;
import com.thoughtworks.mobile.awayday.presenter.SettingsPresenter;
import com.thoughtworks.mobile.awayday.screen.SettingScreen;

public class SettingFragment extends Fragment {

    private SettingScreen settingScreen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_page, container, false);
        this.settingScreen = ((SettingScreen) view.findViewById(R.id.settings_screen));
        initSettings();
        return view;
    }

    private void initSettings() {
        SettingsPresenter localSettingsPresenter = new SettingsPresenter(this.settingScreen);
        localSettingsPresenter.initialize(Settings.getSettings().getUserName());
    }
}
