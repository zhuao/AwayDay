package com.thoughtworks.mobile.awayday.components;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.domain.Session;
import com.thoughtworks.mobile.awayday.listeners.AgendaItemActionClickedListener;
import com.thoughtworks.mobile.awayday.listeners.AgendaItemViewStateRecorder;
import com.thoughtworks.mobile.awayday.listeners.OnSessionJoinedListener;
import com.thoughtworks.mobile.awayday.utils.ActionStatus;
import com.thoughtworks.mobile.awayday.utils.StringUtils;

import java.util.Date;

public class AgendaItemView extends RelativeLayout
        implements OnSessionJoinedListener {
    private AgendaItemAppendix agendaItemAppendix;
    private AgendaItemViewStateRecorder agendaItemViewStateRecorder;
    private TextView dateView;
    private boolean isTitleOneLine = false;
    private Session session;
    private TextView timeView;
    private LinearLayout titleTextContainer;
    private TextView titleView;
    private int titleViewWidth;

    public AgendaItemView(Context paramContext) {
        super(paramContext);
        initUI();
        initListener();
    }

    private void adjustTitleText(String paramString, Paint paramPaint) {
        int i = calculateMaxTitleTextLength(paramString, paramPaint);
        this.titleView.setText(calculateShownTitleText(paramString, paramPaint, i));
    }

    private int calculateMaxTitleTextLength(String paramString, Paint paramPaint) {
        for (int maxLength = 0; ; maxLength++)
            if ((maxLength >= paramString.length()) || (paramPaint.measureText(paramString, 0, maxLength) >= this.titleViewWidth))
                return maxLength;
    }

    private String calculateShownTitleText(String paramString, Paint paramPaint, int maxLength) {
        int i = maxLength;
        String str = null;
        while (i >= 0) {
            str = paramString.substring(0, maxLength) + "..";
            if (paramPaint.measureText(str) >= this.titleViewWidth) {
                i--;
            } else {
                break;
            }
        }
        return str;
    }

    private void calculateTitleViewWidth() {
        this.titleViewWidth = (this.titleTextContainer.getMeasuredWidth() - this.timeView.getMeasuredWidth() - this.timeView.getPaddingLeft());
    }

    private void fillAppendix() {
        this.agendaItemAppendix.setSession(this.session);
    }

    private void foldAppendix() {
        setTitleTextToFitOneLine();
        this.agendaItemAppendix.setVisibility(8);
    }

    private void handleTitleView() {
        calculateTitleViewWidth();
        if (this.isTitleOneLine) {
            setTitleTextToFitOneLine();
            this.isTitleOneLine = false;
        }
    }

    private void hideDateTitle() {
        this.dateView.setVisibility(8);
    }

    private void highlightTile() {
        this.titleView.setTextColor(getResources().getColor(R.color.agenda_highlight));
        this.timeView.setTextColor(getResources().getColor(R.color.agenda_highlight));
    }

    private void initListener() {
        setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                AgendaItemView.this.toggleAppendix();
            }
        });
        this.agendaItemAppendix.setOnSessionJoinedListener(this);
    }

    private void initUI() {
        addView((ViewGroup) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.agenda_item_view_layout, this, false));
        this.titleTextContainer = ((LinearLayout) findViewById(R.id.title_text_container));
        this.dateView = ((TextView) findViewById(R.id.agenda_item_view_date));
        this.titleView = ((TextView) findViewById(R.id.agenda_item_view_title));
        this.timeView = ((TextView) findViewById(R.id.agenda_item_view_time));
        this.agendaItemAppendix = ((AgendaItemAppendix) findViewById(R.id.agenda_item_appendix));
    }

    private void resetTileState() {
        this.titleView.setTextColor(getResources().getColor(R.color.agenda_item_view_title));
        this.timeView.setTextColor(getResources().getColor(R.color.agenda_item_view_title));
    }

    private void saveItemViewState(int paramInt) {
        if (this.agendaItemViewStateRecorder == null)
            return;
        this.agendaItemViewStateRecorder.saveAgendaItemAppendixViewState(this.session.sessionId, paramInt);
    }

    private void setAppendixVisibility(int paramInt) {
        this.agendaItemAppendix.setVisibility(paramInt);
    }

    private void setTitleTextAccordingState(int paramInt) {
        if (paramInt == 8) {
            setTitleTextToFitOneLine();
            return;
        }
        this.isTitleOneLine = false;
        setTitleTextWithFullValue();
    }

    private void setTitleTextToFitOneLine() {
        TextPaint localTextPaint = this.titleView.getPaint();
        if (localTextPaint.measureText(this.session.sessionTitle) <= this.titleViewWidth) {
            this.titleView.setText(this.session.sessionTitle);
            return;
        }
        adjustTitleText(this.session.sessionTitle, localTextPaint);
    }

    private void setTitleTextWithFullValue() {
        this.titleView.setText(this.session.sessionTitle);
    }

    private void switchTitleColor() {
        if (this.session.hasJoined) {
            highlightTile();
            return;
        }
        resetTileState();
    }

    private void toggleAppendix() {
        if (this.agendaItemAppendix.getVisibility() == 8) {
            unfoldAppendix();
        } else {
            foldAppendix();
        }
        saveItemViewState(this.agendaItemAppendix.getVisibility());
    }

    private void unfoldAppendix() {
        setTitleTextWithFullValue();
        this.agendaItemAppendix.setVisibility(0);
    }

    public void fillWithData(Session paramSession) {
        this.session = paramSession;
        this.isTitleOneLine = true;
        switchTitleColor();
        this.timeView.setText(StringUtils.formatSessionDateToString(paramSession.sessionStartTime) + " ~ " + StringUtils.formatSessionDateToString(paramSession.sessionEndTime));
        fillAppendix();
    }

    public void initStateAccordingToSession() {
        hideDateTitle();
        if (this.agendaItemViewStateRecorder == null)
            return;
        int i = this.agendaItemViewStateRecorder.getItemViewVisibility(this.session.sessionId);
        setAppendixVisibility(i);
        setTitleTextAccordingState(i);
    }

    protected void onMeasure(int paramInt1, int paramInt2) {
        super.onMeasure(paramInt1, paramInt2);
        handleTitleView();
    }

    public void onSessionJoin(ActionStatus paramActionStatus) {
        paramActionStatus.showMessage(getContext());
        switchTitleColor();
        this.agendaItemAppendix.switchJoinImage();
    }

    public void setAgendaItemActionClickedListener(AgendaItemActionClickedListener paramAgendaItemActionClickedListener) {
        this.agendaItemAppendix.initComponent(paramAgendaItemActionClickedListener);
    }

    public void setAgendaItemViewStateRecorder(AgendaItemViewStateRecorder paramAgendaItemViewStateRecorder) {
        this.agendaItemViewStateRecorder = paramAgendaItemViewStateRecorder;
    }

    public void showAgendaDate(Date paramDate) {
        this.dateView.setVisibility(0);
        this.dateView.setText(StringUtils.formatAgendaDateToString(paramDate));
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.components.AgendaItemView
 * JD-Core Version:    0.6.2
 */
