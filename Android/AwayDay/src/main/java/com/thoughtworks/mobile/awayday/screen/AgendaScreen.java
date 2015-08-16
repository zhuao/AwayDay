package com.thoughtworks.mobile.awayday.screen;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.adapter.AgendaListViewAdapter;
import com.thoughtworks.mobile.awayday.domain.Agenda;
import com.thoughtworks.mobile.awayday.factory.AgendaItemBuilder;
import com.thoughtworks.mobile.awayday.presenter.AgendaPresenter;

import java.util.List;

public class AgendaScreen extends LinearLayout {
    private AgendaListViewAdapter agendaListViewAdapter;
    private AgendaPresenter agendaPresenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView toRefreshListView;

    public AgendaScreen(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        initUI();
    }

    private void initAdapter(AgendaItemBuilder paramAgendaItemBuilder) {
        this.agendaListViewAdapter = new AgendaListViewAdapter(paramAgendaItemBuilder);
        toRefreshListView.setAdapter(agendaListViewAdapter);
    }

    private void initPresenter() {
        swipeRefreshLayout.setOnRefreshListener(agendaPresenter);
    }

    private void initUI() {
        inflate(getContext(), R.layout.agenda_layout, this);
        swipeRefreshLayout = ((SwipeRefreshLayout) findViewById(R.id.agenda_screen_swipe_refresh));
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_red_dark, android.R.color.holo_orange_dark);
        toRefreshListView = ((ListView) findViewById(android.R.id.list));
    }

    public void initComponent(AgendaItemBuilder paramAgendaItemBuilder, AgendaPresenter paramAgendaPresenter) {
        this.agendaPresenter = paramAgendaPresenter;
        initAdapter(paramAgendaItemBuilder);
        initPresenter();
    }

    public void startFetchAgendas() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        agendaPresenter.onRefresh();

    }

    public void updateWithAgenda(List<Agenda> paramList) {
        this.agendaListViewAdapter.updateAgenda(paramList);
        swipeRefreshLayout.setRefreshing(false);
    }
}