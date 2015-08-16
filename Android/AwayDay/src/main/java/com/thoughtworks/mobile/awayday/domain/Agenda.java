package com.thoughtworks.mobile.awayday.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Agenda {
    private Date agendaDate;
    private int agendaId;
    private List<Session> sessions = new ArrayList();

    public void addSession(Session paramSession) {
        this.sessions.add(paramSession);
    }

    public Date getAgendaDate() {
        return this.agendaDate;
    }

    public int getAgendaId() {
        return this.agendaId;
    }

    public Session getSession(int paramInt) {
        return (Session) this.sessions.get(paramInt);
    }

    public Session getSessionById(int paramInt) {
        Iterator localIterator = this.sessions.iterator();
        while (localIterator.hasNext()) {
            Session localSession = (Session) localIterator.next();
            if (localSession.sessionId == paramInt)
                return localSession;
        }
        return null;
    }

    public int getSessionCount() {
        return this.sessions.size();
    }

    public List<Session> getSessions() {
        return this.sessions;
    }

    public void setAgendaDate(Date paramDate) {
        this.agendaDate = paramDate;
    }

    public void setAgendaId(int paramInt) {
        this.agendaId = paramInt;
    }

    public void setSessions(List<Session> paramList) {
        this.sessions = paramList;
    }

    public String toString() {
        return "Agenda Date:" + this.agendaDate + ", sessions count: " + this.sessions.size() + "---{ " + this.sessions.toString() + " }";
    }
}