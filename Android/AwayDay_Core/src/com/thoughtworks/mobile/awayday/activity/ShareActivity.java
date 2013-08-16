package com.thoughtworks.mobile.awayday.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.domain.Path;
import com.thoughtworks.mobile.awayday.factory.TaskProvider;
import com.thoughtworks.mobile.awayday.helper.ShareScreenHelper;
import com.thoughtworks.mobile.awayday.listeners.ParseThumbnailListener;
import com.thoughtworks.mobile.awayday.listeners.ScreenBackWithoutResultButtonClickedListener;
import com.thoughtworks.mobile.awayday.listeners.SharePostScreenListener;
import com.thoughtworks.mobile.awayday.screen.ShareScreen;
import com.thoughtworks.mobile.awayday.storage.BeanContext;
import com.thoughtworks.mobile.awayday.task.ParseThumbnailTask;

public class ShareActivity extends Activity implements ScreenBackWithoutResultButtonClickedListener, SharePostScreenListener, ParseThumbnailListener {
    private Footprint footprint;
    private ShareScreen shareScreen;
    private ShareScreenHelper shareScreenHelper;

    private void initFootprint() {
        Footprint localFootprint = (Footprint) getIntent().getSerializableExtra("share");
        if (localFootprint != null) {
            this.footprint = localFootprint;
            return;
        }
        this.footprint = new Footprint();
    }

    private void initScreen() {
        this.shareScreen.initComponent(this.footprint, this);
        this.shareScreen.setBackButtonClickedListener(this);
    }

    private void initService() {
        this.shareScreenHelper = new ShareScreenHelper(this);
    }

    private void initUI() {
        setContentView(R.layout.share_activity_layout);
        this.shareScreen = ((ShareScreen) findViewById(R.id.share_screen));
    }

    private void showThumbnail() {
        if (this.footprint.imageUri() == null)
            return;
        ParseThumbnailTask localParseThumbnailTask = TaskProvider.createParseThumbnailTask(getContentResolver(), this);
        Uri[] arrayOfUri = new Uri[1];
        arrayOfUri[0] = this.footprint.imageUri();
        localParseThumbnailTask.execute(arrayOfUri);
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        if (paramInt2 != -1)
            return;
        switch (paramInt1) {
            case 3021:
                this.footprint.imageUriString = paramIntent.getData().toString();
                Log.i("Pick image", " image uri =" + this.footprint.imageUriString);
            case 3023:
                this.footprint.imageUriString = Uri.parse("file:///" + this.shareScreenHelper.getCurrentPhotoFilePath()).toString();
                Log.i("Take image", " image uri =" + this.footprint.imageUriString);
        }
        showThumbnail();
    }

    public void onBack() {
        finish();
    }

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        initUI();
        initService();
    }

    public void onParseEnd(Bitmap paramBitmap) {
        this.shareScreen.showThumbnail(paramBitmap);
    }

    protected void onPostCreate(Bundle paramBundle) {
        super.onPostCreate(paramBundle);
        initFootprint();
        initScreen();
    }

    public void selectImage(Footprint paramFootprint) {
        this.shareScreenHelper.openChooseDialog();
    }

    public void sharePost(Footprint paramFootprint) {
        ((Path) BeanContext.getInstance().getBean(Path.class)).shareFootprint(paramFootprint);
        finish();
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.activity.ShareActivity
 * JD-Core Version:    0.6.2
 */
