package com.thoughtworks.mobile.awayday.service.implement;

import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.domain.Path;
import com.thoughtworks.mobile.awayday.domain.Session;
import com.thoughtworks.mobile.awayday.service.JoinService;
import com.thoughtworks.mobile.awayday.storage.BeanContext;
import com.thoughtworks.mobile.awayday.storage.LocalStorage;
import com.thoughtworks.mobile.awayday.utils.ActionStatus;

import java.util.Calendar;

public class JoinServiceImpl
        implements JoinService {
    private ActionStatus joinSession(Session paramSession) {
        updateData(paramSession, true);
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramSession.sessionTitle;
        joinSessionFootprint(paramSession, String.format("Join %s", arrayOfObject));
        return ActionStatus.JOIN_SUCCESS;
    }

    private void joinSessionFootprint(Session paramSession, String paramString) {
        Footprint localFootprint = new Footprint();
        localFootprint.createdDate = Calendar.getInstance().getTime();
        localFootprint.content = paramString;
        localFootprint.sessionId = paramSession.sessionId;
        shareFootprint(localFootprint);
    }

    private void shareFootprint(Footprint paramFootprint) {
        ((Path) BeanContext.getInstance().getBean(Path.class)).saveToLocal(paramFootprint);
    }

    private ActionStatus unJoinSession(Session paramSession) {
        updateData(paramSession, false);
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramSession.sessionTitle;
        joinSessionFootprint(paramSession, String.format("Leave %s", arrayOfObject));
        return ActionStatus.UNJOIN_SUCCESS;
    }

    private void updateData(Session paramSession, boolean paramBoolean) {
        paramSession.hasJoined = paramBoolean;
        ((LocalStorage) BeanContext.getInstance().getBean(LocalStorage.class)).joinSession(paramSession.sessionId, paramBoolean);
    }

    public ActionStatus handleSession(Session paramSession) {
        if (paramSession.hasJoined)
            return unJoinSession(paramSession);
        return joinSession(paramSession);
    }
}