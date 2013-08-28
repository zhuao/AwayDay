package com.thoughtworks.mobile.awayday.service.implement;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import com.thoughtworks.mobile.awayday.AwayDayApplication;
import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.helper.BitmapHelper;
import com.thoughtworks.mobile.awayday.listeners.OnShareFootprintListener;
import com.thoughtworks.mobile.awayday.service.ShareToRemoteService;
import com.thoughtworks.mobile.awayday.storage.BeanContext;
import com.thoughtworks.mobile.awayday.utils.ActionStatus;
import com.thoughtworks.mobile.awayday.utils.AppSettings;
import com.thoughtworks.mobile.awayday.utils.StringUtils;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.WeiboParameters;
import com.weibo.sdk.android.net.AsyncWeiboRunner;
import com.weibo.sdk.android.net.HttpManager;
import com.weibo.sdk.android.net.RequestListener;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ShareToRemoteServiceImpl implements ShareToRemoteService{
    private Context context;

    public ShareToRemoteServiceImpl(Context paramContext) {
        this.context = paramContext;
    }

    private String getImage(Footprint paramFootprint)
            throws IOException, JSONException {
        if (StringUtils.isEmpty(paramFootprint.imageUriString))
            return null;
        Bitmap localBitmap = BitmapHelper.extractCompressedBitmap(this.context.getContentResolver(), paramFootprint.imageUri(), AppSettings.getBitmapCompressedWidth(), AppSettings.getBitmapCompressedHeight());
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        localBitmap.compress(Bitmap.CompressFormat.PNG, AppSettings.getBitmapCompressedQuality(), localByteArrayOutputStream);
        try {
            String str = new String(Base64.encode(localByteArrayOutputStream.toByteArray(), 0));
            Log.i("upload buffer", "upload image as buffer to server, buffer string length is " + str.length());

            return str;
        } catch (OutOfMemoryError localOutOfMemoryError) {
            Log.w("out of memory", "out of memory when decode image");
        }
        return null;
    }

    private void sendFootprintToServer(Footprint paramFootprint)
            throws Exception {

        WeiboParameters params = new WeiboParameters();
        params.add("status", paramFootprint.content);
        params.add("access_token", String.valueOf(AwayDayApplication.accessToken.getToken()));
        if (AppSettings.isSharedImageOn() && paramFootprint.imageUriString != null) {

            params.add("pic", paramFootprint.imageUri().getPath());

            AsyncWeiboRunner.request(AppSettings.getWeiboImageApi(), params, "POST", BeanContext.getInstance().getBean(OnShareFootprintListener.class));
        } else {
            AsyncWeiboRunner.request(AppSettings.getWeiboTextApi(), params, "POST", BeanContext.getInstance().getBean(OnShareFootprintListener.class));
        }
    }


    public ActionStatus shareToServer(Footprint paramFootprint) {
        try {
            sendFootprintToServer(paramFootprint);
            ActionStatus localActionStatus = ActionStatus.SHARE_SUCCESS;
            return localActionStatus;
        } catch (Exception localException) {
            localException.printStackTrace();
            Log.e("Share post", localException.getMessage());
        }
        return ActionStatus.SHARE_WITH_SERVER_ERROR;
    }

}