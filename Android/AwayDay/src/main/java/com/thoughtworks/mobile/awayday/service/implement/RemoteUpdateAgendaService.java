package com.thoughtworks.mobile.awayday.service.implement;

import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.thoughtworks.mobile.awayday.domain.Agenda;
import com.thoughtworks.mobile.awayday.domain.Session;
import com.thoughtworks.mobile.awayday.service.UpdateAgendaService;
import com.thoughtworks.mobile.awayday.storage.BeanContext;
import com.thoughtworks.mobile.awayday.storage.LocalStorage;
import com.thoughtworks.mobile.awayday.utils.AppSettings;
import com.thoughtworks.mobile.awayday.utils.FileUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class RemoteUpdateAgendaService
        implements UpdateAgendaService {
    private static final String AGENDAS_FILE_NAME = "agendas_file.json";
    private static final String AGENDAS_URL = AppSettings.getServerUrl() + "/sessions_grouped_by_date";
    private SimpleDateFormat agendaDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    private Context context;
    private SimpleDateFormat sessionDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    public RemoteUpdateAgendaService(Context paramContext) {
        this.context = paramContext;
        this.sessionDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
    }

    private List<Agenda> fetchAgendas() {
        List localList = parseAgendas(getAgendasJSON());
        insertAgendasToDB(localList);
        Log.i("Received AgendaList", localList.toString());
        return localList;
    }

    private String fetchJsonFromLocal() {
        return FileUtils.readFiles(this.context, AGENDAS_FILE_NAME);
    }

    private String fetchJsonFromServer() {
        DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
        HttpGet localHttpGet = new HttpGet(AGENDAS_URL);
        Log.d("agendas server url", AGENDAS_URL);
        try {
            String str = (String) localDefaultHttpClient.execute(localHttpGet, new BasicResponseHandler());
            return str;
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }
        return null;
    }

    private String getAgendasJSON() {
        String response = fetchJsonFromServer();
        String logOutput = " no agendas returned from server";
        if (response == null) {
            Log.d(RemoteUpdateAgendaService.class.getName(), logOutput);
            return fetchJsonFromLocal();
        }
        logOutput = response;
        Log.d(RemoteUpdateAgendaService.class.getName(), logOutput);
        FileUtils.saveFile(this.context, AGENDAS_FILE_NAME, response);
        return response;

    }

    private LocalStorage getLocalStorage() {
        return (LocalStorage) BeanContext.getInstance().getBean(LocalStorage.class);
    }

    private void insertAgendasToDB(List<Agenda> paramList) {
        LocalStorage localLocalStorage = getLocalStorage();
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext()) {
            localLocalStorage.addAgendaData((Agenda) localIterator.next());
        }
    }

    private List<Agenda> parseAgendas(String agendaList) {

        TypeAdapter<Date> dateTypeAdapter = new DateTypeAdapter();
        List<Agenda> agendas = (List<Agenda>) new GsonBuilder().setDateFormat("yyyy-MM-dd").registerTypeAdapter(Date.class, dateTypeAdapter).create().fromJson(agendaList, new TypeToken<List<Agenda>>() {
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

    private String replaceTandZ(String paramString) {
        return paramString.replace('T', ' ').replace("Z", "");
    }

    public List<Agenda> updateAgendas() {
        List<Agenda> agendas = getLocalStorage().queryAgendas();
        if (agendas == null || agendas.size() == 0) {
            return fetchAgendas();
        } else {
            return agendas;
        }
    }

    private static class DateTypeAdapter extends TypeAdapter<Date> {

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