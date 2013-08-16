package com.thoughtworks.mobile.awayday.components;

import android.content.Context;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextFitTextView extends TextView {
    public TextFitTextView(Context paramContext) {
        super(paramContext);
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.END);
    }

    public TextFitTextView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.END);
    }

    public TextFitTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.END);
    }

    protected void onMeasure(int paramInt1, int paramInt2) {
        super.onMeasure(paramInt1, paramInt2);
        Layout localLayout = getLayout();
        if (localLayout != null) {
            int i = localLayout.getLineCount();
            if ((i > 0) && (localLayout.getEllipsisCount(i - 1) > 0)) {
                setTextSize(0, getTextSize() - 1.0F);
                super.onMeasure(paramInt1, paramInt2);
            }
        }
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.components.TextFitTextView
 * JD-Core Version:    0.6.2
 */
