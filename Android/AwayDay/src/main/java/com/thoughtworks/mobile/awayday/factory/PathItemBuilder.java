package com.thoughtworks.mobile.awayday.factory;

import android.content.Context;
import com.thoughtworks.mobile.awayday.components.PathItemView;
import com.thoughtworks.mobile.awayday.domain.Footprint;

public class PathItemBuilder {
    private Context context;

    public PathItemBuilder(Context paramContext) {
        this.context = paramContext;
    }

    public PathItemView buildNewPathItemView(Footprint paramFootprint) {
        PathItemView localPathItemView = new PathItemView(this.context);
        localPathItemView.fillData(paramFootprint);
        return localPathItemView;
    }

    public void updatePathItemView(PathItemView paramPathItemView, Footprint paramFootprint) {
        paramPathItemView.resetToOriginalState();
        paramPathItemView.fillData(paramFootprint);
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.factory.PathItemBuilder
 * JD-Core Version:    0.6.2
 */
