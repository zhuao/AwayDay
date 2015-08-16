package com.thoughtworks.mobile.awayday.listeners;

import com.thoughtworks.mobile.awayday.domain.Footprint;

import java.util.List;

public abstract interface PathDataChangedListener {
    public abstract void onFetchedFootprints(List<Footprint> paramList);

    public abstract void onFootprintChanged();
}