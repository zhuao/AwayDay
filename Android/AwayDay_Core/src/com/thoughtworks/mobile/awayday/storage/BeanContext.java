package com.thoughtworks.mobile.awayday.storage;

import java.util.HashMap;
import java.util.Map;

public class BeanContext {
    private static BeanContext beanContext;
    private Map<Class, Object> beans = new HashMap();

    public static BeanContext getInstance() {
        if (beanContext == null)
            beanContext = new BeanContext();
        return beanContext;
    }

    public <T> T getBean(Class<T> paramClass) {
        return (T) beans.get(paramClass);
    }

    public <T> void putBean(Class<T> paramClass, T paramT) {
        if (this.beans == null)
            this.beans = new HashMap();
        this.beans.put(paramClass, paramT);
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.storage.BeanContext
 * JD-Core Version:    0.6.2
 */
