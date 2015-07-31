package com.thoughtworks.mobile.awayday.presenter;

import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.components.PullToRefreshListView;
import com.thoughtworks.mobile.awayday.listeners.PullToRefreshListener;
import com.thoughtworks.mobile.awayday.utils.PullToRefreshState;

public class PullToRefreshPresenter implements AbsListView.OnScrollListener {
    private boolean mBounceHack;
    private int mCurrentScrollState;
    private int mLastMotionY;
    private int mRefreshOriginalTopPadding;
    private PullToRefreshState mRefreshState;
    private int mRefreshViewHeight;
    private AbsListView.OnScrollListener onScrollListener;
    private PullToRefreshListView pullToRefreshListView;
    private PullToRefreshListener refreshListener;

    public PullToRefreshPresenter(PullToRefreshListView paramPullToRefreshListView) {
        this.pullToRefreshListView = paramPullToRefreshListView;
        init();
    }

    private void applyHeaderPadding(MotionEvent paramMotionEvent) {
        int i = paramMotionEvent.getHistorySize();
        for (int j = 0; j < i; j++)
            if (this.mRefreshState == PullToRefreshState.RELEASE_TO_REFRESH) {
                if (this.pullToRefreshListView.isVerticalFadingEdgeEnabled())
                    this.pullToRefreshListView.setVerticalScrollBarEnabled(false);
                this.pullToRefreshListView.setHeaderPadding((int) (((int) paramMotionEvent.getHistoricalY(j) - this.mLastMotionY - this.mRefreshViewHeight) / 1.7D));
            }
    }

    private boolean canReleaseToHideHeader() {
        return (this.pullToRefreshListView.getHeaderBottom() < this.mRefreshViewHeight) || (this.pullToRefreshListView.getHeaderTop() <= 0);
    }

    private boolean canReleaseToRefresh() {
        return ((this.pullToRefreshListView.getHeaderBottom() >= this.mRefreshViewHeight) || (this.pullToRefreshListView.getHeaderTop() >= 0)) && (this.mRefreshState == PullToRefreshState.RELEASE_TO_REFRESH);
    }

    private void handleActionUp() {
        if (!this.pullToRefreshListView.isVerticalScrollBarEnabled()) {
            this.pullToRefreshListView.setVerticalScrollBarEnabled(true);
        }
        if ((!isHeaderVisible(this.pullToRefreshListView.getFirstVisiblePosition())) || (this.mRefreshState == PullToRefreshState.REFRESHING)) {
            return;
        }

        if (canReleaseToRefresh()) {
            setToRefresh();
            return;
        }
        if (!canReleaseToHideHeader()) {
            resetHeader();
            this.pullToRefreshListView.hideHeader();
        }

    }

    private void handleScrollDe(int firstVisiblePosition) {
        if ((this.mCurrentScrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) && (this.mRefreshState != PullToRefreshState.REFRESHING)) {

            if (isHeaderVisible(firstVisiblePosition)) {
                this.pullToRefreshListView.showRefreshImage();
                if (shouldShowReleaseToRefresh()) {
                    this.mRefreshState = PullToRefreshState.RELEASE_TO_REFRESH;
                    this.pullToRefreshListView.setToReleaseToFresh();
                }
            }
        }
        if ((!this.mBounceHack) || (this.mCurrentScrollState != SCROLL_STATE_FLING)) {
            this.pullToRefreshListView.setToPullToRefresh(this.mRefreshState);
            this.mRefreshState = PullToRefreshState.PULL_TO_REFRESH;
            return;
        } else {
            this.pullToRefreshListView.hideHeader();
        }
        if (!shouldShowPullToRefresh()) {
            this.pullToRefreshListView.hideRefreshImage();
            resetHeader();
            return;
        }
        if ((this.mCurrentScrollState == SCROLL_STATE_FLING) && (firstVisiblePosition == 0) && (this.mRefreshState != PullToRefreshState.REFRESHING)) {
            this.pullToRefreshListView.hideHeader();
            this.mBounceHack = true;
            return;
        }


    }

    private void handleScroll(int firstVisiblePosition) {
        if (mCurrentScrollState == SCROLL_STATE_TOUCH_SCROLL && mRefreshState != PullToRefreshState.REFRESHING) {
            if (isHeaderVisible(firstVisiblePosition)) {
                this.pullToRefreshListView.showRefreshImage();
                if (shouldShowReleaseToRefresh()) {
                    this.mRefreshState = PullToRefreshState.RELEASE_TO_REFRESH;
                    this.pullToRefreshListView.setToReleaseToFresh();
                } else if (pullToRefreshListView.getHeaderBottom() < mRefreshViewHeight + 20
                        && mRefreshState != PullToRefreshState.PULL_TO_REFRESH) {
                    this.pullToRefreshListView.setToPullToRefresh(this.mRefreshState);
                    mRefreshState = PullToRefreshState.PULL_TO_REFRESH;
                }
            } else {
                this.pullToRefreshListView.hideRefreshImage();
                resetHeader();
            }
        } else if (mCurrentScrollState == SCROLL_STATE_FLING
                && firstVisiblePosition == 0
                && mRefreshState != PullToRefreshState.REFRESHING) {
            resetHeader();
            mBounceHack = true;
        } else if (mBounceHack && mCurrentScrollState == SCROLL_STATE_FLING) {
            resetHeader();
        }
    }

    private void init() {
        this.mRefreshState = PullToRefreshState.TAP_TO_REFRESH;
    }

    private boolean isHeaderVisible(int firstVisiblePosition) {
        return firstVisiblePosition == 0;
    }

    private void onRefresh() {
        if (this.refreshListener != null)
            this.refreshListener.onRefresh();
    }

    private boolean shouldShowPullToRefresh() {
        return (this.pullToRefreshListView.getHeaderBottom() < -8 + this.mRefreshViewHeight) && (this.mRefreshState != PullToRefreshState.PULL_TO_REFRESH);
    }

    private boolean shouldShowReleaseToRefresh() {
        return ((this.pullToRefreshListView.getHeaderBottom() >= -8 + this.mRefreshViewHeight) || (this.pullToRefreshListView.getHeaderTop() >= 0)) && (this.mRefreshState != PullToRefreshState.RELEASE_TO_REFRESH);
    }

    public void forceToRefresh() {
        if (this.mRefreshState != PullToRefreshState.REFRESHING)
            setToRefresh();
    }

    public void onScroll(AbsListView paramAbsListView, int firstVisiblePosition, int visibleItemCount, int totalItemCount) {
        handleScroll(firstVisiblePosition);
        if (this.onScrollListener != null) {
            this.onScrollListener.onScroll(paramAbsListView, firstVisiblePosition, visibleItemCount, totalItemCount);
        }
    }

    public void onScrollStateChanged(AbsListView paramAbsListView, int scrollState) {
        this.mCurrentScrollState = scrollState;
        if (this.mCurrentScrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            this.mBounceHack = false;
        }
        if (this.onScrollListener != null) {
            this.onScrollListener.onScrollStateChanged(paramAbsListView, scrollState);
        }
    }

    public void onTouchEvent(MotionEvent paramMotionEvent) {
        int i = (int) paramMotionEvent.getY();
        this.mBounceHack = false;
        switch (paramMotionEvent.getAction()) {
            default:
                return;
            case MotionEvent.ACTION_UP:
                handleActionUp();
                return;
            case MotionEvent.ACTION_DOWN:
                this.mLastMotionY = i;
                return;
            case MotionEvent.ACTION_MOVE:
        }
        applyHeaderPadding(paramMotionEvent);
    }

    public void resetHeader() {
        if (this.mRefreshState != PullToRefreshState.TAP_TO_REFRESH) {
            this.mRefreshState = PullToRefreshState.TAP_TO_REFRESH;
            this.pullToRefreshListView.resetHeaderPadding(this.mRefreshOriginalTopPadding);
            this.pullToRefreshListView.resetToOriginalState();
        }
    }

    public void setOnRefreshListener(PullToRefreshListener paramPullToRefreshListener) {
        this.refreshListener = paramPullToRefreshListener;
    }

    public void setOnScrollListener(AbsListView.OnScrollListener paramOnScrollListener) {
        this.onScrollListener = paramOnScrollListener;
    }

    public void setRefreshViewHeight(int paramInt) {
        this.mRefreshViewHeight = paramInt;
    }

    public void setRefreshViewOriginTopPadding(int paramInt) {
        this.mRefreshOriginalTopPadding = paramInt;
    }

    public void setToRefresh() {
        this.mRefreshState = PullToRefreshState.REFRESHING;
        this.pullToRefreshListView.resetHeaderPadding(this.mRefreshOriginalTopPadding);
        this.pullToRefreshListView.prepareForRefresh();
        onRefresh();
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.presenter.PullToRefreshPresenter
 * JD-Core Version:    0.6.2
 */
