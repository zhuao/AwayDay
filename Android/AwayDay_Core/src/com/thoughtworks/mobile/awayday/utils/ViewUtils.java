package com.thoughtworks.mobile.awayday.utils;

import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class ViewUtils {
    public static void hideKeyboard(View paramView) {
        ((InputMethodManager) paramView.getContext().getSystemService("input_method")).hideSoftInputFromWindow(paramView.getWindowToken(), 0);
    }

    public static void measureView(View paramView) {
        ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
        if (localLayoutParams == null)
            localLayoutParams = new ViewGroup.LayoutParams(-1, -2);
        int i = ViewGroup.getChildMeasureSpec(0, 0, localLayoutParams.width);
        int j = localLayoutParams.height;
        if (j > 0) ;
        for (int k = View.MeasureSpec.makeMeasureSpec(j, 1073741824); ; k = View.MeasureSpec.makeMeasureSpec(0, 0)) {
            paramView.measure(i, k);
            return;
        }
    }

    public static void showKeyboard(View paramView) {
        ((InputMethodManager) paramView.getContext().getSystemService("input_method")).toggleSoftInput(2, 0);
    }

    public static void showToast(Context paramContext, int paramInt) {
        Toast.makeText(paramContext, paramContext.getString(paramInt), 20).show();
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.utils.ViewUtils
 * JD-Core Version:    0.6.2
 */
