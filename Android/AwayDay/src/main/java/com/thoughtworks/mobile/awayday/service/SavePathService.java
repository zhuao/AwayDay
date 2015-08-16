package com.thoughtworks.mobile.awayday.service;

import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.utils.ActionStatus;

public abstract interface SavePathService {
    public abstract ActionStatus saveToLocal(Footprint paramFootprint);
}