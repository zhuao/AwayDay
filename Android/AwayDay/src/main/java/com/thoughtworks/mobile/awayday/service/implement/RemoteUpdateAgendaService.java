package com.thoughtworks.mobile.awayday.service.implement;

import android.content.Context;
import android.util.Log;

import com.thoughtworks.mobile.awayday.domain.Agenda;
import com.thoughtworks.mobile.awayday.service.UpdateAgendaService;
import com.thoughtworks.mobile.awayday.storage.BeanContext;
import com.thoughtworks.mobile.awayday.storage.LocalStorage;
import com.thoughtworks.mobile.awayday.utils.AppSettings;
import com.thoughtworks.mobile.awayday.utils.FileUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.*;

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
        List localList = Agenda.parseAgendas(new StringReader(getAgendasJSON()));
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

    public List<Agenda> updateAgendas() {
        List<Agenda> agendas = getLocalStorage().queryAgendas();
        if (agendas == null || agendas.size() == 0) {
            return fetchAgendas();
        } else {
            return agendas;
        }
    }

}