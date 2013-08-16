package com.thoughtworks.mobile.awayday.service;

import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.utils.ActionStatus;

public abstract interface SaveToLocalService {
    public abstract ActionStatus saveToLocal(Footprint paramFootprint);
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.service.SaveToLocalService
 * JD-Core Version:    0.6.2
 */
