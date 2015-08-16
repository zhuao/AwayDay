package com.thoughtworks.mobile.awayday.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Session implements Serializable {
    public int agendaId;
    public boolean hasJoined;
    public boolean hasReminder;
    public int reminderType;
    @SerializedName("session_end")
    public Date sessionEndTime;
    @SerializedName("session_id")
    public int sessionId;
    @SerializedName("session_location")
    public String sessionLocation;
    @SerializedName("session_description")
    public String sessionNote;
    @SerializedName("session_speaker")
    public String sessionSpeaker;
    @SerializedName("session_start")
    public Date sessionStartTime;
    @SerializedName("session_title")
    public String sessionTitle;

    public Session() {
    }

    public Session(int paramInt1, String paramString1, Date paramDate1, Date paramDate2, String paramString2, String paramString3, String paramString4, int paramInt2) {
        this.sessionId = paramInt1;
        this.sessionTitle = paramString1;
        this.sessionStartTime = paramDate1;
        this.sessionEndTime = paramDate2;
        this.sessionSpeaker = paramString2;
        this.sessionNote = paramString3;
        this.sessionLocation = paramString4;
        this.agendaId = paramInt2;
        this.hasJoined = false;
        this.hasReminder = false;
        this.reminderType = 0;
    }

    public String toString() {
        return "(" + this.sessionId + " )";
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.domain.Session
 * JD-Core Version:    0.6.2
 */
