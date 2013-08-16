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
    }

    protected Void doInBackground(Footprint[] paramArrayOfFootprint) {
        this.footprint = paramArrayOfFootprint[0];
        this.shareResult = this.shareService.shareToServer(this.footprint);
        return null;
    }

    protected void onPostExecute(Void paramVoid) {
        if (this.shareResult == null)
            return;
        this.onShareFootprintListener.onFootprintShared(this.footprint, this.shareResult);
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.task.SharePostTask
 * JD-Core Version:    0.6.2
 */
