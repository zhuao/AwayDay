package com.thoughtworks.mobile.awayday.task;

import android.os.AsyncTask;
import com.thoughtworks.mobile.awayday.domain.Agenda;
import com.thoughtworks.mobile.awayday.listeners.AgendaDataReceiver;
import com.thoughtworks.mobile.awayday.service.UpdateAgendaService;

import java.util.List;

public class UpdateAgendaTask extends AsyncTask<Void, Void, List<Agenda>> {
    protected AgendaDataReceiver agendaDataReceiver;
    protected UpdateAgendaService updateAgendaService;

    public UpdateAgendaTask(UpdateAgendaService paramUpdateAgendaService, AgendaDataReceiver paramAgendaDataReceiver) {
        this.updateAgendaService = paramUpdateAgendaService;
        this.agendaDataReceiver = paramAgendaDataReceiver;
    }

    protected List<Agenda> doInBackground(Void[] paramArrayOfVoid) {
        return this.updateAgendaService.updateAgendas();
    }

    protected void onPostExecute(List<Agenda> paramList) {
        this.agendaDataReceiver.onReceivedAgenda(paramList);
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.task.UpdateAgendaTask
 * JD-Core Version:    0.6.2
 */
