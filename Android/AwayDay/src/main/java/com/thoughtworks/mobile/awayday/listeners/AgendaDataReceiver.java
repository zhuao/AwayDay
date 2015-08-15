package com.thoughtworks.mobile.awayday.listeners;

import com.thoughtworks.mobile.awayday.domain.Agenda;

import java.util.List;

public abstract interface AgendaDataReceiver {
    public abstract void onReceivedAgenda(List<Agenda> paramList);
}