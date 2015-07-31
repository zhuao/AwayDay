package com.thoughtworks.mobile.awayday.storage;

import com.thoughtworks.mobile.awayday.domain.Agenda;
import com.thoughtworks.mobile.awayday.domain.Footprint;

import java.util.List;

public abstract interface LocalStorage {
    public abstract void addAgendaData(Agenda paramAgenda);

    public abstract void addFootprint(Footprint paramFootprint);

    public abstract void deleteAgendas();

    public abstract void joinSession(int paramInt, boolean paramBoolean);

    public abstract List<Agenda> queryAgendas();

    public abstract List<Footprint> queryTracks();

    public abstract void reminderSession(int paramInt, boolean paramBoolean);
}