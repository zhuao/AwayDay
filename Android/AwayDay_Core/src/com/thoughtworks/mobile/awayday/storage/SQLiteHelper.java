package com.thoughtworks.mobile.awayday.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "away_day.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(Context paramContext) {
        super(paramContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
        paramSQLiteDatabase.execSQL("create table if not exists agendas(agenda_id integer primary key,agenda_date varchar)");
        paramSQLiteDatabase.execSQL("create table if not exists sessions(session_id integer primary key,session_title varchar,session_speaker varchar,session_note varchar,session_location varchar,session_start_time varchar,session_end_time varchar,agenda_id integer)");
        paramSQLiteDatabase.execSQL("create table if not exists session_statuses(session_id integer primary key,has_joined BOOLEAN,has_reminder BOOLEAN)");
        paramSQLiteDatabase.execSQL("create table if not exists tracks(track_id integer primary key autoincrement,message varchar,picture_path varchar,track_time datetime)");
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
    }
}