package com.thoughtworks.mobile.awayday.factory;

import android.content.ContentResolver;
import com.thoughtworks.mobile.awayday.listeners.AgendaDataReceiver;
import com.thoughtworks.mobile.awayday.listeners.OnSaveFootprintListener;
import com.thoughtworks.mobile.awayday.listeners.OnSessionJoinedListener;
import com.thoughtworks.mobile.awayday.listeners.OnShareFootprintListener;
import com.thoughtworks.mobile.awayday.listeners.ParseThumbnailListener;
import com.thoughtworks.mobile.awayday.service.FetchTrackService;
import com.thoughtworks.mobile.awayday.service.JoinService;
import com.thoughtworks.mobile.awayday.service.SavePathService;
import com.thoughtworks.mobile.awayday.service.ShareToRemoteService;
import com.thoughtworks.mobile.awayday.service.UpdateAgendaService;
import com.thoughtworks.mobile.awayday.task.FetchPathTask;
import com.thoughtworks.mobile.awayday.task.JoinSessionTask;
import com.thoughtworks.mobile.awayday.task.ParseThumbnailTask;
import com.thoughtworks.mobile.awayday.task.SavePathTask;
import com.thoughtworks.mobile.awayday.task.SharePostTask;
import com.thoughtworks.mobile.awayday.task.UpdateAgendaTask;

public class TaskProvider {
    public static FetchPathTask createFetchPathTask(FetchTrackService paramFetchTrackService) {
        return new FetchPathTask(paramFetchTrackService);
    }

    public static JoinSessionTask createJoinSessionTask(OnSessionJoinedListener paramOnSessionJoinedListener, JoinService paramJoinService) {
        return new JoinSessionTask(paramOnSessionJoinedListener, paramJoinService);
    }

    public static ParseThumbnailTask createParseThumbnailTask(ContentResolver paramContentResolver, ParseThumbnailListener paramParseThumbnailListener) {
        return new ParseThumbnailTask(paramContentResolver, paramParseThumbnailListener);
    }

    public static SavePathTask createSavePathTask(SavePathService paramSavePathService, OnSaveFootprintListener paramOnSaveFootprintListener) {
        return new SavePathTask(paramSavePathService, paramOnSaveFootprintListener);
    }

    public static SharePostTask createSharePostTask(ShareToRemoteService paramShareToRemoteService, OnShareFootprintListener paramOnShareFootprintListener) {
        return new SharePostTask(paramShareToRemoteService, paramOnShareFootprintListener);
    }

    public static UpdateAgendaTask createUpdateAgendaTask(UpdateAgendaService paramUpdateAgendaService, AgendaDataReceiver paramAgendaDataReceiver) {
        return new UpdateAgendaTask(paramUpdateAgendaService, paramAgendaDataReceiver);
    }
}