package com.thoughtworks.mobile.awayday.factory;

import android.content.Context;
import com.thoughtworks.mobile.awayday.domain.Path;
import com.thoughtworks.mobile.awayday.listeners.OnSaveFootprintListener;
import com.thoughtworks.mobile.awayday.listeners.OnShareFootprintListener;
import com.thoughtworks.mobile.awayday.listeners.SaveFootprintMonitor;
import com.thoughtworks.mobile.awayday.listeners.ShareFootprintMonitor;
import com.thoughtworks.mobile.awayday.service.JoinService;
import com.thoughtworks.mobile.awayday.service.SaveToLocalService;
import com.thoughtworks.mobile.awayday.service.ShareToRemoteService;
import com.thoughtworks.mobile.awayday.service.implement.JoinServiceImpl;
import com.thoughtworks.mobile.awayday.service.implement.SaveToLocalServiceImpl;
import com.thoughtworks.mobile.awayday.service.implement.ShareToRemoteServiceImpl;
import com.thoughtworks.mobile.awayday.storage.BeanContext;
import com.thoughtworks.mobile.awayday.storage.LocalSharedPreferencesStorage;
import com.thoughtworks.mobile.awayday.storage.LocalStorage;
import com.thoughtworks.mobile.awayday.storage.PreferencesStorage;
import com.thoughtworks.mobile.awayday.storage.SqliteStorage;

public class BeanRegister
        implements BaseRegister {
    private Context context;

    public BeanRegister(Context paramContext) {
        this.context = paramContext;
    }

    public void onRegister() {
        BeanContext.getInstance().putBean(PreferencesStorage.class, new LocalSharedPreferencesStorage(this.context));
        BeanContext.getInstance().putBean(LocalStorage.class, new SqliteStorage(this.context));
        BeanContext.getInstance().putBean(ShareToRemoteService.class, new ShareToRemoteServiceImpl(this.context));
        BeanContext.getInstance().putBean(SaveToLocalService.class, new SaveToLocalServiceImpl());
        BeanContext.getInstance().putBean(JoinService.class, new JoinServiceImpl());
        BeanContext.getInstance().putBean(Path.class, new Path());
        BeanContext.getInstance().putBean(OnSaveFootprintListener.class, new SaveFootprintMonitor(this.context));
        BeanContext.getInstance().putBean(OnShareFootprintListener.class, new ShareFootprintMonitor(this.context));
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.factory.BeanRegister
 * JD-Core Version:    0.6.2
 */
