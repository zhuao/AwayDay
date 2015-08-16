package com.thoughtworks.mobile.awayday.task;

import android.os.AsyncTask;
import com.thoughtworks.mobile.awayday.domain.Session;
import com.thoughtworks.mobile.awayday.listeners.OnSessionJoinedListener;
import com.thoughtworks.mobile.awayday.service.JoinService;
import com.thoughtworks.mobile.awayday.utils.ActionStatus;

public class JoinSessionTask extends AsyncTask<Session, Void, ActionStatus> {
    private JoinService joinService;
    private OnSessionJoinedListener onSessionJoinedListener;

    public JoinSessionTask(OnSessionJoinedListener paramOnSessionJoinedListener, JoinService paramJoinService) {
        this.onSessionJoinedListener = paramOnSessionJoinedListener;
        this.joinService = paramJoinService;
    }

    protected ActionStatus doInBackground(Session[] paramArrayOfSession) {
        if (paramArrayOfSession.length != 0)
            return this.joinService.handleSession(paramArrayOfSession[0]);
        return ActionStatus.EMPTY;
    }

    protected void onPostExecute(ActionStatus paramActionStatus) {
        this.onSessionJoinedListener.onSessionJoin(paramActionStatus);
    }
}