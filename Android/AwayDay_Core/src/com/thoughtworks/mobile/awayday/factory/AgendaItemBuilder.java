package com.thoughtworks.mobile.awayday.factory;

import android.content.Context;
import com.thoughtworks.mobile.awayday.components.AgendaItemView;
import com.thoughtworks.mobile.awayday.domain.Session;
import com.thoughtworks.mobile.awayday.listeners.AgendaItemActionClickedListener;
import com.thoughtworks.mobile.awayday.listeners.AgendaItemViewStateRecorder;

import java.util.Date;

public class AgendaItemBuilder {
    private AgendaItemActionClickedListener agendaItemActionClickedListener;
    private AgendaItemViewStateRecorder agendaItemViewStateRecorder;
    private Context context;

    public AgendaItemBuilder(Context paramContext) {
        this.context = paramContext;
    }

    public AgendaItemView buildNewAgendaItemView(Session paramSession) {
        AgendaItemView localAgendaItemView = new AgendaItemView(this.context);
        localAgendaItemView.setAgendaItemActionClickedListener(this.agendaItemActionClickedListener);
        localAgendaItemView.setAgendaItemViewStateRecorder(this.agendaItemViewStateRecorder);
        localAgendaItemView.fillWithData(paramSession);
        return localAgendaItemView;
    }

    public AgendaItemView buildNewAgendaItemViewWithTopDateTitle(Date paramDate, Session paramSession) {
        AgendaItemView localAgendaItemView = buildNewAgendaItemView(paramSession);
        localAgendaItemView.showAgendaDate(paramDate);
        return localAgendaItemView;
    }

    public void setAgenItemViewStateRecorder(AgendaItemViewStateRecorder paramAgendaItemViewStateRecorder) {
        this.agendaItemViewStateRecorder = paramAgendaItemViewStateRecorder;
    }

    public void setAgendaItemActionClickedListener(AgendaItemActionClickedListener paramAgendaItemActionClickedListener) {
        this.agendaItemActionClickedListener = paramAgendaItemActionClickedListener;
    }

    public void updateAgendaItemView(AgendaItemView paramAgendaItemView, Session paramSession) {
        paramAgendaItemView.fillWithData(paramSession);
        paramAgendaItemView.initStateAccordingToSession();
    }

    public void updateAgendaItemViewWithTopDateTitle(AgendaItemView paramAgendaItemView, Date paramDate, Session paramSession) {
        updateAgendaItemView(paramAgendaItemView, paramSession);
        paramAgendaItemView.showAgendaDate(paramDate);
    }
}