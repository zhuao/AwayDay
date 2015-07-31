package com.thoughtworks.mobile.awayday.service.implement;

import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.service.SaveToLocalService;
import com.thoughtworks.mobile.awayday.storage.BeanContext;
import com.thoughtworks.mobile.awayday.storage.LocalStorage;
import com.thoughtworks.mobile.awayday.utils.ActionStatus;

public class SaveToLocalServiceImpl
        implements SaveToLocalService {
    public ActionStatus saveToLocal(Footprint paramFootprint) {
        try {
            ((LocalStorage) BeanContext.getInstance().getBean(LocalStorage.class)).addFootprint(paramFootprint);
            ActionStatus localActionStatus = ActionStatus.SHARE_SUCCESS;
            return localActionStatus;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return ActionStatus.SAVE_TO_LOCAL_ERROR;
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.service.implement.SaveToLocalServiceImpl
 * JD-Core Version:    0.6.2
 */
