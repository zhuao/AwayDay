package com.thoughtworks.mobile.awayday.task;

import android.os.AsyncTask;
import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.listeners.OnShareFootprintListener;
import com.thoughtworks.mobile.awayday.service.ShareToRemoteService;
import com.thoughtworks.mobile.awayday.utils.ActionStatus;

public class SharePostTask extends AsyncTask<Footprint, Void, Void> {
    private Footprint footprint;
    private OnShareFootprintListener onShareFootprintListener;
    private ActionStatus shareResult;
    private ShareToRemoteService shareService;

    public SharePostTask(ShareToRemoteService paramShareToRemoteService, OnShareFootprintListener paramOnShareFootprintListener) {
        this.shareService = paramShareToRemoteService;
        this.onShareFootprintListener = paramOnShareFootprintListener;
        onShareFootprintListener.showFullScreenLoading();
    }

    protected Void doInBackground(Footprint[] paramArrayOfFootprint) {
        this.footprint = paramArrayOfFootprint[0];
        this.shareResult = this.shareService.shareToServer(this.footprint);
        return null;
    }

    protected void onPostExecute(Void paramVoid) {
        onShareFootprintListener.dismissLoading();
        if (this.shareResult == null) {
            return;
        }
        this.onShareFootprintListener.onFootprintShared(this.footprint, this.shareResult);

    }
}