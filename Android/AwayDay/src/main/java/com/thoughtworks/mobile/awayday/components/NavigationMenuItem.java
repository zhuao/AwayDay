package com.thoughtworks.mobile.awayday.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.R.styleable;
import com.thoughtworks.mobile.awayday.listeners.NavigateCommand;
import com.thoughtworks.mobile.awayday.listeners.NavigationMenuItemListener;

public abstract class NavigationMenuItem extends LinearLayout {
    private ImageView indicator;
    private TextView itemLabel;
    private ImageView itemLogo;
    private NavigationMenuItemListener navigationMenuItemListener;

    public NavigationMenuItem(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        initUI(paramAttributeSet);
        initState(paramAttributeSet);
        initListener();
    }

    private void initListener() {
        setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                if (NavigationMenuItem.this.navigationMenuItemListener != null)
                    NavigationMenuItem.this.navigationMenuItemListener.itemClicked(NavigationMenuItem.this, NavigationMenuItem.this.getNavigateAction());
            }
        });
    }

    private void initState(AttributeSet paramAttributeSet) {
        String str = paramAttributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "enabled");
        if (str == null) ;
        while (!Boolean.parseBoolean(str))
            return;
        active();
    }

    private void initUI(AttributeSet paramAttributeSet) {
        addView((ViewGroup) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.navigation_menu_item, this, false));
        this.indicator = ((ImageView) findViewById(R.id.activated_indicator));
        this.itemLogo = ((ImageView) findViewById(R.id.navigation_item_logo));
        this.itemLabel = ((TextView) findViewById(R.id.navigation_item_label));
        TypedArray localTypedArray = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.NavigationMenuItem);
        try {
            this.itemLogo.setImageResource(localTypedArray.getResourceId(R.styleable.NavigationMenuItem_src, R.drawable.ic_action_button));
            this.itemLabel.setText(localTypedArray.getText(R.styleable.NavigationMenuItem_text));
        } finally {
            localTypedArray.recycle();
        }
    }

    public void active() {
        indicator.setVisibility(View.VISIBLE);
        itemLogo.setSelected(true);
        itemLabel.setTextColor(getResources().getColor(R.color.navigation_menu_item_label_active));
    }

    public void deActive() {
        this.indicator.setVisibility(4);
        this.itemLogo.setSelected(false);
        this.itemLabel.setTextColor(getResources().getColor(R.color.navigation_menu_item_label_deactive));
    }

    public abstract NavigateCommand getNavigateAction();

    public boolean isActivated() {
        return this.indicator.getVisibility() == 0;
    }

    public abstract boolean isInterceptKeyboardBack();

    public void setItemClickedListener(NavigationMenuItemListener paramNavigationMenuItemListener) {
        this.navigationMenuItemListener = paramNavigationMenuItemListener;
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.components.NavigationMenuItem
 * JD-Core Version:    0.6.2
 */
