package com.thoughtworks.mobile.awayday.helper;

import android.util.Log;
import com.thoughtworks.mobile.awayday.domain.Footprint;

import java.util.Comparator;
import java.util.Date;

public class FootprintComparator
        implements Comparator<Footprint> {
    public int compare(Footprint paramFootprint1, Footprint paramFootprint2) {
        if (paramFootprint1 == paramFootprint2) {
            return 0;
        }
        try {
            if (paramFootprint1.createdDate.getTime() < paramFootprint2.createdDate.getTime())
                return -1;
            if (paramFootprint1.createdDate.getTime() > paramFootprint2.createdDate.getTime())
                return 1;
            if (paramFootprint1.createdDate.getTime() == paramFootprint2.createdDate.getTime()) {
                return 0;
            }

        } catch (NullPointerException localNullPointerException) {
            Log.w("compare footprint", "null pointer error!");
        }
        return -1;
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.helper.FootprintComparator
 * JD-Core Version:    0.6.2
 */
