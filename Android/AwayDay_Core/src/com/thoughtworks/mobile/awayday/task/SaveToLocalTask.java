package com.thoughtworks.mobile.awayday.task;

import android.os.AsyncTask;
import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.listeners.OnSaveFootprintListener;
import com.thoughtworks.mobile.awayday.service.SaveToLocalService;
import com.thoughtworks.mobile.awayday.utils.ActionStatus;

public class SaveToLocalTask extends AsyncTask<Footprint, Void, Void> {
    private OnSaveFootprintListener OnSaveFootprintListener;
    private ActionStatus actionStatus;
    private SaveToLocalService saveToLocalService;
    private Footprint savedFootprint;

    public SaveToLocalTask(SaveToLocalService paramSaveToLocalService, OnSaveFootprintListener paramOnSaveFootprintListener) {
        this.saveToLocalService = paramSaveToLocalService;
        this.OnSaveFootprintListener = paramOnSaveFootprintListener;
    }

    protected Void doInBackground(Footprint[] paramArrayOfFootprint) {
        if ((paramArrayOfFootprint == null) || (paramArrayOfFootprint.length == 0))
            return null;
        this.savedFootprint = paramArrayOfFootprint[0];
        this.actionStatus = this.saveToLocalService.saveToLocal(this.savedFootprint);
        return null;
    }

    protected void onPostExecute(Void paramVoid) {
        this.OnSaveFootprintListener.onFootprintSaved(this.savedFootprint, this.actionStatus);
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.task.SaveToLocalTask
 * JD-Core Version:    0.6.2
 */
