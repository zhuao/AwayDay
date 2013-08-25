package com.thoughtworks.mobile.awayday.service.implement;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import com.thoughtworks.mobile.awayday.AwayDayApplication;
import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.helper.BitmapHelper;
import com.thoughtworks.mobile.awayday.service.ShareToRemoteService;
import com.thoughtworks.mobile.awayday.utils.ActionStatus;
import com.thoughtworks.mobile.awayday.utils.AppSettings;
import com.thoughtworks.mobile.awayday.utils.StringUtils;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.WeiboParameters;
import com.weibo.sdk.android.net.AsyncWeiboRunner;
import com.weibo.sdk.android.net.HttpManager;
import com.weibo.sdk.android.net.RequestListener;
import org.json.JSONException;

import java.io.*;

public class ShareToRemoteServiceImpl implements ShareToRemoteService, RequestListener{
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

            Log.e("Send weibo", "image1 +" + paramFootprint.imageUriString);
            Log.e("Send weibo", "image2 +" + paramFootprint.imageUri().getPath());
            Log.e("Send weibo", "image3 +" + paramFootprint.imageUri().getEncodedPath());
            Log.e("Send weibo", "image4 +" + paramFootprint.imageUri().getQuery());
            params.add("pic", paramFootprint.imageUri().getPath());

            AsyncWeiboRunner.request(AppSettings.getWeiboImageApi(), params, "POST", this);
        } else {
            AsyncWeiboRunner.request(AppSettings.getWeiboTextApi(), params, HttpManager.HTTPMETHOD_GET, this);
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

    @Override
    public void onComplete(String s) {
        Log.e("Send weibo", s);
    }

    @Override
    public void onComplete4binary(ByteArrayOutputStream byteArrayOutputStream) {
        Log.e("Send weibo", "onComplete4binary");
    }

    @Override
    public void onIOException(IOException e) {
        Log.e("Send weibo","onIOException", e);
    }

    @Override
    public void onError(WeiboException e) {
        Log.e("Send weibo","onError", e);
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.service.implement.ShareToRemoteServiceImpl
 * JD-Core Version:    0.6.2
 */
