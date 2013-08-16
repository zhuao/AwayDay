package com.thoughtworks.mobile.awayday.listeners;

import com.thoughtworks.mobile.awayday.domain.Footprint;

import java.util.List;

public abstract interface PathDataChangedListener {
    public abstract void onFetchedFootprints(List<Footprint> paramList);

    public abstract void onFootprintChanged();
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.listeners.PathDataChangedListener
 * JD-Core Version:    0.6.2
 */
