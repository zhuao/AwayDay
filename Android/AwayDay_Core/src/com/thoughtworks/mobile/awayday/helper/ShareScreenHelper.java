package com.thoughtworks.mobile.awayday.helper;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.ContextThemeWrapper;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;
import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.activity.ShareActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShareScreenHelper {
    public static final int CAMERA_WITH_DATA = 3023;
    private static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");
    public static final int PHOTO_PICKED_WITH_DATA = 3021;
    private ShareActivity context;
    private File mCurrentPhotoFile;

    public ShareScreenHelper(ShareActivity paramShareActivity) {
        this.context = paramShareActivity;
    }

    private File createImageFile()
            throws IOException {
        if ((!PHOTO_DIR.exists()) || (!PHOTO_DIR.isDirectory()))
            PHOTO_DIR.mkdirs();
        String str = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return File.createTempFile("IMG_" + str, ".jpg", PHOTO_DIR);
    }

    private ListAdapter createShareDialogAdapter(Context paramContext) {
        String[] arrayOfString = new String[2];
        arrayOfString[0] = this.context.getString(R.string.share_screen_take_photo);
        arrayOfString[1] = this.context.getString(R.string.share_screen_pick_photo);
        return new ArrayAdapter(paramContext, 17367043, arrayOfString);
    }

    public static Intent getPhotoPickIntent() {
        Intent localIntent = new Intent("android.intent.action.GET_CONTENT", null);
        localIntent.setType("image/*");
        return localIntent;
    }

    public static Intent getTakePickIntent(File paramFile) {
        Intent localIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        localIntent.putExtra("output", Uri.fromFile(paramFile));
        return localIntent;
    }

    private void showToast(int paramInt) {
        Toast.makeText(this.context, this.context.getString(paramInt), 30).show();
    }

    protected void doPickPhotoFromGallery() {
        try {
            Intent localIntent = getPhotoPickIntent();
            this.context.startActivityForResult(localIntent, 3021);
            return;
        } catch (ActivityNotFoundException localActivityNotFoundException) {
        }
    }

    protected void doTakePhoto() {
        try {
            this.mCurrentPhotoFile = createImageFile();
            Intent localIntent = getTakePickIntent(this.mCurrentPhotoFile);
            this.context.startActivityForResult(localIntent, 3023);
            return;
        } catch (ActivityNotFoundException localActivityNotFoundException) {
            localActivityNotFoundException.printStackTrace();
            return;
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }
    }

    public String getCurrentPhotoFilePath() {
        return this.mCurrentPhotoFile.getPath();
    }

    public void openChooseDialog() {
        ContextThemeWrapper localContextThemeWrapper = new ContextThemeWrapper(this.context, 16973836);
        String str = this.context.getString(R.string.share_screen_pick_photo_cancel);
        ListAdapter localListAdapter = createShareDialogAdapter(localContextThemeWrapper);
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(localContextThemeWrapper);
        localBuilder.setTitle(R.string.share_screen_add_photo);
        localBuilder.setSingleChoiceItems(localListAdapter, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                paramAnonymousDialogInterface.dismiss();
                switch (paramAnonymousInt) {
                    default:
                        return;
                    case 0:
                        if (Environment.getExternalStorageState().equals("mounted")) {
                            ShareScreenHelper.this.doTakePhoto();
                            return;
                        }
                        ShareScreenHelper.this.showToast(R.string.share_screen_no_sd_card);
                        return;
                    case 1:
                }
                ShareScreenHelper.this.doPickPhotoFromGallery();
            }
        });
        localBuilder.setNegativeButton(str, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                paramAnonymousDialogInterface.dismiss();
            }
        });
        localBuilder.create().show();
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.helper.ShareScreenHelper
 * JD-Core Version:    0.6.2
 */
