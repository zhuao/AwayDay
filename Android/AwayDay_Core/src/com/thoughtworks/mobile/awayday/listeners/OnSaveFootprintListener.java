package com.thoughtworks.mobile.awayday.listeners;

import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.utils.ActionStatus;

public abstract interface OnSaveFootprintListener {
    public abstract void onFootprintSaved(Footprint paramFootprint, ActionStatus paramActionStatus);
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.listeners.OnSaveFootprintListener
 * JD-Core Version:    0.6.2
 */
