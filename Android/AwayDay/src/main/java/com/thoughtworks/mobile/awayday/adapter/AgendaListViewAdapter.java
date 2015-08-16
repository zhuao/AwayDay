package com.thoughtworks.mobile.awayday.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.thoughtworks.mobile.awayday.components.AgendaItemView;
import com.thoughtworks.mobile.awayday.domain.Agenda;
import com.thoughtworks.mobile.awayday.domain.Session;
import com.thoughtworks.mobile.awayday.factory.AgendaItemBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AgendaListViewAdapter extends BaseAdapter {
    private List<Agenda> agendaList;
    private AgendaItemBuilder itemBuilder;

    public AgendaListViewAdapter(AgendaItemBuilder paramAgendaItemBuilder) {
        this.itemBuilder = paramAgendaItemBuilder;
        this.agendaList = new ArrayList();
    }

    private Agenda getAgendaByPosition(int paramInt) {
        int i = 0;
        Iterator localIterator = this.agendaList.iterator();
        while (localIterator.hasNext()) {
            Agenda localAgenda = (Agenda) localIterator.next();
            for (int j = 0; j < localAgenda.getSessionCount(); j++) {
                if (i == paramInt)
                    return localAgenda;
                i++;
            }
        }
        return null;
    }

    private Session getSessionByPosition(int paramInt) {
        int i = 0;
        Iterator localIterator = this.agendaList.iterator();
        while (localIterator.hasNext()) {
            Agenda localAgenda = (Agenda) localIterator.next();
            for (int j = 0; j < localAgenda.getSessionCount(); j++) {
                if (i == paramInt)
                    return localAgenda.getSession(j);
                i++;
            }
        }
        return null;
    }

    private View getViewWithAgendaDate(View paramView, int paramInt) {
        Agenda localAgenda = getAgendaByPosition(paramInt);
        if (localAgenda == null) {
            return paramView;
        }
        if (paramView == null) {
            return itemBuilder.buildNewAgendaItemViewWithTopDateTitle(localAgenda.getAgendaDate(), localAgenda.getSession(0));
        }
        itemBuilder.updateAgendaItemViewWithTopDateTitle((AgendaItemView) paramView, localAgenda.getAgendaDate(), localAgenda.getSession(0));
        return paramView;
    }

    private View getViewWithoutAgendaDate(int paramInt, View paramView) {
        Session localSession = getSessionByPosition(paramInt);
        if (localSession == null)
            return paramView;
        if (paramView == null)
            return this.itemBuilder.buildNewAgendaItemView(localSession);
        this.itemBuilder.updateAgendaItemView((AgendaItemView) paramView, localSession);
        return paramView;
    }

    private boolean isFirstSessionInAgenda(int paramInt) {
        int i = 0;
        Iterator localIterator = this.agendaList.iterator();
        while (localIterator.hasNext()) {
            Agenda localAgenda = (Agenda) localIterator.next();
            for (int j = 0; j < localAgenda.getSessionCount(); j++) {
                if ((i == paramInt) && (j == 0))
                    return true;
                i++;
            }
        }
        return false;
    }

    public int getCount() {
        int i = 0;
        Iterator localIterator = this.agendaList.iterator();
        while (localIterator.hasNext())
            i += ((Agenda) localIterator.next()).getSessionCount();
        return i;
    }

    public Object getItem(int paramInt) {
        return Integer.valueOf(paramInt);
    }

    public long getItemId(int paramInt) {
        return paramInt;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (isFirstSessionInAgenda(position)) {
            return getViewWithAgendaDate(convertView, position);
        }
        return getViewWithoutAgendaDate(position, convertView);
    }

    public void updateAgenda(List<Agenda> paramList) {
        this.agendaList = paramList;
        notifyDataSetChanged();
    }
}