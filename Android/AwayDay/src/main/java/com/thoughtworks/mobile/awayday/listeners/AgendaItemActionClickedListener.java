package com.thoughtworks.mobile.awayday.listeners;

import com.thoughtworks.mobile.awayday.domain.Session;

public abstract interface AgendaItemActionClickedListener {
    public abstract void onJoinActionClicked(OnSessionJoinedListener paramOnSessionJoinedListener, Session paramSession);

    public abstract void onRemindActionClicked(OnRemindSetListener paramOnRemindSetListener, Session paramSession);

    public abstract void onShareActionClicked(Session paramSession);
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.listeners.AgendaItemActionClickedListener
 * JD-Core Version:    0.6.2
 */
