package com.thoughtworks.mobile.awayday.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.thoughtworks.mobile.awayday.domain.Agenda;
import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.domain.Session;
import com.thoughtworks.mobile.awayday.utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SqliteStorage implements LocalStorage {
    private SQLiteHelper sqLiteHelper;

    public SqliteStorage(Context paramContext) {
        sqLiteHelper = new SQLiteHelper(paramContext);
    }

    private void addAgenda(SQLiteDatabase paramSQLiteDatabase, Agenda paramAgenda) {
        paramSQLiteDatabase.beginTransaction();
        try {
            Object[] arrayOfObject = new Object[2];
            arrayOfObject[0] = Integer.valueOf(paramAgenda.getAgendaId());
            arrayOfObject[1] = StringUtils.formatAgendaDateToStringForDb(paramAgenda.getAgendaDate());
            paramSQLiteDatabase.execSQL("insert into agendas values(?,?)", arrayOfObject);
            paramSQLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            paramSQLiteDatabase.endTransaction();
        }
    }

    private void addSessions(SQLiteDatabase paramSQLiteDatabase, List<Session> paramList) {
        paramSQLiteDatabase.beginTransaction();
        try {
            Iterator localIterator = paramList.iterator();
            while (localIterator.hasNext()) {
                Session localSession = (Session) localIterator.next();
                Object[] arrayOfObject = new Object[8];
                arrayOfObject[0] = Integer.valueOf(localSession.sessionId);
                arrayOfObject[1] = localSession.sessionTitle;
                arrayOfObject[2] = localSession.sessionSpeaker;
                arrayOfObject[3] = localSession.sessionNote;
                arrayOfObject[4] = localSession.sessionLocation;
                arrayOfObject[5] = StringUtils.formatSessionDateToStringForDB(localSession.sessionStartTime);
                arrayOfObject[6] = StringUtils.formatSessionDateToStringForDB(localSession.sessionEndTime);
                arrayOfObject[7] = Integer.valueOf(localSession.agendaId);
                paramSQLiteDatabase.execSQL("insert into sessions values(?,?,?,?,?,?,?,?)", arrayOfObject);
            }
            paramSQLiteDatabase.setTransactionSuccessful();
        } finally {
            paramSQLiteDatabase.endTransaction();
        }
    }

    private void addSessionsToAgenda(List<Agenda> paramList) {
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext()) {
            Agenda localAgenda = (Agenda) localIterator.next();
            localAgenda.setSessions(getSessionsByAgendaId(localAgenda.getAgendaId()));
        }
    }

    private void addStatusToSessions(List<Session> paramList) {
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext()) {
            setSessionStatus((Session) localIterator.next());
        }
    }

    private SQLiteDatabase getReadableDatabase() {
        return this.sqLiteHelper.getReadableDatabase();
    }

    private List<Session> getSessionsByAgendaId(int paramInt) {
        List localList = getSessionsFromDbByAgendaId(paramInt);
        addStatusToSessions(localList);
        return localList;
    }

    private List<Session> getSessionsFromDbByAgendaId(int paramInt) {
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        ArrayList localArrayList = new ArrayList();
        String[] arrayOfString = new String[1];
        arrayOfString[0] = (paramInt + "");
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT * FROM sessions where agenda_id = ?", arrayOfString);
        while (localCursor.moveToNext()) {
            Session localSession = new Session();
            localSession.sessionId = localCursor.getInt(localCursor.getColumnIndex("session_id"));
            localSession.sessionTitle = localCursor.getString(localCursor.getColumnIndex("session_title"));
            localSession.sessionSpeaker = localCursor.getString(localCursor.getColumnIndex("session_speaker"));
            localSession.sessionNote = localCursor.getString(localCursor.getColumnIndex("session_note"));
            localSession.sessionLocation = localCursor.getString(localCursor.getColumnIndex("session_location"));
            localSession.sessionStartTime = StringUtils.parseSessionDateToString(localCursor.getString(localCursor.getColumnIndex("session_start_time")));
            localSession.sessionEndTime = StringUtils.parseSessionDateToString(localCursor.getString(localCursor.getColumnIndex("session_end_time")));
            localSession.agendaId = paramInt;
            localArrayList.add(localSession);
        }
        localCursor.close();
        return localArrayList;
    }

    private SQLiteDatabase getWritableDatabase() {
        return this.sqLiteHelper.getWritableDatabase();
    }

    private List<Agenda> queryAgendaFromDB() {
        ArrayList localArrayList = new ArrayList();
        Cursor localCursor = getReadableDatabase().rawQuery("SELECT * FROM agendas", null);
        while (localCursor.moveToNext()) {
            Agenda localAgenda = new Agenda();
            localAgenda.setAgendaId(localCursor.getInt(localCursor.getColumnIndex("agenda_id")));
            localAgenda.setAgendaDate(StringUtils.parseAgendaDate(localCursor.getString(localCursor.getColumnIndex("agenda_date"))));
            localArrayList.add(localAgenda);
        }
        localCursor.close();
        return localArrayList;
    }

    private void setSessionStatus(Session paramSession) {
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        String[] arrayOfString = new String[1];
        arrayOfString[0] = (paramSession.sessionId + "");
        Cursor localCursor = localSQLiteDatabase.rawQuery("select * from session_statuses where session_id = ?", arrayOfString);
        while (localCursor.moveToNext()) {
            paramSession.hasJoined = StringUtils.parseToBoolean(localCursor.getString(localCursor.getColumnIndex("has_joined")));
            paramSession.hasReminder = StringUtils.parseToBoolean(localCursor.getString(localCursor.getColumnIndex("has_reminder")));
        }
        localCursor.close();
    }

    public void addAgendaData(Agenda paramAgenda) {
        try {
            SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
            addAgenda(localSQLiteDatabase, paramAgenda);
            List<Session> localList = paramAgenda.getSessions();
            addStatusToSessions(localList);
            addSessions(localSQLiteDatabase, localList);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        finally {
//            this.sqLiteHelper.close();
//        }
    }

    public void addFootprint(Footprint paramFootprint) {
    }

    public void deleteAgendas() {
        SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
        try {
            localSQLiteDatabase.beginTransaction();
            localSQLiteDatabase.delete("sessions", null, null);
            localSQLiteDatabase.delete("agendas", null, null);
            localSQLiteDatabase.setTransactionSuccessful();
        } finally {
            localSQLiteDatabase.endTransaction();
            this.sqLiteHelper.close();
        }
    }

    public void joinSession(int paramInt, boolean paramBoolean) {
        try {
            SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
            String[] arrayOfString1 = new String[1];
            arrayOfString1[0] = (paramInt + "");
            Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT * FROM session_statuses where session_id = ?", arrayOfString1);
            if (localCursor.getCount() != 0) {
                ContentValues localContentValues = new ContentValues();
                localContentValues.put("has_joined", Boolean.valueOf(paramBoolean));
                String[] arrayOfString2 = new String[1];
                arrayOfString2[0] = (paramInt + "");
                localSQLiteDatabase.update("session_statuses", localContentValues, "session_id = ?", arrayOfString2);
            } else {
                Object[] arrayOfObject = new Object[3];
                arrayOfObject[0] = Integer.valueOf(paramInt);
                arrayOfObject[1] = Boolean.valueOf(paramBoolean);
                arrayOfObject[2] = Boolean.valueOf(false);
                localSQLiteDatabase.execSQL("insert into session_statuses values(?,?,?)", arrayOfObject);
            }
            localCursor.close();
        } finally {
            this.sqLiteHelper.close();
        }
    }

    public List<Agenda> queryAgendas() {
        List localList = queryAgendaFromDB();
        addSessionsToAgenda(localList);
        return localList;
    }

    public List<Footprint> queryTracks() {
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        ArrayList localArrayList = new ArrayList();
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT * FROM tracks", null);
        while (localCursor.moveToNext()) {
            Footprint localFootprint = new Footprint();
            localFootprint.id = localCursor.getInt(localCursor.getColumnIndex("track_id"));
            localFootprint.content = localCursor.getString(localCursor.getColumnIndex("message"));
            localFootprint.imageUriString = localCursor.getString(localCursor.getColumnIndex("picture_path"));
            localFootprint.createdDate = StringUtils.parseAgendaDate(localCursor.getString(localCursor.getColumnIndex("track_time")));
            localArrayList.add(localFootprint);
        }
        localCursor.close();
        return localArrayList;
    }

    public void reminderSession(int paramInt, boolean paramBoolean) {
        try {
            SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
            String[] arrayOfString = new String[1];
            arrayOfString[0] = (paramInt + "");
            Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT * FROM session_statuses where session_id = ?", arrayOfString);
            if (localCursor.getCount() != 0) {
                ContentValues localContentValues = new ContentValues();
                localContentValues.put("has_reminder", Boolean.valueOf(paramBoolean));
                localSQLiteDatabase.update("session_statuses", localContentValues, "session_id = ?", arrayOfString);
            } else {
                Object[] arrayOfObject = new Object[3];
                arrayOfObject[0] = Integer.valueOf(paramInt);
                arrayOfObject[1] = Boolean.valueOf(false);
                arrayOfObject[2] = Boolean.valueOf(paramBoolean);
                localSQLiteDatabase.execSQL("insert into session_statuses values(?,?,?)", arrayOfObject);
            }
            localCursor.close();
        } finally {
            this.sqLiteHelper.close();
        }
    }
}