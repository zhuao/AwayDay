package com.thoughtworks.mobile.awayday.task;

import android.os.AsyncTask;

import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.domain.Path;
import com.thoughtworks.mobile.awayday.service.FetchTrackService;
import com.thoughtworks.mobile.awayday.storage.BeanContext;

import java.util.List;

public class FetchPathTask extends AsyncTask<Void, Void, List<Footprint>> {
    private final FetchTrackService fetchTrackService;

    public FetchPathTask(FetchTrackService paramFetchTrackService) {
        this.fetchTrackService = paramFetchTrackService;
    }

    protected List<Footprint> doInBackground(Void... paramArrayOfVoid) {
        return fetchTrackService.fetchAllFootprints();
    }

    @Override
    protected void onPostExecute(List<Footprint> footprintList) {
        ((Path) BeanContext.getInstance().getBean(Path.class)).onFetchedFootprints(footprintList);
    }
}