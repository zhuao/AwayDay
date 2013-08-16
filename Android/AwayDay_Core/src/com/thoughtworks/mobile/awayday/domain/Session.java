package com.thoughtworks.mobile.awayday.domain;

import java.io.Serializable;
import java.util.Date;

public class Session
  implements Serializable
{
  public int agendaId;
  public boolean hasJoined;
  public boolean hasReminder;
  public int reminderType;
  public Date sessionEndTime;
  public int sessionId;
  public String sessionLocation;
  public String sessionNote;
  public String sessionSpeaker;
  public Date sessionStartTime;
  public String sessionTitle;

  public Session()
  {
  }

  public Session(int paramInt1, String paramString1, Date paramDate1, Date paramDate2, String paramString2, String paramString3, String paramString4, int paramInt2)
  {
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

  public String toString()
  {
    return "(" + this.sessionId + " )";
  }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.domain.Session
 * JD-Core Version:    0.6.2
 */
