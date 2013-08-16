package com.thoughtworks.mobile.awayday.listeners;

import com.thoughtworks.mobile.awayday.domain.Agenda;

import java.util.List;

public abstract interface AgendaDataReceiver {
    public abstract void onReceivedAgenda(List<Agenda> paramList);
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.listeners.AgendaDataReceiver
 * JD-Core Version:    0.6.2
 */
