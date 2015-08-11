package com.thoughtworks.mobile.awayday.listeners;

import com.thoughtworks.mobile.awayday.domain.Session;

public abstract interface AgendaItemActionClickedListener {
    public abstract void onJoinActionClicked(OnSessionJoinedListener paramOnSessionJoinedListener, Session paramSession);

    public abstract void onRemindActionClicked(OnRemindSetListener paramOnRemindSetListener, Session paramSession);

    public abstract void onShareActionClicked(Session paramSession);
}