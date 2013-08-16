package com.thoughtworks.mobile.awayday.service.implement;

import android.content.Context;
import android.util.Log;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RemoteUpdateAgendaService
        implements UpdateAgendaService {
    private static final String AGENDAS_FILE_NAME = "agendas_file.json";
    private static final String AGENDAS_URL = AppSettings.getServerUrl() + "/sessions_grouped_by_date";
    private SimpleDateFormat agendaDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private Context context;
    private SimpleDateFormat sessionDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    public RemoteUpdateAgendaService(Context paramContext) {
        this.context = paramContext;
        this.sessionDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    private List<Agenda> fetchAgendas() {
        List localList = parseAgendas(getAgendasJSON());
        insertAgendasToDB(localList);
        Log.i("Received AgendaList", localList.toString());
        return localList;
    }

    private String fetchJsonFromLocal() {
        return FileUtils.readFiles(this.context, "agendas_file.json");
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
        String str1 = fetchJsonFromServer();
        if (str1 == null) ;
        for (String str2 = " no agendas returned from server"; ; str2 = str1) {
            Log.d("fetched agendas from server", str2);
            if (str1 == null)
                break;
            FileUtils.saveFile(this.context, "agendas_file.json", str1);
            return str1;
        }
        return fetchJsonFromLocal();
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

    private List<Agenda> parseAgendas(String paramString) {
        ArrayList localArrayList = new ArrayList();
        if (paramString != null) ;
        try {
            JSONArray localJSONArray1 = new JSONArray(paramString);
            int i = localJSONArray1.length();
            for (int j = 0; j < i; j++) {
                JSONObject localJSONObject1 = localJSONArray1.getJSONObject(j);
                Agenda localAgenda = new Agenda();
                localAgenda.setAgendaId(j);
                localAgenda.setAgendaDate(this.agendaDateFormat.parse(localJSONObject1.getString("agenda_date")));
                JSONArray localJSONArray2 = localJSONObject1.getJSONArray("agenda_sessions");
                int k = localJSONArray2.length();
                for (int m = 0; m < k; m++) {
                    JSONObject localJSONObject2 = localJSONArray2.getJSONObject(m);
                    localAgenda.addSession(new Session(localJSONObject2.getInt("session_id"), localJSONObject2.getString("session_title"), this.sessionDateFormat.parse(replaceTandZ(localJSONObject2.getString("session_start"))), this.sessionDateFormat.parse(replaceTandZ(localJSONObject2.getString("session_end"))), localJSONObject2.getString("session_speaker"), localJSONObject2.getString("session_description"), localJSONObject2.getString("session_location"), j));
                }
                localArrayList.add(localAgenda);
            }
        } catch (JSONException localJSONException) {
            localJSONException.printStackTrace();
            return localArrayList;
        } catch (ParseException localParseException) {
            localParseException.printStackTrace();
        }
        return localArrayList;
    }

    private String replaceTandZ(String paramString) {
        return paramString.replace('T', ' ').replace("Z", "");
    }

    public List<Agenda> updateAgendas() {
        getLocalStorage().deleteAgendas();
        return fetchAgendas();
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.service.implement.RemoteUpdateAgendaService
 * JD-Core Version:    0.6.2
 */
