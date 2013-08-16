package com.thoughtworks.mobile.awayday.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.*;
import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.listeners.PullToRefreshCallback;
import com.thoughtworks.mobile.awayday.listeners.PullToRefreshListener;
import com.thoughtworks.mobile.awayday.presenter.PullToRefreshPresenter;
import com.thoughtworks.mobile.awayday.utils.PullToRefreshState;
import com.thoughtworks.mobile.awayday.utils.StringUtils;
import com.thoughtworks.mobile.awayday.utils.ViewUtils;

import java.util.Date;

public class PullToRefreshListView extends ListView implements PullToRefreshCallback {
    private RotateAnimation mFlipAnimation;
    private RelativeLayout mRefreshView;
    private ImageView mRefreshViewImage;
    private TextView mRefreshViewLastUpdated;
    private ProgressBar mRefreshViewProgress;
    private TextView mRefreshViewText;
    private RotateAnimation mReverseFlipAnimation;
    private PullToRefreshPresenter presenter;

    public PullToRefreshListView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    private void init() {
        initAnimation();
        initUI();
        initPresenter();
        initListener();
    }

    private void initAnimation() {

        this.mFlipAnimation = new RotateAnimation(0.0F, -180.0F, RotateAnimation.RELATIVE_TO_SELF, 0.5F, RotateAnimation.RELATIVE_TO_SELF, 0.5F);
        this.mFlipAnimation.setInterpolator(new LinearInterpolator());
        this.mFlipAnimation.setDuration(250L);
        this.mFlipAnimation.setFillAfter(true);
        this.mReverseFlipAnimation = new RotateAnimation(-180.0F, 0.0F, RotateAnimation.RELATIVE_TO_SELF, 0.5F, RotateAnimation.RELATIVE_TO_SELF, 0.5F);
        this.mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
        this.mReverseFlipAnimation.setDuration(250L);
        this.mReverseFlipAnimation.setFillAfter(true);
    }

    private void initListener() {
        this.mRefreshView.setOnClickListener(new OnClickRefreshListener());
        super.setOnScrollListener(this.presenter);
    }

    private void initPresenter() {
        this.presenter = new PullToRefreshPresenter(this);
        this.presenter.setRefreshViewOriginTopPadding(this.mRefreshView.getPaddingTop());
        this.presenter.setRefreshViewHeight(this.mRefreshView.getMeasuredHeight() + this.mRefreshView.getPaddingTop() + this.mRefreshView.getPaddingBottom());
    }

    private void initUI() {
        this.mRefreshView = ((RelativeLayout) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.pull_to_refresh_header, this, false));
        this.mRefreshViewText = ((TextView) this.mRefreshView.findViewById(R.id.pull_to_refresh_text));
        this.mRefreshViewImage = ((ImageView) this.mRefreshView.findViewById(R.id.pull_to_refresh_image));
        this.mRefreshViewProgress = ((ProgressBar) this.mRefreshView.findViewById(R.id.pull_to_refresh_progress));
        this.mRefreshViewLastUpdated = ((TextView) this.mRefreshView.findViewById(R.id.pull_to_refresh_updated_at));
        this.mRefreshViewImage.setMinimumHeight(50);
        addHeaderView(this.mRefreshView);
        ViewUtils.measureView(this.mRefreshView);
    }

    private void setLastUpdated(String paramString) {
        if (paramString != null) {
            this.mRefreshViewLastUpdated.setVisibility(View.VISIBLE);
            this.mRefreshViewLastUpdated.setText(paramString);
            return;
        }
        this.mRefreshViewLastUpdated.setVisibility(View.GONE);
    }

    public int getHeaderBottom() {
        return this.mRefreshView.getBottom();
    }

    public int getHeaderTop() {
        return this.mRefreshView.getTop();
    }

    public void hideHeader() {
        setSelection(1);
    }

    public void hideRefreshImage() {
        this.mRefreshViewImage.setVisibility(View.GONE);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        hideHeader();
    }

    public void onRefreshCompletion() {
        this.presenter.resetHeader();
        setLastUpdated(StringUtils.parsePullToRefreshDateToString(new Date()));
        hideHeader();
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent) {
        this.presenter.onTouchEvent(paramMotionEvent);
        return super.onTouchEvent(paramMotionEvent);
    }

    public void prepareForRefresh() {
        this.mRefreshViewImage.setVisibility(View.GONE);
        this.mRefreshViewImage.setImageDrawable(null);
        this.mRefreshViewProgress.setVisibility(View.VISIBLE);
        this.mRefreshViewText.setText(R.string.pull_to_refresh_refreshing_label);
    }

    public void resetHeaderPadding(int paramInt) {
        this.mRefreshView.setPadding(this.mRefreshView.getPaddingLeft(), paramInt, this.mRefreshView.getPaddingRight(), this.mRefreshView.getPaddingBottom());
    }

    public void resetToOriginalState() {
        this.mRefreshViewText.setText(R.string.pull_to_refresh_tap_label);
        this.mRefreshViewImage.setImageResource(R.drawable.ic_pulltorefresh_arrow);
        this.mRefreshViewImage.clearAnimation();
        this.mRefreshViewImage.setVisibility(View.GONE);
        this.mRefreshViewProgress.setVisibility(View.GONE);
    }

    public void setAdapter(ListAdapter paramListAdapter) {
        super.setAdapter(paramListAdapter);
        hideHeader();
    }

    public void setHeaderPadding(int paramInt) {
        this.mRefreshView.setPadding(this.mRefreshView.getPaddingLeft(), paramInt, this.mRefreshView.getPaddingRight(), this.mRefreshView.getPaddingBottom());
    }

    public void setOnRefreshListener(PullToRefreshListener paramPullToRefreshListener) {
        this.presenter.setOnRefreshListener(paramPullToRefreshListener);
    }

    public void setOnScrollListener(AbsListView.OnScrollListener paramOnScrollListener) {
        this.presenter.setOnScrollListener(paramOnScrollListener);
    }

    public void setToPullToRefresh(PullToRefreshState paramPullToRefreshState) {
        this.mRefreshViewText.setText(R.string.pull_to_refresh_pull_label);
        if (paramPullToRefreshState != PullToRefreshState.TAP_TO_REFRESH) {
            this.mRefreshViewImage.clearAnimation();
            this.mRefreshViewImage.startAnimation(this.mReverseFlipAnimation);
        }
    }

    public void setToReleaseToFresh() {
        this.mRefreshViewText.setText(R.string.pull_to_refresh_release_label);
        this.mRefreshViewImage.clearAnimation();
        this.mRefreshViewImage.startAnimation(this.mFlipAnimation);
    }

    public void showRefreshImage() {
        this.mRefreshViewImage.setVisibility(VISIBLE);
    }

    public void startRefresh() {
        this.presenter.forceToRefresh();
    }

    private class OnClickRefreshListener
            implements View.OnClickListener {
        private OnClickRefreshListener() {
        }

        public void onClick(View paramView) {
            PullToRefreshListView.this.presenter.forceToRefresh();
        }
    }
}