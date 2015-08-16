package com.thoughtworks.mobile.awayday.presenter;

import android.support.v4.widget.SwipeRefreshLayout;

import com.thoughtworks.mobile.awayday.domain.Agenda;
import com.thoughtworks.mobile.awayday.domain.Session;
import com.thoughtworks.mobile.awayday.factory.TaskProvider;
import com.thoughtworks.mobile.awayday.listeners.AgendaDataReceiver;
import com.thoughtworks.mobile.awayday.listeners.AgendaItemViewStateRecorder;
import com.thoughtworks.mobile.awayday.screen.AgendaScreen;
import com.thoughtworks.mobile.awayday.service.implement.RemoteUpdateAgendaService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AgendaPresenter implements SwipeRefreshLayout.OnRefreshListener, AgendaDataReceiver, AgendaItemViewStateRecorder {
    private List<Agenda> agendaList;
    private AgendaScreen agendaScreen;
    private Map<Integer, Integer> itemViewStateMap;

    public AgendaPresenter(AgendaScreen paramAgendaScreen) {
        this.agendaScreen = paramAgendaScreen;
        this.itemViewStateMap = new HashMap();
    }

    public int getItemViewVisibility(int paramInt) {
        if (!this.itemViewStateMap.containsKey(Integer.valueOf(paramInt)))
            return 8;
        return ((Integer) this.itemViewStateMap.get(Integer.valueOf(paramInt))).intValue();
    }

    public void onReceivedAgenda(List<Agenda> paramList) {
        this.agendaList = paramList;
        this.agendaScreen.updateWithAgenda(paramList);
    }

    public void onRefresh() {
        this.itemViewStateMap.clear();
        TaskProvider.createUpdateAgendaTask(new RemoteUpdateAgendaService(this.agendaScreen.getContext()), this).execute(new Void[0]);
    }

    public void saveAgendaItemAppendixViewState(int paramInt1, int paramInt2) {
        this.itemViewStateMap.put(Integer.valueOf(paramInt1), Integer.valueOf(paramInt2));
    }

    public void setRemindForSession(int paramInt, boolean paramBoolean) {
        Iterator localIterator = this.agendaList.iterator();
        while (localIterator.hasNext()) {
            Session localSession = ((Agenda) localIterator.next()).getSessionById(paramInt);
            if (localSession != null)
                localSession.hasReminder = paramBoolean;
        }
    }
}