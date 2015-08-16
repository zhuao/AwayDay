package com.thoughtworks.mobile.awayday.task;

import android.os.AsyncTask;
import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.listeners.OnSaveFootprintListener;
import com.thoughtworks.mobile.awayday.service.SavePathService;
import com.thoughtworks.mobile.awayday.utils.ActionStatus;

public class SavePathTask extends AsyncTask<Footprint, Void, Void> {
    private OnSaveFootprintListener OnSaveFootprintListener;
    private ActionStatus actionStatus;
    private SavePathService savePathService;
    private Footprint savedFootprint;

    public SavePathTask(SavePathService paramSavePathService, OnSaveFootprintListener paramOnSaveFootprintListener) {
        this.savePathService = paramSavePathService;
        this.OnSaveFootprintListener = paramOnSaveFootprintListener;
    }

    protected Void doInBackground(Footprint[] paramArrayOfFootprint) {
        if ((paramArrayOfFootprint == null) || (paramArrayOfFootprint.length == 0))
            return null;
        this.savedFootprint = paramArrayOfFootprint[0];
        this.actionStatus = this.savePathService.saveToLocal(this.savedFootprint);
        return null;
    }

    protected void onPostExecute(Void paramVoid) {
        this.OnSaveFootprintListener.onFootprintSaved(this.savedFootprint, this.actionStatus);
    }
}