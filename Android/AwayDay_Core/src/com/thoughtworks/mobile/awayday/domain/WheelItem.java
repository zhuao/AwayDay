package com.thoughtworks.mobile.awayday.domain;

public class WheelItem {
    public String label;
    public int value;

    public WheelItem(String paramString, int paramInt) {
        this.label = paramString;
        this.value = paramInt;
    }

    public String toString() {
        return this.label;
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.domain.WheelItem
 * JD-Core Version:    0.6.2
 */
