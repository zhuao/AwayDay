package com.thoughtworks.mobile.awayday.listeners;

import android.content.Context;
import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.utils.ActionStatus;

public class ShareFootprintMonitor
        implements OnShareFootprintListener {
    private Context context;

    public ShareFootprintMonitor(Context paramContext) {
        this.context = paramContext;
    }

    public void onFootprintShared(Footprint paramFootprint, ActionStatus paramActionStatus) {
        if (paramActionStatus == null) ;
        while (paramActionStatus == ActionStatus.SHARE_SUCCESS)
            return;
        paramActionStatus.showMessage(this.context);
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.listeners.ShareFootprintMonitor
 * JD-Core Version:    0.6.2
 */
