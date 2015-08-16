package com.thoughtworks.mobile.awayday.screen;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.listeners.ScreenBackWithoutResultButtonClickedListener;
import com.thoughtworks.mobile.awayday.listeners.SharePostScreenListener;
import com.thoughtworks.mobile.awayday.utils.ViewUtils;

import java.util.Calendar;

public class ShareScreen extends RelativeLayout implements View.OnClickListener {
    private ImageView backImageView;
    private Footprint footprint;
    private ScreenBackWithoutResultButtonClickedListener screenBackButtonClickedListener;
    private TextView shareCountTextView;
    private EditText shareEditText;
    private ImageView shareImageImageView;
    private ImageView sharePostImageView;
    private SharePostScreenListener sharePostScreenListener;
    private ViewGroup thumbnailContainer;
    private TextView thumbnailTipText;

    public ShareScreen(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        initUI();
    }

    private void addThumbnailToView(Bitmap paramBitmap) {
        this.thumbnailTipText.setVisibility(VISIBLE);
        ImageView localImageView = new ImageView(getContext());
        localImageView.setImageBitmap(paramBitmap);
        localImageView.setOnClickListener(this);
        this.thumbnailContainer.addView(localImageView);
    }

    private void initListener() {
        this.backImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                ViewUtils.hideKeyboard(ShareScreen.this.shareEditText);
                if (ShareScreen.this.screenBackButtonClickedListener != null)
                    ShareScreen.this.screenBackButtonClickedListener.onBack();
            }
        });
        this.sharePostImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                ViewUtils.hideKeyboard(ShareScreen.this.shareEditText);
                if (ShareScreen.this.sharePostScreenListener != null) {
                    ShareScreen.this.footprint.createdDate = Calendar.getInstance().getTime();
                    ShareScreen.this.sharePostScreenListener.sharePost(ShareScreen.this.footprint);
                }
            }
        });
        this.shareEditText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable paramAnonymousEditable) {
                TextView localTextView = ShareScreen.this.shareCountTextView;
                Object[] arrayOfObject = new Object[1];
                arrayOfObject[0] = Integer.valueOf(140 - ShareScreen.this.shareEditText.getText().length());
                localTextView.setText(String.format("%d/140", arrayOfObject));
                ShareScreen.this.footprint.content = ShareScreen.this.shareEditText.getText().toString();
            }

            public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
            }

            public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
            }
        });
        this.shareImageImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                ViewUtils.hideKeyboard(ShareScreen.this.shareEditText);
                if (ShareScreen.this.sharePostScreenListener != null)
                    ShareScreen.this.sharePostScreenListener.selectImage(ShareScreen.this.footprint);
            }
        });
    }

    private void initShareEditText() {
        if (this.footprint == null)
            return;
        this.shareEditText.setText(this.footprint.content);
        this.shareEditText.setSelection(this.shareEditText.getText().length());
        TextView localTextView = this.shareCountTextView;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(140 - this.shareEditText.getText().length());
        localTextView.setText(String.format("%d/140", arrayOfObject));
        ViewUtils.showKeyboard(this.shareEditText);
    }

    private void initUI() {
        addView((ViewGroup) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.share_screen_layout, this, false));
        this.backImageView = ((ImageView) findViewById(R.id.share_screen_back_btn));
        this.sharePostImageView = ((ImageView) findViewById(R.id.share_post_button));
        this.shareImageImageView = ((ImageView) findViewById(R.id.share_image));
        this.shareEditText = ((EditText) findViewById(R.id.share_edit_text));
        this.shareCountTextView = ((TextView) findViewById(R.id.share_count_text_view));
        this.thumbnailTipText = ((TextView) findViewById(R.id.thumbnail_tip_text));
        this.thumbnailContainer = ((ViewGroup) findViewById(R.id.share_screen_thumbnail_container));
    }

    private void recycleThumbnail() {
        if (this.thumbnailContainer.getChildCount() == 0)
            return;
        ImageView localImageView = (ImageView) this.thumbnailContainer.getChildAt(0);
        localImageView.setImageBitmap(null);
        localImageView.setOnClickListener(null);
        this.thumbnailTipText.setVisibility(8);
        this.thumbnailContainer.removeAllViews();
    }

    public void initComponent(Footprint paramFootprint, SharePostScreenListener paramSharePostScreenListener) {
        this.footprint = paramFootprint;
        this.sharePostScreenListener = paramSharePostScreenListener;
        initShareEditText();
        initListener();
    }

    public void onClick(View paramView) {
        this.footprint.removeImage();
        recycleThumbnail();
    }

    public void setBackButtonClickedListener(ScreenBackWithoutResultButtonClickedListener paramScreenBackWithoutResultButtonClickedListener) {
        this.screenBackButtonClickedListener = paramScreenBackWithoutResultButtonClickedListener;
    }

    public void showThumbnail(Bitmap paramBitmap) {
        recycleThumbnail();
        if (paramBitmap == null)
            return;
        addThumbnailToView(paramBitmap);
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.screen.ShareScreen
 * JD-Core Version:    0.6.2
 */
