package com.thoughtworks.mobile.awayday.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.helper.ScreenSwitchHelper;
import com.thoughtworks.mobile.awayday.listeners.NavigateCommand;
import com.thoughtworks.mobile.awayday.listeners.NavigationMenuItemListener;
import com.thoughtworks.mobile.awayday.utils.ViewUtils;

public class NavigationMenu extends LinearLayout
        implements NavigationMenuItemListener {
    private NavigationMenuItem agendaItem;
    private LinearLayout content;
    private NavigationMenuItem currentItem;
    private LinearLayout handler;
    private NavigationMenuItem lastClickedItem;
    private ScreenSwitchHelper screenSwitchHelper;
    private Animation translateInAnimation;
    private Animation translateOutAnimation;

    public NavigationMenu(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        initUi();
        initAnimation();
        initListener();
    }

    private void activeItem(NavigateCommand paramNavigateCommand) {
        this.currentItem.active();
        hideMenu();
        hideSoftKeyboard();
        switchScreen(paramNavigateCommand);
    }

    private void deActiveCurrentItem() {
        this.currentItem.deActive();
    }

    private void hideMenu() {
        if (this.content.getVisibility() == GONE)
            return;
        startAnimation(this.translateOutAnimation);
    }

    private void hideSoftKeyboard() {
        ViewUtils.hideKeyboard(this);
    }

    private void initAnimation() {
        this.translateInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.translate_in);
        this.translateOutAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.translate_out);
        this.translateOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation paramAnonymousAnimation) {
                NavigationMenu.this.content.setVisibility(GONE);
            }

            public void onAnimationRepeat(Animation paramAnonymousAnimation) {
            }

            public void onAnimationStart(Animation paramAnonymousAnimation) {
            }
        });
    }

    private void initListener() {
        this.handler.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent) {
                NavigationMenu.this.switchContent();
                return false;
            }
        });
        for (int i = 0; i < this.content.getChildCount(); i++)
            ((NavigationMenuItem) this.content.getChildAt(i)).setItemClickedListener(this);
    }

    private void initUi() {
        inflate(getContext(), R.layout.navigation_menu, this);
        this.agendaItem = ((NavigationMenuItem) findViewById(R.id.menu_item_agenda));
        this.handler = ((LinearLayout) findViewById(R.id.sliding_drawer_handler));
        this.content = ((LinearLayout) findViewById(R.id.sliding_drawer_content));
        this.content.setVisibility(GONE);
    }

    private void performClickItem(NavigationMenuItem paramNavigationMenuItem, NavigateCommand paramNavigateCommand) {
        recordLastActiveItem();
        this.lastClickedItem.deActive();
        this.currentItem = paramNavigationMenuItem;
        activeItem(paramNavigateCommand);
    }

    private void recordLastActiveItem() {
        for (int i = 0; ; i++)
            if (i < this.content.getChildCount()) {
                NavigationMenuItem localNavigationMenuItem = (NavigationMenuItem) this.content.getChildAt(i);
                if (localNavigationMenuItem.isActivated())
                    this.lastClickedItem = localNavigationMenuItem;
            } else {
                return;
            }
    }

    private void showMenu() {
        this.content.setVisibility(View.VISIBLE);
        startAnimation(this.translateInAnimation);
    }

    private void switchContent() {
        if (this.content.getVisibility() == View.GONE) {
            showMenu();
            return;
        }
        hideMenu();
    }

    private void switchScreen(NavigateCommand paramNavigateCommand) {
        if (this.screenSwitchHelper != null)
            paramNavigateCommand.execute(this.screenSwitchHelper);
    }

    public void activeLastItem() {
        if (this.lastClickedItem == null)
            return;
        deActiveCurrentItem();
        activeItem(this.lastClickedItem.getNavigateAction());
    }

    public boolean isInterceptKeyboardBack() {
        if (this.currentItem == null)
            return false;
        return this.currentItem.isInterceptKeyboardBack();
    }

    public void itemClicked(NavigationMenuItem paramNavigationMenuItem, NavigateCommand paramNavigateCommand) {
        performClickItem(paramNavigationMenuItem, paramNavigateCommand);
    }

    public void onKeyboardBack() {
        performClickItem(this.agendaItem, this.agendaItem.getNavigateAction());
    }

    public void setScreenSwitchHelper(ScreenSwitchHelper paramScreenSwitchHelper) {
        this.screenSwitchHelper = paramScreenSwitchHelper;
    }
}