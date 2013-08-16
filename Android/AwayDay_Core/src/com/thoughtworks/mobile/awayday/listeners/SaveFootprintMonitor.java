package com.thoughtworks.mobile.awayday.listeners;

import android.content.Context;
import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.domain.Path;
import com.thoughtworks.mobile.awayday.storage.BeanContext;
import com.thoughtworks.mobile.awayday.utils.ActionStatus;

public class SaveFootprintMonitor
        implements OnSaveFootprintListener {
    private Context context;

    public SaveFootprintMonitor(Context paramContext) {
        this.context = paramContext;
    }

    public void onFootprintSaved(Footprint paramFootprint, ActionStatus paramActionStatus) {
        if (paramActionStatus == null)
            return;
        if (paramActionStatus == ActionStatus.SHARE_SUCCESS) {
            ((Path) BeanContext.getInstance().getBean(Path.class)).addFootprint(paramFootprint);
            return;
        }
        paramActionStatus.showMessage(this.context);
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.listeners.SaveFootprintMonitor
 * JD-Core Version:    0.6.2
 */
