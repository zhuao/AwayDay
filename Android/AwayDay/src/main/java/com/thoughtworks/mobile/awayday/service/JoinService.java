package com.thoughtworks.mobile.awayday.service;

import com.thoughtworks.mobile.awayday.domain.Session;
import com.thoughtworks.mobile.awayday.utils.ActionStatus;

public abstract interface JoinService {
    public abstract ActionStatus handleSession(Session paramSession);
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.service.JoinService
 * JD-Core Version:    0.6.2
 */
