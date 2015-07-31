package com.thoughtworks.mobile.awayday.components;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.helper.BitmapHelper;
import com.thoughtworks.mobile.awayday.utils.StringUtils;
import com.thoughtworks.mobile.awayday.utils.ViewUtils;

import java.io.FileNotFoundException;

public class PathItemView extends RelativeLayout {
    private LinearLayout contentContainer;
    private TextView contentTextView;
    private TextView dateTextView;
    private FrameLayout imageContainer;
    private RelativeLayout leftContainer;
    private PathImageView pathImageView;
    private LinearLayout rightContainer;
    private TextView timeTextView;

    public PathItemView(Context paramContext) {
        super(paramContext);
        initUI();
        initBitmapHelper();
    }

    private void addImage(Uri paramUri)
            throws FileNotFoundException {
        this.pathImageView.loadImage(paramUri);
        LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -2);
        this.imageContainer.addView(this.pathImageView, localLayoutParams);
    }

    private void initBitmapHelper() {
        int i = this.rightContainer.getMeasuredWidth();
        BitmapHelper.setPathImageMaxSize(i, i);
    }

    private void initUI() {
        addView((ViewGroup) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.path_item_view_layout, this, false));
        this.contentTextView = ((TextView) findViewById(R.id.path_item_view_content));
        this.leftContainer = ((RelativeLayout) findViewById(R.id.path_item_view_left_container));
        this.dateTextView = ((TextView) findViewById(R.id.path_item_view_date));
        this.timeTextView = ((TextView) findViewById(R.id.path_item_view_time));
        this.imageContainer = ((FrameLayout) findViewById(R.id.path_item_view_images_container));
        this.rightContainer = ((LinearLayout) findViewById(R.id.path_item_view_right_container));
        this.contentContainer = ((LinearLayout) findViewById(R.id.path_item_view_content_container));
        ViewUtils.measureView(this.rightContainer);
        this.pathImageView = new PathImageView(getContext());
    }

    public void fillData(Footprint paramFootprint) {
        this.dateTextView.setText(StringUtils.formatPathDate(paramFootprint.createdDate));
        this.timeTextView.setText(StringUtils.formatPathTime(paramFootprint.createdDate));
        if (StringUtils.isEmpty(paramFootprint.content)) {
            this.contentContainer.setVisibility(View.GONE);
        } else {
            this.contentTextView.setText(paramFootprint.content);
        }
        if (paramFootprint.imageUri() == null) {
            return;
        }
        try {
            addImage(paramFootprint.imageUri());
        } catch (FileNotFoundException localFileNotFoundException) {
            Log.e("load path image", "can not find images");
        }
    }

    public int getDividerLeft() {
        return this.leftContainer.getRight();
    }

    public void resetToOriginalState() {
        if (this.imageContainer.getChildCount() == 0)
            return;
        this.pathImageView.setImageBitmap(null);
        this.imageContainer.removeView(this.pathImageView);
        this.contentContainer.setVisibility(0);
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.components.PathItemView
 * JD-Core Version:    0.6.2
 */
