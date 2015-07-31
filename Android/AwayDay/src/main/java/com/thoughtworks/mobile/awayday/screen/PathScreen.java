package com.thoughtworks.mobile.awayday.screen;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.adapter.PathListViewAdapter;
import com.thoughtworks.mobile.awayday.components.PathItemView;
import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.factory.PathItemBuilder;
import com.thoughtworks.mobile.awayday.listeners.OnPathAddActionListener;
import com.thoughtworks.mobile.awayday.utils.StringUtils;

import java.util.List;

public class PathScreen extends LinearLayout {
    private static final int PATH_DIVIDER_WIDTH = 3;
    private View addBtn;
    private LinearLayout headerContainer;
    private TextView nameTextView;
    private OnPathAddActionListener onPathAddActionListener;
    private Paint paint;
    private ListView pathListView;
    private PathListViewAdapter pathListViewAdapter;
    private TextView summaryText;

    public PathScreen(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        initUI();
        initPaint();
    }

    private void drawLine(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        this.paint.setStrokeWidth(paramInt3);
        paramCanvas.drawLine(paramInt1, paramInt2, paramInt1, paramInt2 + paramInt4, this.paint);
    }

    private void drawPathDivider(Canvas paramCanvas, int paramInt) {
        int i = 0;
        int j = 0;
        for (int k = 0; k < paramInt; k++) {
            PathItemView localPathItemView = (PathItemView) this.pathListView.getChildAt(k);
            i += localPathItemView.getHeight();
            if (j == 0)
                j = localPathItemView.getDividerLeft();
        }
        drawLine(paramCanvas, j, getDividerTop(), 3, getDividerBottom(i, paramInt));
    }

    private int getDividerBottom(int paramInt1, int paramInt2) {
        return paramInt1 + this.headerContainer.getPaddingBottom() + paramInt2 * this.pathListView.getDividerHeight();
    }

    private int getDividerTop() {
        return this.headerContainer.getBottom() - this.headerContainer.getPaddingBottom();
    }

    private void initListener() {
        this.addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                if (PathScreen.this.onPathAddActionListener != null)
                    PathScreen.this.onPathAddActionListener.onPathAddAction();
            }
        });
    }

    private void initPaint() {
        this.paint = new Paint();
        this.paint.setColor(getResources().getColor(R.color.path_divider));
    }

    private void initUI() {
        addView((ViewGroup) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.path_screen_layout, this, false));
        this.summaryText = ((TextView) findViewById(R.id.path_summary_text));
        this.nameTextView = ((TextView) findViewById(R.id.path_name_text));
        this.addBtn = findViewById(R.id.path_add_text);
        this.pathListView = ((ListView) findViewById(R.id.path_screen_list));
        this.headerContainer = ((LinearLayout) findViewById(R.id.path_header_container));
    }

    protected void dispatchDraw(Canvas paramCanvas) {
        super.dispatchDraw(paramCanvas);
        int i = this.pathListView.getChildCount();
        if (i == 0)
            return;
        drawPathDivider(paramCanvas, i);
    }

    public void initComponent(PathItemBuilder paramPathItemBuilder) {
        this.pathListViewAdapter = new PathListViewAdapter(paramPathItemBuilder, this);
        this.pathListView.setAdapter(this.pathListViewAdapter);
    }

    public void onPathFetched(List<Footprint> paramList) {
        this.pathListViewAdapter.onDataFetched(paramList);
    }

    public void setAddButtonClickedListener(OnPathAddActionListener paramOnPathAddActionListener) {
        this.onPathAddActionListener = paramOnPathAddActionListener;
        initListener();
    }

    public void setUserName(String paramString) {
        this.nameTextView.setText(paramString);
    }

    public void updateOnPathChanged() {
        this.pathListViewAdapter.onDataChanged();
    }

    public void updateSummaryText(final String paramString) {
        post(new Runnable() {
            public void run() {
                PathScreen.this.summaryText.setText(StringUtils.parseToPathSummaryText(paramString));
            }
        });
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.screen.PathScreen
 * JD-Core Version:    0.6.2
 */
