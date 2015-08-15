package com.thoughtworks.mobile.awayday.service;

import com.thoughtworks.mobile.awayday.domain.Agenda;

import java.util.List;

public abstract interface UpdateAgendaService {
    public abstract List<Agenda> updateAgendas();
}