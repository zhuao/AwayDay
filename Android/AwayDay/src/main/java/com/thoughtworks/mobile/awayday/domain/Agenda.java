package com.thoughtworks.mobile.awayday.domain;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class Agenda {
    @SerializedName("agenda_date")
    private Date agendaDate;

    private int agendaId;
    @SerializedName("agenda_sessions")
    private List<Session> sessions = new ArrayList();

    public static List<Agenda> parseAgendas(Reader agendasJson) {

        TypeAdapter<Date> dateTypeAdapter = new DateTypeAdapter();
        List<Agenda> agendas = (List<Agenda>) new GsonBuilder().registerTypeAdapter(Date.class, dateTypeAdapter).create().fromJson(agendasJson, new TypeToken<List<Agenda>>() {
        }.getType());
        int agendaIndex = 1;
        for (Agenda agenda : agendas) {
            agenda.setAgendaId(agendaIndex);
            for (Session session : agenda.getSessions()) {
                session.agendaId = agendaIndex;
            }
            agendaIndex++;
        }
        return agendas;
    }

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

    public static class DateTypeAdapter extends TypeAdapter<Date> {

        private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        private static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
        private static Pattern DATE_PATTERN = Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}$");


        @Override
        public void write(JsonWriter out, Date value) throws IOException {
            out.value(DATE_TIME_FORMAT.format(value));
        }

        @Override
        public Date read(JsonReader in) throws IOException {
            String s = in.nextString();
            try {
                if (DATE_PATTERN.matcher(s).find()) {
                    return DATE_FORMAT.parse(s);
                } else {
                    return DATE_TIME_FORMAT.parse(s);
                }
            } catch (ParseException e) {
                throw new IOException(e);
            }
        }
    }
}
