package com.thoughtworks.mobile.awayday.utils;

import android.content.Context;
import com.thoughtworks.mobile.awayday.R;

public enum ActionStatus {
    SESSION_CONFLICT(R.string.session_conflict_message),

    SHARE_WITH_SERVER_ERROR(R.string.share_with_server_error),
    JOIN_SUCCESS(R.string.join_in_success_message),
    UNJOIN_SUCCESS(R.string.unjoin_success_message),
    SAVE_TO_LOCAL_ERROR(R.string.save_share_to_local_error),
    SHARE_SUCCESS(R.string.share_success_message),
    SAVE_SUCCESS_BUT_SHARE_ERROR(R.string.save_success_but_share_error),
    EMPTY(R.string.app_name);

    private int messageId;



    ActionStatus(int messageResourceId) {
        this.messageId = messageResourceId;
    }


    public void showMessage(Context paramContext) {
        ViewUtils.showToast(paramContext, messageId);
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.utils.ActionStatus
 * JD-Core Version:    0.6.2
 */
