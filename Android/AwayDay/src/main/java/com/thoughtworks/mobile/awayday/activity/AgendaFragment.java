package com.thoughtworks.mobile.awayday.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.domain.Session;
import com.thoughtworks.mobile.awayday.factory.AgendaItemBuilder;
import com.thoughtworks.mobile.awayday.factory.TaskProvider;
import com.thoughtworks.mobile.awayday.helper.RemindAlarmHelper;
import com.thoughtworks.mobile.awayday.listeners.AgendaItemActionClickedListener;
import com.thoughtworks.mobile.awayday.listeners.OnRemindSetListener;
import com.thoughtworks.mobile.awayday.listeners.OnSessionJoinedListener;
import com.thoughtworks.mobile.awayday.presenter.AgendaPresenter;
import com.thoughtworks.mobile.awayday.screen.AgendaScreen;
import com.thoughtworks.mobile.awayday.service.JoinService;
import com.thoughtworks.mobile.awayday.storage.BeanContext;
import com.thoughtworks.mobile.awayday.storage.LocalStorage;

public class AgendaFragment extends Fragment implements AgendaItemActionClickedListener {

    private AgendaPresenter agendaPresenter;
    private AgendaScreen agendaScreen;
    private OnRemindSetListener onRemindSetListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.agenda_page, container);
        agendaScreen = (AgendaScreen) view.findViewById(R.id.agenda_screen);
        initAgenda();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        startFetchAgendas();
    }

    private void startFetchAgendas() {
        this.agendaScreen.startFetchAgendas();
    }
    private void initAgenda() {
        this.agendaPresenter = new AgendaPresenter(agendaScreen);
        AgendaItemBuilder localAgendaItemBuilder = new AgendaItemBuilder(getActivity());
        localAgendaItemBuilder.setAgendaItemActionClickedListener(this);
        localAgendaItemBuilder.setAgenItemViewStateRecorder(this.agendaPresenter);
        this.agendaScreen.initComponent(localAgendaItemBuilder, this.agendaPresenter);
    }

    public void onJoinActionClicked(OnSessionJoinedListener paramOnSessionJoinedListener, Session paramSession) {
        TaskProvider.createJoinSessionTask(paramOnSessionJoinedListener, (JoinService) BeanContext.getInstance().getBean(JoinService.class)).execute(new Session[]{paramSession});
    }

    public void onRemindActionClicked(OnRemindSetListener paramOnRemindSetListener, Session paramSession) {
        if (paramSession.hasReminder) {
            cancelRemind(paramSession);
            return;
        }
        doSetRemind(paramOnRemindSetListener, paramSession);
    }

    public void onShareActionClicked(Session paramSession) {
        Intent localIntent = new Intent();
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramSession.sessionTitle;
        localIntent.putExtra("share", new Footprint(String.format("[%s]", arrayOfObject), paramSession.sessionId));
        startShareActivity(localIntent);
    }

    @Override
    public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        if ((paramInt1 == 123) && (paramInt2 == -1)) {
            boolean bool = paramIntent.getBooleanExtra("setResult", false);
            this.agendaPresenter.setRemindForSession(paramIntent.getIntExtra("sessionId", 0), bool);
            setAgendaItemRemindActionImage(bool);
        }
    }

    private void cancelRemind(Session paramSession) {
        paramSession.hasReminder = false;
        BeanContext.getInstance().getBean(LocalStorage.class).reminderSession(paramSession.sessionId, false);
        RemindAlarmHelper.getInstance(getActivity()).cancelRemind(paramSession.sessionId);
        setAgendaItemRemindActionImage(paramSession.hasReminder);
    }

    private void doSetRemind(OnRemindSetListener paramOnRemindSetListener, Session paramSession) {
        this.onRemindSetListener = paramOnRemindSetListener;
        Intent localIntent = new Intent();
        localIntent.putExtra("session", paramSession);
        localIntent.setClass(getActivity(), ReminderActivity.class);
        startActivityForResult(localIntent, 123);
    }

    private void startShareActivity(Intent paramIntent) {
        paramIntent.setClass(getActivity(), ShareActivity.class);
        startActivity(paramIntent);
    }

    private void setAgendaItemRemindActionImage(boolean paramBoolean) {
        if (this.onRemindSetListener == null)
            return;
        this.onRemindSetListener.onRemindSet(paramBoolean);
    }

}
