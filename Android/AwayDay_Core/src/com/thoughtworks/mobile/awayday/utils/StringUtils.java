package com.thoughtworks.mobile.awayday.utils;

import com.thoughtworks.mobile.awayday.domain.Session;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringUtils {
    private static SimpleDateFormat agendaDateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH);
    private static SimpleDateFormat agendaDateFormatForDb = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private static SimpleDateFormat pathDateFormat = new SimpleDateFormat("dd, MMM", Locale.ENGLISH);
    private static SimpleDateFormat pathTimeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private static SimpleDateFormat pullToRefreshDateFormat = new SimpleDateFormat("yyyy-MMMM-dd HH:mm:ss", Locale.ENGLISH);
    private static SimpleDateFormat sessionDateFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private static SimpleDateFormat sessionDateFormatForDb = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    public static String formatAgendaDateToString(Date paramDate) {
        return agendaDateFormat.format(paramDate);
    }

    public static String formatAgendaDateToStringForDb(Date paramDate) {
        return agendaDateFormatForDb.format(paramDate);
    }

    public static String formatAlarmMessage(Session paramSession) {
        return paramSession.sessionTitle + " [ " + paramSession.sessionSpeaker + " ] at " + paramSession.sessionLocation + " - " + formatSessionDateToString(paramSession.sessionStartTime);
    }

    public static String formatPathDate(Date paramDate) {
        return pathDateFormat.format(paramDate);
    }

    public static String formatPathTime(Date paramDate) {
        return pathTimeFormat.format(paramDate);
    }

    public static String formatSessionDateToString(Date paramDate) {
        return sessionDateFormat.format(paramDate);
    }

    public static String formatSessionDateToStringForDB(Date paramDate) {
        return sessionDateFormatForDb.format(paramDate);
    }

    public static boolean isEmpty(String paramString) {
        return (paramString == null) || ("".equals(paramString));
    }

    public static Date parseAgendaDate(String paramString) {
        try {
            Date localDate = agendaDateFormatForDb.parse(paramString);
            return localDate;
        } catch (ParseException localParseException) {
        }
        return new Date();
    }

    public static String parsePullToRefreshDateToString(Date paramDate) {
        return pullToRefreshDateFormat.format(paramDate);
    }

    public static Date parseSessionDateToString(String paramString) {
        try {
            Date localDate = sessionDateFormatForDb.parse(paramString);
            return localDate;
        } catch (ParseException localParseException) {
            localParseException.printStackTrace();
        }
        return new Date();
    }

    public static boolean parseToBoolean(String paramString) {
        return paramString.equals("1");
    }

    public static String parseToPathSummaryText(String paramString) {
        return "has " + paramString + " records";
    }

    public static String trim(String paramString) {
        if (paramString == null) return "";
        String str = paramString.trim();
        if ((str.equalsIgnoreCase("null")) || (str.equalsIgnoreCase("(null)"))) {
            str = "";
        }
        return str;
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.utils.StringUtils
 * JD-Core Version:    0.6.2
 */
