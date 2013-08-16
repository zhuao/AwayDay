package com.thoughtworks.mobile.awayday.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.domain.Session;
import com.thoughtworks.mobile.awayday.listeners.AgendaItemActionClickedListener;
import com.thoughtworks.mobile.awayday.listeners.OnRemindSetListener;
import com.thoughtworks.mobile.awayday.listeners.OnSessionJoinedListener;
import com.thoughtworks.mobile.awayday.utils.StringUtils;

public class AgendaItemAppendix extends LinearLayout
        implements OnRemindSetListener {
    private AgendaItemActionClickedListener agendaItemActionClickedListener;
    private TextView descriptionTextView;
    private View joinAction;
    private ImageView joinInBtn;
    private TextView joinLabel;
    private TextView locationTextView;
    private OnSessionJoinedListener onSessionJoinedListener;
    private View remindAction;
    private TextView remindLabel;
    private ImageView reminderBtn;
    private Session session;
    private View shareAction;
    private ImageView shareBtn;
    private TextView shareLabel;
    private TextView speakerTextView;

    public AgendaItemAppendix(Context paramContext) {
        super(paramContext);
        initUI();
    }

    public AgendaItemAppendix(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        initUI();
    }

    private void initListener() {
        View.OnClickListener local1 = new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                if (AgendaItemAppendix.this.agendaItemActionClickedListener != null)
                    AgendaItemAppendix.this.agendaItemActionClickedListener.onRemindActionClicked(AgendaItemAppendix.this, AgendaItemAppendix.this.session);
            }
        };
        this.remindAction.setOnClickListener(local1);
        this.reminderBtn.setOnClickListener(local1);
        View.OnClickListener local2 = new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                if (AgendaItemAppendix.this.agendaItemActionClickedListener != null)
                    AgendaItemAppendix.this.agendaItemActionClickedListener.onShareActionClicked(AgendaItemAppendix.this.session);
            }
        };
        this.shareAction.setOnClickListener(local2);
        this.shareBtn.setOnClickListener(local2);
        View.OnClickListener local3 = new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                if (AgendaItemAppendix.this.agendaItemActionClickedListener != null)
                    AgendaItemAppendix.this.agendaItemActionClickedListener.onJoinActionClicked(AgendaItemAppendix.this.onSessionJoinedListener, AgendaItemAppendix.this.session);
            }
        };
        this.joinAction.setOnClickListener(local3);
        this.joinInBtn.setOnClickListener(local3);
    }

    private void initUI() {
        addView((ViewGroup) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.agenda_item_view_appendix_layout, this, false));
        this.speakerTextView = ((TextView) findViewById(R.id.agenda_item_appendix_speaker));
        this.locationTextView = ((TextView) findViewById(R.id.agenda_item_appendix_location));
        this.descriptionTextView = ((TextView) findViewById(R.id.agenda_item_appendix_description));
        this.joinInBtn = ((ImageView) findViewById(R.id.agenda_item_appendix_join_btn));
        this.joinLabel = ((TextView) findViewById(R.id.agenda_item_appendix_join_label));
        this.reminderBtn = ((ImageView) findViewById(R.id.agenda_item_appendix_reminder_btn));
        this.remindLabel = ((TextView) findViewById(R.id.agenda_item_appendix_reminder_label));
        this.shareBtn = ((ImageView) findViewById(R.id.agenda_item_appendix_share_btn));
        this.shareLabel = ((TextView) findViewById(R.id.agenda_item_appendix_share_label));
        this.joinAction = findViewById(R.id.agenda_item_appendix_join_action);
        this.remindAction = findViewById(R.id.agenda_item_appendix_reminder_action);
        this.shareAction = findViewById(R.id.agenda_item_appendix_share_action);
    }

    private void switchRemindImage(boolean paramBoolean) {
        if (paramBoolean) {
            this.reminderBtn.setImageDrawable(getResources().getDrawable(R.drawable.reminder));
            this.remindLabel.setText("Cancel");
            return;
        }
        this.reminderBtn.setImageDrawable(getResources().getDrawable(R.drawable.non_reminder));
        this.remindLabel.setText("Remind");
    }

    private void updateActionImages() {
        switchRemindImage(this.session.hasReminder);
        switchJoinImage();
    }

    private void updateTextViewWithValue(TextView paramTextView, String paramString) {
        if (StringUtils.isEmpty(StringUtils.trim(paramString))) {
            paramTextView.setVisibility(View.GONE);
            return;
        }
        paramTextView.setVisibility(View.VISIBLE);
        paramTextView.setText(StringUtils.trim(paramString));
    }

    private void updateView(Session paramSession) {
        speakerTextView.setText(StringUtils.trim(paramSession.sessionSpeaker));
        updateTextViewWithValue(this.descriptionTextView, paramSession.sessionNote);
        updateTextViewWithValue(this.locationTextView, paramSession.sessionLocation);
        updateActionImages();
    }

    public void initComponent(AgendaItemActionClickedListener paramAgendaItemActionClickedListener) {
        this.agendaItemActionClickedListener = paramAgendaItemActionClickedListener;
        initListener();
    }

    public void onRemindSet(boolean paramBoolean) {
        switchRemindImage(paramBoolean);
    }

    public void setOnSessionJoinedListener(OnSessionJoinedListener paramOnSessionJoinedListener) {
        this.onSessionJoinedListener = paramOnSessionJoinedListener;
    }

    public void setSession(Session paramSession) {
        this.session = paramSession;
        updateView(paramSession);
    }

    void switchJoinImage() {
        if (this.session.hasJoined) {
            this.joinInBtn.setImageDrawable(getResources().getDrawable(R.drawable.unjoin));
            this.joinLabel.setText("Leave");
            return;
        }
        this.joinInBtn.setImageDrawable(getResources().getDrawable(R.drawable.join));
        this.joinLabel.setText("Join");
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.components.AgendaItemAppendix
 * JD-Core Version:    0.6.2
 */
