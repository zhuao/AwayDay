package com.thoughtworks.mobile.awayday.screen;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.adapter.AgendaListViewAdapter;
import com.thoughtworks.mobile.awayday.components.PullToRefreshListView;
import com.thoughtworks.mobile.awayday.domain.Agenda;
import com.thoughtworks.mobile.awayday.factory.AgendaItemBuilder;
import com.thoughtworks.mobile.awayday.presenter.AgendaPresenter;

import java.util.List;

public class AgendaScreen extends LinearLayout {
    private AgendaListViewAdapter agendaListViewAdapter;
    private AgendaPresenter agendaPresenter;
    private PullToRefreshListView pullToRefreshListView;

    public AgendaScreen(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        initUI();
    }

    private void initAdapter(AgendaItemBuilder paramAgendaItemBuilder) {
        this.agendaListViewAdapter = new AgendaListViewAdapter(paramAgendaItemBuilder);
        this.pullToRefreshListView.setAdapter(this.agendaListViewAdapter);
    }

    private void initPresenter() {
        this.pullToRefreshListView.setOnRefreshListener(this.agendaPresenter);
        this.agendaPresenter.setRefreshAgendaCallback(this.pullToRefreshListView);
    }

    private void initUI() {
        addView((ViewGroup) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.agenda_layout, this, false));
        this.pullToRefreshListView = ((PullToRefreshListView) findViewById(R.id.pull_to_refresh_list));
    }

    public void initComponent(AgendaItemBuilder paramAgendaItemBuilder, AgendaPresenter paramAgendaPresenter) {
        this.agendaPresenter = paramAgendaPresenter;
        initAdapter(paramAgendaItemBuilder);
        initPresenter();
    }

    public void startFetchAgendas() {
        this.pullToRefreshListView.startRefresh();
    }

    public void updateWithAgenda(List<Agenda> paramList) {
        this.agendaListViewAdapter.updateAgenda(paramList);
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.screen.AgendaScreen
 * JD-Core Version:    0.6.2
 */
