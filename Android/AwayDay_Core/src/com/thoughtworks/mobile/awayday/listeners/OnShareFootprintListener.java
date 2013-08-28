package com.thoughtworks.mobile.awayday.listeners;

import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.utils.ActionStatus;
import com.weibo.sdk.android.net.RequestListener;

public abstract interface OnShareFootprintListener extends RequestListener {
    public abstract void showFullScreenLoading();
    public abstract void dismissLoading();
    public abstract void onFootprintShared(Footprint paramFootprint, ActionStatus paramActionStatus);
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.listeners.OnShareFootprintListener
 * JD-Core Version:    0.6.2
 */
