package com.thoughtworks.mobile.awayday.service.implement;

import android.content.Context;
import android.graphics.Bitmap;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.domain.Settings;
import com.thoughtworks.mobile.awayday.helper.BitmapHelper;
import com.thoughtworks.mobile.awayday.service.ShareToRemoteService;
import com.thoughtworks.mobile.awayday.utils.ActionStatus;
import com.thoughtworks.mobile.awayday.utils.AppSettings;
import com.thoughtworks.mobile.awayday.utils.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ShareToRemoteServiceImpl
        implements ShareToRemoteService {
    private static final String SHARE_URL = AppSettings.getShareServerUrl();
    private Context context;
    private String deviceId;

    public ShareToRemoteServiceImpl(Context paramContext) {
        this.context = paramContext;
        this.deviceId = ((TelephonyManager) this.context.getSystemService("phone")).getDeviceId();
    }

    private void addImage(Footprint paramFootprint, JSONObject paramJSONObject)
            throws IOException, JSONException {
        if (StringUtils.isEmpty(paramFootprint.imageUriString))
            return;
        Bitmap localBitmap = BitmapHelper.extractCompressedBitmap(this.context.getContentResolver(), paramFootprint.imageUri(), AppSettings.getBitmapCompressedWidth(), AppSettings.getBitmapCompressedHeight());
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        localBitmap.compress(Bitmap.CompressFormat.PNG, AppSettings.getBitmapCompressedQuality(), localByteArrayOutputStream);
        try {
            String str = new String(Base64.encode(localByteArrayOutputStream.toByteArray(), 0));
            Log.i("upload buffer", "upload image as buffer to server, buffer string length is " + str.length());
            paramJSONObject.put("share_image", str);
            return;
        } catch (OutOfMemoryError localOutOfMemoryError) {
            Log.w("out of memory", "out of memory when decode image");
        }
    }

    private void sendFootprintToServer(Footprint paramFootprint)
            throws Exception {
        JSONObject localJSONObject = new JSONObject();
        localJSONObject.put("session_id", String.valueOf(this.deviceId));
        localJSONObject.put("device_id", String.valueOf(this.deviceId));
        localJSONObject.put("share_text", paramFootprint.content);
        localJSONObject.put("username", Settings.getSettings().getUserName());
        localJSONObject.put("timestamp", String.valueOf(paramFootprint.createdDate.getTime() / 1000L));
        if (AppSettings.isSharedImageOn())
            addImage(paramFootprint, localJSONObject);
        sendToServer(localJSONObject);
    }

    private void sendToServer(JSONObject paramJSONObject)
            throws IOException {
        DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
        HttpPost localHttpPost = new HttpPost(SHARE_URL);
        String str = paramJSONObject.toString();
        Log.d("share url", SHARE_URL);
        Log.d("share string", str);
        localHttpPost.setEntity(new StringEntity(str, "UTF-8"));
        localHttpPost.setHeader("Accept", "application/json");
        localHttpPost.setHeader("Content-type", "application/json");
        localDefaultHttpClient.execute(localHttpPost, new BasicResponseHandler());
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

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.service.implement.ShareToRemoteServiceImpl
 * JD-Core Version:    0.6.2
 */
