package com.thoughtworks.mobile.awayday.service.implement;

import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.service.FetchTrackService;
import com.thoughtworks.mobile.awayday.storage.BeanContext;
import com.thoughtworks.mobile.awayday.storage.LocalStorage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FetchTrackServiceImpl implements FetchTrackService {
    public List<Footprint> fetchAllFootprints() {
        List<Footprint> localArrayList = new ArrayList();
        Iterator localIterator = ((LocalStorage) BeanContext.getInstance().getBean(LocalStorage.class)).queryTracks().iterator();
        while (localIterator.hasNext()) {
            Footprint localFootprint1 = (Footprint) localIterator.next();
            Footprint localFootprint2 = new Footprint();
            localFootprint2.content = localFootprint1.content;
            localFootprint2.createdDate = localFootprint1.createdDate;
            localFootprint2.imageUriString = localFootprint1.imageUriString;
            localArrayList.add(localFootprint2);
        }
        return localArrayList;
    }
}