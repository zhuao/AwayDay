package com.thoughtworks.mobile.awayday.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.thoughtworks.mobile.awayday.components.PathItemView;
import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.factory.PathItemBuilder;
import com.thoughtworks.mobile.awayday.helper.FootprintComparator;
import com.thoughtworks.mobile.awayday.screen.PathScreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PathListViewAdapter extends BaseAdapter {
    private List<Footprint> footprintList;
    private PathItemBuilder pathItemBuilder;
    private PathScreen pathScreen;

    public PathListViewAdapter(PathItemBuilder paramPathItemBuilder, PathScreen paramPathScreen) {
        this.pathItemBuilder = paramPathItemBuilder;
        this.pathScreen = paramPathScreen;
        this.footprintList = new ArrayList();
    }

    private void updatePathScreen() {
        this.pathScreen.updateSummaryText(String.valueOf(this.footprintList.size()));
    }

    public int getCount() {
        return this.footprintList.size();
    }

    public Object getItem(int paramInt) {
        return Integer.valueOf(paramInt);
    }

    public long getItemId(int paramInt) {
        return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        Footprint localFootprint = (Footprint) this.footprintList.get(paramInt);
        if (localFootprint == null)
            return paramView;
        if (paramView == null)
            return this.pathItemBuilder.buildNewPathItemView(localFootprint);
        this.pathItemBuilder.updatePathItemView((PathItemView) paramView, localFootprint);
        return paramView;
    }

    public void onDataChanged() {
        updatePathScreen();
        Collections.sort(this.footprintList, new FootprintComparator());
        notifyDataSetChanged();
    }

    public void onDataFetched(List<Footprint> paramList) {
        this.footprintList = paramList;
        updatePathScreen();
        notifyDataSetChanged();
    }
}