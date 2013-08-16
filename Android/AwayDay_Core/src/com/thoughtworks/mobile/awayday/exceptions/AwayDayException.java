package com.thoughtworks.mobile.awayday.exceptions;

public class AwayDayException extends RuntimeException {
    public String message;

    public AwayDayException(String paramString) {
        super(paramString);
        this.message = paramString;
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.exceptions.AwayDayException
 * JD-Core Version:    0.6.2
 */
