package com.thoughtworks.mobile.awayday.listeners;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.utils.ActionStatus;
import com.weibo.sdk.android.WeiboException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ShareFootprintMonitor implements OnShareFootprintListener {
    private Context context;
    private ProgressDialog progressDialog;

    public ShareFootprintMonitor(Context paramContext) {
        this.context = paramContext;
    }

    @Override
    public void showFullScreenLoading() {
        progressDialog = ProgressDialog.show(context, "Share", "It's sharing...");
    }

    @Override
    public void dismissLoading() {
        progressDialog.dismiss();
    }

    public void onFootprintShared(Footprint paramFootprint, ActionStatus paramActionStatus) {
        if (paramActionStatus != null) {
            paramActionStatus.showMessage(this.context);
        }
    }

    @Override
    public void onComplete(String s) {
    }

    @Override
    public void onComplete4binary(ByteArrayOutputStream byteArrayOutputStream) {
    }

    @Override
    public void onIOException(IOException e) {
        throw new RuntimeException(e.getMessage());
    }

    @Override
    public void onError(WeiboException e) {
        throw new RuntimeException(e.getMessage());
    }
}