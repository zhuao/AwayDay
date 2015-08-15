package com.thoughtworks.mobile.awayday.task;

import android.os.AsyncTask;
import com.thoughtworks.mobile.awayday.service.FetchTrackService;

public class FetchPathTask extends AsyncTask<Void, Void, Void> {
    private final FetchTrackService fetchTrackService;

    public FetchPathTask(FetchTrackService paramFetchTrackService) {
        this.fetchTrackService = paramFetchTrackService;
    }

    protected Void doInBackground(Void[] paramArrayOfVoid) {
        this.fetchTrackService.fetchAllFootprints();
        return null;
    }
}