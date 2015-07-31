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

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.task.FetchPathTask
 * JD-Core Version:    0.6.2
 */
