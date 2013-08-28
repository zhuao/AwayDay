package com.thoughtworks.mobile.awayday.domain;

import com.thoughtworks.mobile.awayday.factory.TaskProvider;
import com.thoughtworks.mobile.awayday.listeners.OnSaveFootprintListener;
import com.thoughtworks.mobile.awayday.listeners.OnShareFootprintListener;
import com.thoughtworks.mobile.awayday.listeners.PathDataChangedListener;
import com.thoughtworks.mobile.awayday.service.SaveToLocalService;
import com.thoughtworks.mobile.awayday.service.ShareToRemoteService;
import com.thoughtworks.mobile.awayday.storage.BeanContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Path {
    private List<Footprint> footprintList = new ArrayList();
    private List<PathDataChangedListener> pathDataChangedListeners = new ArrayList();

    private void notifyFootprintsFetched() {
        Iterator localIterator = this.pathDataChangedListeners.iterator();
        while (localIterator.hasNext()) {
            PathDataChangedListener localPathDataChangedListener = (PathDataChangedListener) localIterator.next();
            if (localPathDataChangedListener == null)
                this.pathDataChangedListeners.remove(localPathDataChangedListener);
            else
                localPathDataChangedListener.onFetchedFootprints(this.footprintList);
        }
    }

    private void notifyNewFootprintAdded() {
        Iterator localIterator = this.pathDataChangedListeners.iterator();
        while (localIterator.hasNext()) {
            PathDataChangedListener localPathDataChangedListener = (PathDataChangedListener) localIterator.next();
            if (localPathDataChangedListener == null)
                this.pathDataChangedListeners.remove(localPathDataChangedListener);
            else
                localPathDataChangedListener.onFootprintChanged();
        }
    }

    public void saveToLocal(Footprint paramFootprint) {
        TaskProvider.createSaveToLocalTask((SaveToLocalService) BeanContext.getInstance().getBean(SaveToLocalService.class), (OnSaveFootprintListener) BeanContext.getInstance().getBean(OnSaveFootprintListener.class)).execute(new Footprint[]{paramFootprint});
    }

    private void shareToServer(Footprint paramFootprint) {
        TaskProvider.createSharePostTask((ShareToRemoteService) BeanContext.getInstance().getBean(ShareToRemoteService.class), (OnShareFootprintListener) BeanContext.getInstance().getBean(OnShareFootprintListener.class)).execute(new Footprint[]{paramFootprint});
    }

    public void addFootprint(Footprint paramFootprint) {
        this.footprintList.add(paramFootprint);
        notifyNewFootprintAdded();
    }

    public void addPathDataChangedListener(PathDataChangedListener paramPathDataChangedListener) {
        this.pathDataChangedListeners.add(paramPathDataChangedListener);
    }

    public void onFetchedFootprints(List<Footprint> paramList) {
        this.footprintList = paramList;
        notifyFootprintsFetched();
    }

    public void shareFootprint(Footprint paramFootprint) {
        if (!paramFootprint.isValid())
            return;
        saveToLocal(paramFootprint);
        shareToServer(paramFootprint);
    }

}