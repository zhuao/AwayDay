package com.thoughtworks.mobile.awayday.presenter;

import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.domain.Settings;
import com.thoughtworks.mobile.awayday.factory.TaskProvider;
import com.thoughtworks.mobile.awayday.helper.FootprintComparator;
import com.thoughtworks.mobile.awayday.listeners.OnSettingsChangedListener;
import com.thoughtworks.mobile.awayday.listeners.PathDataChangedListener;
import com.thoughtworks.mobile.awayday.screen.PathScreen;
import com.thoughtworks.mobile.awayday.service.FetchTrackService;

import java.util.Collections;
import java.util.List;

public class PathPresenter implements OnSettingsChangedListener, PathDataChangedListener {
    private FetchTrackService fetchTrackService;
    private final PathScreen pathScreen;

    public PathPresenter(PathScreen paramPathScreen) {
        this.pathScreen = paramPathScreen;
    }

    private void startFetchTask() {
        TaskProvider.createFetchPathTask(this.fetchTrackService).execute(new Void[0]);
    }

    private void updatePathScreen(List<Footprint> paramList) {
        Collections.sort(paramList, new FootprintComparator());
        this.pathScreen.onPathFetched(paramList);
    }

    public void loadFootprints(FetchTrackService paramFetchTrackService) {
        this.fetchTrackService = paramFetchTrackService;
        startFetchTask();
    }

    public void onFetchedFootprints(List<Footprint> paramList) {
        if (paramList == null)
            return;
        updatePathScreen(paramList);
    }

    public void onFootprintChanged() {
        this.pathScreen.updateOnPathChanged();
    }

    public void onSettingsChanged() {
        this.pathScreen.setUserName(Settings.getSettings().getUserName());
    }
}